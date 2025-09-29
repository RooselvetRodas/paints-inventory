package it.miniatures.paints.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "paints",
        uniqueConstraints = {
                @UniqueConstraint(name = "ux_paints_brand_name", columnNames = {"brand", "color_name"})
        }
)
public class Paint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "color_name", nullable = false, length = 150)
    @NotBlank
    private String colorName;
    @Column(nullable = false, length = 30)
    @NotBlank
    private String type;
    @Column(nullable = false, length = 100)
    @NotBlank
    private String brand;
    @Column(length = 50)
    private String code;
    @Column(nullable = false)
    @Min(0)
    private Integer quantity = 0;
    @Column(nullable = false)
    private LocalDateTime data;

    @PrePersist
    void prePersist(){
        if (data == null) data = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }

    @Override
    public String toString() {
        return "Paint{" +
                "id=" + id +
                ", colorName='" + colorName + '\'' +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", code='" + code + '\'' +
                ", quantity=" + quantity +
                ", data=" + data +
                '}';
    }
}
