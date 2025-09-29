package it.miniatures.paints.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class PaintRequest {
    @Schema(description = "Nome del brand", example = "Vallejo")
    @NotBlank
    private String colorName;
    @Schema(description = "Tipo di colore", example = "Game color")
    @NotBlank
    private String type;
    @Schema(description = "Nome del colore", example = "Rojo Sanguina")
    @NotBlank
    private String brand;
    @Schema(description = "Codice identificativo (opzionale)", example = "72.010")
    private String code;
    @Schema(description = "Quantità posseduta", example = "2")
    @Min(1)
    private int quantity = 1;


    public String getColorName() { return colorName; }
    public void setColorName(String colorName) { this.colorName = colorName; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}

