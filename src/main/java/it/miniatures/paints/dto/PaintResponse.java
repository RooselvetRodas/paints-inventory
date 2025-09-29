package it.miniatures.paints.dto;

import java.time.LocalDateTime;

public class PaintResponse {
    private Long id;
    private String colorName;
    private String type;
    private String brand;
    private String code;
    private Integer quantity;
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
