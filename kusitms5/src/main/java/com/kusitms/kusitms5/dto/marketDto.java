package com.kusitms.kusitms5.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kusitms.kusitms5.domain.Market;
import com.kusitms.kusitms5.domain.Store;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class marketDto {
    String marketName;
    int storeCnt;

    public marketDto(Market market){
        marketName = market.getMarketName();
        storeCnt = market.getStoreCnt();
    }

}
