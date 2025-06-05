package com.kathford.wms.payload.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationResponse {

    private String userRequestedName;
    private String address;
    private String longitude;
    private String latitude;

}
