package com.guidetrack.mentorship_tracker.models;

import com.guidetrack.mentorship_tracker.models.basemodels.BaseUserModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "advisees")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Advisee extends BaseUserModel {
    @Temporal(TemporalType.DATE)
    @NotNull(message = "date of birth is required")
    private LocalDate dateOfBirth;

    @NotNull(message = "location is required")
    private String location;

    @ManyToOne
    @JoinColumn(name = "advisor_id")
    private Advisor advisor;
}
