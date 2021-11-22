package com.kusitms.kusitms5.dto;

import com.kusitms.kusitms5.domain.Review;
import lombok.*;

@Getter
@Setter
@Builder
public class TokenDto {

    private String token;
    private int click_cnt;

    public TokenDto(String token, int click){
        this.token = token;
        this.click_cnt = click;
    }
}

