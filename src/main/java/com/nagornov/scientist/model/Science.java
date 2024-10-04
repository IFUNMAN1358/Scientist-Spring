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
@Table(name = "sciences")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Science {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "science_id", insertable = false, updatable = false, nullable = false, unique = true)
    private Integer scienceId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "science")
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

    public Science(Integer scienceId, String name) {
        this.scienceId = scienceId;
        this.name = name;
    }

    @Override
    public String toString() {
        return JsonUtil.toJsonString(this);
    }

    public static Science fromString(String str) {
        return JsonUtil.fromJsonString(str, Science.class);
    }

}
