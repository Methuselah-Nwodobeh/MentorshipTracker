package com.guidetrack.mentorship_tracker.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "admins")
@Data
@AllArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "username cannot be null")
    private String username;

    @Email
    @NotNull(message = "email is required")
    private String email;

    @NotNull(message = "Password is required")
    @Length(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Date Created must be filled")
    @Column(name = "date_created", nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateModified;

     public Admin() {
        this.dateCreated = LocalDateTime.now();
    }

    public Admin(@NotNull String username, @NotNull String email, @NotNull String password) {
         this.username = username;
         this.email = email;
         this.password = password;
    }

    // PrePersist and PreUpdate callbacks

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateModified = LocalDateTime.now();
    }
}