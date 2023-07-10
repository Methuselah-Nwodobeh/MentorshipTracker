package com.guidetrack.mentorship_tracker.dao.responses;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String status;
    private String refreshToken;
    private String accessToken;

    @Override
    public String toString(){
        return "{'status': "+status + "\n" +
                "'data': {'access': " + accessToken + "\n" +
                "'refresh': " + refreshToken + "}";
    }
}