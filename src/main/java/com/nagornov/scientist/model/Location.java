package com.nagornov.scientist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nagornov.scientist.util.JsonUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "locations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "location_id", insertable = false, updatable = false, nullable = false, unique = true)
    private Integer locationId;

    @Column(name = "country", updatable = false, nullable = false)
    private String country;

    @Column(name = "region", updatable = false, unique = true)
    private String region;

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    private Set<Biography> biographies = new HashSet<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Location(String country, String region) {
        this.country = country;
        this.region = region;
    }

    public Location(Integer locationId, String country, String region) {
        this.locationId = locationId;
        this.country = country;
        this.region = region;
    }

    @Override
    public String toString() {
        return JsonUtil.toJsonString(this);
    }

    public static Location fromString(String str) {
        return JsonUtil.fromJsonString(str, Location.class);
    }

}
