package com.nagornov.scientist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nagornov.scientist.util.JsonUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "biographies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Biography {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "biography_id", insertable = false, updatable = false, nullable = false, unique = true)
    private Integer biographyId;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "degree", nullable = false)
    private String degree;

    @Column(name = "rank", nullable = false)
    private String rank;

    @ManyToOne
    @JoinColumn(name = "science_id", nullable = false)
    private Science science;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "content", length = 2000)
    private String content;

    @Column(name = "publications", length = 1000)
    private String publications;

    @Column(name = "is_confirmed", nullable = false)
    private Boolean isConfirmed = false;

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

    @Override
    public String toString() {
        return JsonUtil.toJsonString(this);
    }

    public static Biography fromString(String str) {
        return JsonUtil.fromJsonString(str, Biography.class);
    }

}
