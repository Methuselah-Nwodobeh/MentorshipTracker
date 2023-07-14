package com.guidetrack.mentorship_tracker.models.basemodels;

import com.guidetrack.mentorship_tracker.models.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@EqualsAndHashCode(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseUserModel extends BaseModel {
    @NotNull(message = "username cannot be null")
    private String username;

    @Email
    @NotNull(message = "email is required")
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @NotNull(message = "Password is required")
    @Length(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Column(name = "verified", nullable = false)
    private boolean verified;
    }
