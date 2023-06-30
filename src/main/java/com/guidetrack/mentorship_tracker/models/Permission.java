package com.guidetrack.mentorship_tracker.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "permissions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Description is required")
    private String description;

//    @NotNull(message = "Permissions is required")
//    @NotBlank(message = "Permissions cannot be null")
    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;

    public Permission(@NotNull String name, @NotNull String description) {
        this.name = name;
        this.description = description;
    }
}
