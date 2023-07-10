package com.guidetrack.mentorship_tracker.models;

import com.guidetrack.mentorship_tracker.models.basemodels.BaseUserModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "advisors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Advisor extends BaseUserModel {
    @Temporal(TemporalType.DATE)
    @NotNull(message = "date of birth is required")
    private LocalDate dateOfBirth;

    @NotNull(message = "location is required")
    private String location;

    @OneToMany
    @JoinTable(name = "advisor_advisees",
            joinColumns = @JoinColumn(name = "advisor_id"),
            inverseJoinColumns = @JoinColumn(name = "advisee_id")
    )
    private List<Advisee> advisees;
}
