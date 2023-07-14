package com.guidetrack.mentorship_tracker.models;

import com.guidetrack.mentorship_tracker.models.basemodels.BaseUserModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "admins")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends BaseUserModel {

    @NotNull(message = "firstname cannot be null")
    private String firstname;

}