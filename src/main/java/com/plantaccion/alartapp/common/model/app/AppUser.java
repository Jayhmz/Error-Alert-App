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
    private Long staffId;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, columnDefinition = "ENUM('ADMIN', 'ZCH', 'ICO')")
    @Enumerated(EnumType.STRING)
    private Roles role;

    @Column(name = "is_disabled", nullable = false, columnDefinition = "BIT DEFAULT b'0'")
    private boolean isDisabled;

    public AppUser() {
    }

    public AppUser(Long staffId, String email, Roles role) {
        this.staffId = staffId;
        this.email = email;
        this.role = role;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }
}
