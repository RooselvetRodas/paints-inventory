package it.miniatures.paints.repository;

import it.miniatures.paints.entity.Paint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaintRepository extends JpaRepository<Paint, Long> {
    Optional<Paint> findByBrandAndColorName(String brand, String colorName);
}