package it.miniatures.paints.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class PaintResponse {
    @Schema(description = "ID univoco", example = "1")
    private Long id;
    @Schema(description = "Nome del colore", example = "Abaddon Black")
    private String colorName;
    @Schema(description = "Tipo di vernice", example = "Base")
    private String type;
    @Schema(description = "Brand della vernice", example = "Citadel")
    private String brand;
    @Schema(description = "Codice identificativo", example = "21-12")
    private String code;
    @Schema(description = "Quantità disponibile", example = "3")
    private Integer quantity;
    @Schema(description = "Data di creazione o aggiornamento", example = "2025-09-29T15:20:00")
    private LocalDateTime data;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getColorName() { return colorName; }
    public void setColorName(String colorName) { this.colorName = colorName; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
}
