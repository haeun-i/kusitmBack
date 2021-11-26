package com.kusitms.kusitms5.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "marketInfo")
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "market_id", nullable = false, unique = true) //pk 설정
    private Long marketId;

    @Column(name = "market_name", nullable = false)
    private String marketName;

    @Column(name = "store_cnt")
    private int storeCnt; // 점포 수

    @Column(name = "market_type", nullable = false)
    private int marketType; // 5일장 여부

    @Column(name = "market_address", nullable = false)
    private String marketAddress; // 5일장 여부

    @Column(name = "market_click", nullable = false)
    private int marketClick;

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Store> stores = new ArrayList<>();

}
