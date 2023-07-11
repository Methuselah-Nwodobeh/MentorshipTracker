package com.guidetrack.mentorship_tracker.dto.responses;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponse {
    private String status;
    private String data;

    @Override
    public String toString(){
        return "{'status': "+status + "\n" +
                "'data': " + data +  "}";
    }
}

