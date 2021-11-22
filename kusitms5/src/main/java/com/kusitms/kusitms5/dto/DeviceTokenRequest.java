package com.kusitms.kusitms5.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DeviceTokenRequest {

    private String username;
    private String token;
}
