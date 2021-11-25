package com.kusitms.kusitms5.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kusitms.kusitms5.dto.StoreDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "storeInfo")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true) //pk 설정
    private Long storeId;

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(name = "store_address", nullable = false)
    private String storeAddress;

    @Column(name = "store_number", nullable = false)
    private String storePhone;

    @Column(name = "store_category", nullable = false)
    private String storeCategory;

    @Column(name = "store_giftcard", nullable = false)
    private boolean storeGiftcard;

    @Column(name = "store_score", nullable = false)
    private double storeScore;

    @Column(name = "store_link", nullable = false)
    private String storeLink;

    @Column(name = "store_time", nullable = false)
    private String storeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name = "market_id")
    private Market market;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Love> loves = new ArrayList<>();

    public void UpdateStore(StoreDto storeDto) {
        storeAddress = storeDto.getStoreAddress();
        storeCategory = storeDto.getStoreCategory();
        storeName = storeDto.getStoreName();
        storePhone = storeDto.getStorePhone();
        storeGiftcard = storeDto.isStoreGiftcard();
        storeLink = storeDto.getStoreLink();
        storeTime = storeDto.getStoreTime();
        storeScore = storeDto.getStoreScore();
    }
}
