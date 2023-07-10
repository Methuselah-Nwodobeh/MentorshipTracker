package com.guidetrack.mentorship_tracker.models;

import com.guidetrack.mentorship_tracker.models.basemodels.BaseUserModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "admins")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends BaseUserModel {

    @NotNull(message = "firstname cannot be null")
    private String firstname;

    public Admin(@NotNull String username, @NotNull String firstname, @NotNull String email, @NotNull String password, Role role) {
        super();
        this.setUsername(username);
        this.setEmail(email);
        this.setPassword(password);
        this.setVerified(false);
        this.setRole(role);
        this.firstname = firstname;
    }
}