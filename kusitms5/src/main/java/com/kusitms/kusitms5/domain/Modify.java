package com.kusitms.kusitms5.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "store_modify")
public class Modify {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "modify_id", nullable = false, unique = true) //pk 설정
    private Long modifyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "modify_memo")
    private String modifyMemo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name = "user_id")
    private User user;

    public static Modify createModify(User user, Store store, String memo){

        Modify modify = new Modify();

        modify.setUser(user);
        modify.setStore(store);
        modify.setModifyMemo(memo);

        return modify;
    }

}
