package com.guidetrack.mentorship_tracker.dto.responses;

import com.guidetrack.mentorship_tracker.utils.constants.ResponseConstants;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultResponse {
    private ResponseConstants status;
    private String data;

    @Override
    public String toString(){
        return "{'status': "+status.name() + "\n" +
                "'data': " + data +  "}";
    }
}

