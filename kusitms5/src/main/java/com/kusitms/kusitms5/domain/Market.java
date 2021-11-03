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
    @GeneratedValue
    @Column(name = "market_id", nullable = false, unique = true) //pk 설정
    private Long marketId;

    @Column(name = "market_name", nullable = false)
    private String marketName;

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Store> stores = new ArrayList<>();
}
