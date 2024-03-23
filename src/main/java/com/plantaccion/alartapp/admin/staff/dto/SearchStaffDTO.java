package com.plantaccion.alartapp.admin.staff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchStaffDTO {
    @JsonProperty("attribute")
    private String attribute;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
