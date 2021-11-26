package com.kusitms.kusitms5.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {
    String Response;

    ResponseDto(){

    }

    public ResponseDto(String s){
        this.Response = s;
    }
}
