package com.plantaccion.alartapp.common.model.app;

import com.plantaccion.alartapp.common.enums.LoginProvider;
import com.plantaccion.alartapp.common.enums.Roles;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "app_users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "staff_id", nullable = false, unique = true)
    private String staffId;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, columnDefinition = "ENUM('ADMIN', 'RCH', 'ICO')")
    @Enumerated(EnumType.STRING)
    private Roles role;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "ENUM('GOOGLE', 'AZURE', 'BASIC')")
    @Enumerated(EnumType.STRING)
    private LoginProvider provider;

    @Column(nullable = false, columnDefinition = "BIT DEFAULT b'0'")
    private boolean isDisabled;

    public AppUser() {
    }

    public AppUser(String staffId, String firstname, String lastname, String email, Roles role, String password) {
        this.staffId = staffId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public LoginProvider getProvider() {
        return provider;
    }

    public void setProvider(LoginProvider provider) {
        this.provider = provider;
    }
}
