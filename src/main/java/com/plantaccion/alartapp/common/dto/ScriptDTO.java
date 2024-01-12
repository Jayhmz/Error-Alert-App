package com.plantaccion.alartapp.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.List;

public class ScriptDTO {
    @JsonProperty("title")
    @NotNull(message = "Script title cannot be null")
    private String title;
    @JsonProperty("body")
    @NotNull(message = "Script cannot be null")
    private String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
