package com.kusitms.kusitms5.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "reviewInfo")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false, unique = true) //pk 설정
    private Long reviewId;

    @Column(name = "review_memo")
    private String reviewMemo;

    @Column(name = "review_score")
    private double reviewScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Report> reports = new ArrayList<>();

    @OneToOne(mappedBy = "review", cascade = CascadeType.ALL)
    @JsonBackReference
    private ReviewImage image;

    public static Review createReview(User user, Store store, String memo, double score){

        Review review = new Review();
        review.setUser(user);
        review.setStore(store);
        review.setReviewMemo(memo);
        review.setReviewScore(score);

        return review;
    }
}
