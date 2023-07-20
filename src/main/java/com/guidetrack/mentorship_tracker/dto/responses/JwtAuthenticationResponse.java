package com.guidetrack.mentorship_tracker.dto.responses;

import com.guidetrack.mentorship_tracker.utils.constants.ResponseConstants;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private ResponseConstants status;
    private String refreshToken;
    private String accessToken;

    @Override
    public String toString(){
        return "{'status': "+status.name() + "\n" +
                "'data': {'access': " + accessToken + "\n" +
                "'refresh': " + refreshToken + "}";
    }
}