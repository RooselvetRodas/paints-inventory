package it.miniatures.paints.service;

import it.miniatures.paints.entity.Paint;
import it.miniatures.paints.repository.PaintRepository;
import it.miniatures.paints.util.ExcelExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaintService {

    private static final Logger log = LoggerFactory.getLogger(PaintService.class);

    private final PaintRepository repository;

    public PaintService(PaintRepository repository) {
        this.repository = repository;
    }

    public List<Paint> getAll() {
        log.debug("Richiesta lista colori");
        return repository.findAll();
    }

    public Paint getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Colore id={} non trovato", id);
                    return new IllegalArgumentException("Colore non trovato");
                });
    }

    @Transactional
    public Paint addOrIncrement(Paint incoming) {
        log.info("Inserimento/Incremento richiesto: {}", incoming);

        return repository.findByBrandAndColorName(incoming.getBrand(), incoming.getColorName())
                .map(existing -> {
                    int oldQ = existing.getQuantity();
                    int inc  = (incoming.getQuantity() != null && incoming.getQuantity() > 0)
                            ? incoming.getQuantity() : 1;
                    existing.setQuantity(oldQ + inc);
                    log.info("Colore esistente: {}. Quantità {} -> {}", existing.getId(), oldQ, existing.getQuantity());
                    return repository.save(existing);
                })
                .orElseGet(() -> {
                    if (incoming.getQuantity() == null || incoming.getQuantity() <= 0) {
                        incoming.setQuantity(1);
                    }
                    if (incoming.getData() == null) {
                        incoming.setData(LocalDateTime.now());
                    }
                    try {
                        Paint saved = repository.save(incoming);
                        log.info("Colore nuovo inserito: {}", saved);
                        return saved;
                    } catch (DataIntegrityViolationException ex) {
                        // in caso di race condition con la unique constraint
                        log.warn("Conflitto di unicità su (brand,color_name). Ritento come incremento. Dettagli: {}", ex.getMessage());
                        return addOrIncrement(incoming);
                    }
                });
    }

    @Transactional
    public Paint patchQuantity(Long id, int delta) {
        Paint p = getById(id);
        int oldQ = p.getQuantity();
        int newQ = Math.max(0, oldQ + delta);
        p.setQuantity(newQ);
        log.info("Patch quantity id={} : {} -> {} (delta {})", id, oldQ, newQ, delta);
        return repository.save(p);
    }

    @Transactional
    public void delete(Long id) {
        Paint p = getById(id);
        repository.delete(p);
        log.info("Cancellato colore id={} ({}, {})", id, p.getBrand(), p.getColorName());
    }

    public void exportToExcel(OutputStream out) throws Exception {
        List<Paint> paints = repository.findAll();
        log.info("Exporting {} paints to Excel", paints.size());
        ExcelExporter.exportPaints(paints, out);
    }
}
