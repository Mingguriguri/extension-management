package com.madrascheck.extension_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "extension")
@Getter
@NoArgsConstructor
public class Extension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExtensionType type;

    @Column(nullable = false)
    private boolean enabled;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Constructor
     * */
    public Extension(String name, ExtensionType type) {
        this.name = name;
        this.type = type;
        this.enabled = false;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Setter
    * */
    public void changeName(String name) {
        this.name = name;
    }

    public void changeEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
