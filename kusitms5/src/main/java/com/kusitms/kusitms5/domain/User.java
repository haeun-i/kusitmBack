package com.kusitms.kusitms5.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
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

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

}
