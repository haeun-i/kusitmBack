package com.kusitms.kusitms5.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kusitms.kusitms5.domain.EventImage;
import com.kusitms.kusitms5.domain.Market;
import com.kusitms.kusitms5.domain.Store;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
public class StoreDto {
    String storeName;
    String storeAddress;
    String storePhone;
    String storeCategory;
    String marketName;
    boolean storeGiftcard;
    String userName; // 마지막으로 수정한 유저
    double storeScore;
    String storeLink;
    String storeTime;

    public StoreDto(String storeName, String storeAddress, String storePhone, String storeCategory, String marketName,
                    boolean storeGiftcard, double storeScore, String storeLink, String storeTime) {
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storePhone = storePhone;
        this.storeCategory = storeCategory;
        this.marketName = marketName;
        this.storeGiftcard = storeGiftcard;
        this.storeScore = storeScore;
        this.storeLink = storeLink;
        this.storeTime = storeTime;
    }

    public StoreDto() {

    }
    public StoreDto(Store store){
        storeName = store.getStoreName();
        storeAddress = store.getStoreAddress();
        storePhone = store.getStorePhone();
        storeCategory = store.getStoreCategory();
        marketName = store.getMarket().getMarketName();
        storeScore = store.getStoreScore();
        storeLink = store.getStoreLink();
        storeTime = store.getStoreTime();
    }

}
