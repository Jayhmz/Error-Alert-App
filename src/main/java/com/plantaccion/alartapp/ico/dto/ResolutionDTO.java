package com.plantaccion.alartapp.ico.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ResolutionDTO {
    @JsonProperty("resolution")
    @NotNull(message = "Kindly Input your remark for this review")
    @Min(value = 10, message = "Review remark is too short")
    private String resolution;

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}
