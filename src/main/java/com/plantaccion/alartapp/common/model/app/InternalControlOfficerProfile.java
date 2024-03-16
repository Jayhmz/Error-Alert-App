package com.plantaccion.alartapp.common.model.app;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ico_profile")
public class InternalControlOfficerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ico_staff_id", referencedColumnName = "staff_id", nullable = false)
    private AppUser icoStaff;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by", referencedColumnName = "zch_staff_id", nullable = false)
    private ZonalControlHeadProfile supervisor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "updated_by", referencedColumnName = "zch_staff_id")
    private ZonalControlHeadProfile updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdOn = LocalDateTime.now();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime UpdatedOn;

    @Column(nullable = false, columnDefinition = "BIT DEFAULT b'0'")
    private boolean isDisabled;

    public ZonalControlHeadProfile getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(ZonalControlHeadProfile supervisor) {
        this.supervisor = supervisor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public AppUser getIcoStaff() {
        return icoStaff;
    }

    public void setIcoStaff(AppUser icoStaff) {
        this.icoStaff = icoStaff;
    }

    public ZonalControlHeadProfile getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(ZonalControlHeadProfile updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedOn() {
        return UpdatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        UpdatedOn = updatedOn;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }
}
