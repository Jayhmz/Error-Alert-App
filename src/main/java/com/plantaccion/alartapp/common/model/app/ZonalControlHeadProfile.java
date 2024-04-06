package com.plantaccion.alartapp.common.model.app;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "zch_profile")
public class ZonalControlHeadProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "zch_staff_id", referencedColumnName = "staff_id", nullable = false)
    private AppUser staff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cluster_id", referencedColumnName = "cluster_name")
    private Cluster cluster;

    @Column(name="is_active", nullable = false, columnDefinition = "BIT DEFAULT b'1'")
    private boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "staff_id")
    private AppUser createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", referencedColumnName = "staff_id")
    private AppUser updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdOn = LocalDateTime.now();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime UpdatedOn;

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getStaff() {
        return staff;
    }

    public void setStaff(AppUser staff) {
        this.staff = staff;
    }

    public AppUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AppUser createdBy) {
        this.createdBy = createdBy;
    }

    public AppUser getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(AppUser updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return UpdatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        UpdatedOn = updatedOn;
    }
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
