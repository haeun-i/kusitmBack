package com.kusitms.kusitms5.dto;

import com.kusitms.kusitms5.domain.EventImage;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EventImageDto {
    private Long id;
    private String title;
    private String filePath;
    private boolean pay;

    public EventImage toEntity(){
        EventImage build = EventImage.builder()
                .id(id)
                .title(title)
                .filePath(filePath)
                .pay(pay)
                .build();
        return build;
    }

    @Builder
    public EventImageDto(String title, String filePath, boolean pay) {
        // this.id = id;
        this.title = title;
        this.filePath = filePath;
        this.pay = pay;
    }

}