package com.plantaccion.alartapp.admin.scripts.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class ScriptResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("body")
    private String body;
    @JsonProperty("createdBy")
    private Map<String, Object> createdBy;

    public ScriptResponse(Long id,String title, String body,
                          Map<String, Object> createdBy) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.createdBy = createdBy;
    }

}
