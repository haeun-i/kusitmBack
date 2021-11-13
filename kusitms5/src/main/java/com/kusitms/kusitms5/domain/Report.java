package com.kusitms.kusitms5.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "reviewReport")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id", nullable = false, unique = true) //pk 설정
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(name = "report_memo")
    private String reportMemo;

    public static Report createReport(Review review, String memo){

        Report report = new Report();

        report.setReview(review);
        report.setReportMemo(memo);

        return report;
    }
}
