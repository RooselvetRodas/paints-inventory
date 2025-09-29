package it.miniatures.paints.controller;

import it.miniatures.paints.dto.PaintRequest;
import it.miniatures.paints.dto.PaintResponse;
import it.miniatures.paints.dto.QuantityPatchRequest;
import it.miniatures.paints.entity.Paint;
import it.miniatures.paints.service.PaintService;
import it.miniatures.paints.util.PdfExporter;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/paints")
public class PaintController {

    private static final Logger log = LoggerFactory.getLogger(PaintController.class);

    private final PaintService service;

    public PaintController(PaintService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PaintResponse> add(@Valid @RequestBody PaintRequest req) {
        log.info("API POST /api/paints - brand={}, colorName={}, quantity={}",
                req.getBrand(), req.getColorName(), req.getQuantity());

        Paint entity = new Paint();
        entity.setBrand(req.getBrand());
        entity.setColorName(req.getColorName());
        entity.setType(req.getType());
        entity.setCode(req.getCode());
        entity.setQuantity(req.getQuantity());

        Paint saved = service.addOrIncrement(entity);
        return ResponseEntity.ok(toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<PaintResponse>> list() {
        log.debug("API GET /api/paints");
        return ResponseEntity.ok(service.getAll().stream().map(this::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaintResponse> get(@PathVariable Long id) {
        log.debug("API GET /api/paints/{}", id);
        return ResponseEntity.ok(toResponse(service.getById(id)));
    }

    @GetMapping("/export/excel")
    public void exportExcel(HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=paints.xlsx");

        service.exportToExcel(response.getOutputStream());
    }

    @GetMapping("/export/pdf")
    public void exportToPdf(HttpServletResponse response) throws Exception {
        log.info("API GET /api/paints/export/pdf");

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=paints.pdf");

        List<Paint> list = service.getAll();

        PdfExporter exporter = new PdfExporter(list);
        exporter.export(response);
    }



    @PatchMapping("/{id}/quantity")
    public ResponseEntity<PaintResponse> patchQuantity(@PathVariable Long id,
                                                       @Valid @RequestBody QuantityPatchRequest body) {
        log.info("API PATCH /api/paints/{}/quantity delta={}", id, body.getDelta());
        return ResponseEntity.ok(toResponse(service.patchQuantity(id, body.getDelta())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("API DELETE /api/paints/{}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private PaintResponse toResponse(Paint p) {
        PaintResponse r = new PaintResponse();
        r.setId(p.getId());
        r.setBrand(p.getBrand());
        r.setColorName(p.getColorName());
        r.setType(p.getType());
        r.setCode(p.getCode());
        r.setQuantity(p.getQuantity());
        r.setData(p.getData());
        return r;
    }
}
