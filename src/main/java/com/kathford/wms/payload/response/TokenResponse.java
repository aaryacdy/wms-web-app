package com.kathford.wms.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenResponse {

    private Long id;

    private String role;

    private String token;
}
