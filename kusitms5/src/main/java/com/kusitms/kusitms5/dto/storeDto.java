package com.kusitms.kusitms5.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kusitms.kusitms5.domain.Market;
import com.kusitms.kusitms5.domain.Store;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
public class storeDto {
    String storeName;
    String storeAddress;
    String storePhone;
    String storeCategory;
    String marketName;
    String userName; // 마지막으로 수정한 유저

    public storeDto(Store store){
        storeName = store.getStoreName();
        storeAddress = store.getStoreAddress();
        storePhone = store.getStorePhone();
        storeCategory = store.getStoreCategory();
        marketName = store.getMarket().getMarketName();
    }
}
