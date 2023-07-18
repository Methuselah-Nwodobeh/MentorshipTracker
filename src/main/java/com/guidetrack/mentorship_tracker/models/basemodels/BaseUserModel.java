package com.guidetrack.mentorship_tracker.models.basemodels;

import com.guidetrack.mentorship_tracker.models.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp="[A-Za-z0-9]+", message="Username can only contain letters and digits")
    private String username;

    @Email
    @NotNull(message = "email is required")
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,253}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,253}[a-zA-Z0-9])?)*$", message = "Invalid email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @NotNull(message = "Password is required")
    @Length(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$&*~]).{8,}$", message = """
            Invalid password:
            At least 9 characters long
            Contains at least one uppercase letter
            Contains at least one digit
            Contains at least one special character (e.g. !@#$%^&*)
            """)
    private String password;

    @Column(name = "verified", nullable = false)
    private boolean verified;
    }
