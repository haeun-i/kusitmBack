package com.kusitms.kusitms5.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "event_image")
public class EventImage {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String filepath;

    @Column
    private boolean pay;

    @Builder
    public EventImage(Long id, String title, String filePath, boolean pay) {
        this.id = id;
        this.title = title;
        this.filepath = filePath;
        this.pay = pay;
    }
}