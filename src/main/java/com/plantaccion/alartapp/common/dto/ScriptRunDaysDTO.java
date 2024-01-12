package com.plantaccion.alartapp.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class ScriptRunDaysDTO {
    @JsonProperty("day")
    @NotNull(message = "Enter a valid day between Monday to Sunday")
    private String day;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
