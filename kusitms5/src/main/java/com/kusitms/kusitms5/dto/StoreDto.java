package com.kusitms.kusitms5.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    public StoreDto(String storeName, String storeAddress, String storePhone, String storeCategory, String marketName, boolean storeGiftcard) {
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storePhone = storePhone;
        this.storeCategory = storeCategory;
        this.marketName = marketName;
        this.storeGiftcard = storeGiftcard;
    }

    public StoreDto() {

    }
    public StoreDto(Store store){
        storeName = store.getStoreName();
        storeAddress = store.getStoreAddress();
        storePhone = store.getStorePhone();
        storeCategory = store.getStoreCategory();
        marketName = store.getMarket().getMarketName();
    }
}
