package com.guidetrack.mentorship_tracker.models;

import com.guidetrack.mentorship_tracker.models.basemodels.BaseModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Description is required")
    private String description;

    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;

    public Permission(@NotNull String name, @NotNull String description) {
        this.name = name;
        this.description = description;
    }
}
