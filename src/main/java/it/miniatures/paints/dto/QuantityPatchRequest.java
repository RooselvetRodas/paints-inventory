package it.miniatures.paints.dto;

import jakarta.validation.constraints.NotNull;

public class QuantityPatchRequest {
    @NotNull
    private Integer delta; // può essere negativo per scalare

    public Integer getDelta() { return delta; }
    public void setDelta(Integer delta) { this.delta = delta; }
}
