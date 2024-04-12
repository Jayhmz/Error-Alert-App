package com.plantaccion.alartapp.common.model.app;

import com.plantaccion.alartapp.common.model.app.AppUser;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "scripts")
public class Script {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String title;
    @Column(nullable = false)
    private String body;
    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "staff_id")
    private AppUser createdBy;
    @Column(name = "is_active", nullable = false, columnDefinition = "BIT DEFAULT b'0'")
    private boolean isActive;

    public Script() {
    }

    public Script(String title, String body, AppUser createdBy) {
        this.title = title;
        this.body = body;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public AppUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AppUser createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
