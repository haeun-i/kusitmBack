package com.kusitms.kusitms5.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.util.Set;


@Entity
@Table(name = "user_info")
@Getter // lombok어노테이션으로 get, set, 생성자를 생성함
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @JsonIgnore
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @JsonIgnore
    @Column(name = "user_pw", length = 100)
    private String password;

    @Column(name = "user_name", length = 50, unique = true)
    private String username;

    @Column(name = "user_phone")
    private String phone;

    @JsonIgnore
    @Column(name = "user_activated")
    private boolean activated;

    @Column(name = "user_nickname", length = 50)
    private String nickname;


    @OneToOne(mappedBy = "user")
    private Device deviceToken;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Love> loves = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Modify> modifies = new ArrayList<>();

    @Column(name = "user_click")
    private int userClick;

    public User(String nickname, String email){
        this.username = email;
        this.nickname = nickname;

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        authorities = Collections.singleton(authority);
    }

    public User update(String name, String email) {
        this.nickname = name;
        this.username = email;

        return this;
    }
}
