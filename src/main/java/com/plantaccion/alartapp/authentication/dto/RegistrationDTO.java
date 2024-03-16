package com.plantaccion.alartapp.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantaccion.alartapp.authentication.validation.StrongPassword;
import com.plantaccion.alartapp.authentication.validation.ValidEmail;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegistrationDTO {
    @JsonProperty("staffId")
    @NotNull(message = "staff id cannot be null")
    private Long staffId;
    @JsonProperty("firstname")
    @NotNull(message = "firstname cannot be null")
    private String firstname;
    @JsonProperty("lastname")
    @NotNull(message = "lastname cannot be null")
    private String lastname;
    @JsonProperty("email")
    @ValidEmail(message = "Enter a valid email")
    @NotNull(message = "Email cannot be null")
    private String email;
    @JsonProperty("password")
    @Size(min = 8, message = "Password less than 8 characters")
    @StrongPassword(message = "Password is not strong enough")
    @NotNull(message = "Email cannot be null")
    private String password;

    public RegistrationDTO(Long staffId,String firstname, String lastname, String email, String password) {
        this.staffId = staffId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
