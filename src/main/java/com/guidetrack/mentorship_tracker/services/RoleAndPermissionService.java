package com.guidetrack.mentorship_tracker.services;

import com.guidetrack.mentorship_tracker.dto.requests.*;
import com.guidetrack.mentorship_tracker.dto.responses.DefaultResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

public interface RoleAndPermissionService {
    DefaultResponse create(@Valid RoleAndPermissionRequest request);

    DefaultResponse updateName(@Valid GenericUpdateRequest<String, String> request);

    DefaultResponse updateDescription(@Valid GenericUpdateRequest<String, String> request);

    DefaultResponse delete(@NotBlank @RequestBody String name);

    DefaultResponse read(@NotBlank @RequestBody String name);

    DefaultResponse readAll();

    DefaultResponse join(GenericUpdateRequest<String, Set<String>> request);
}
