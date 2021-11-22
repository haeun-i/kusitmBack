package com.kusitms.kusitms5.domain;

import lombok.AllArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "device")
public class Device {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Device() {
    }

    public Device(Long id, User user, String token) {
        this.id = id;
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "device_token")
    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
