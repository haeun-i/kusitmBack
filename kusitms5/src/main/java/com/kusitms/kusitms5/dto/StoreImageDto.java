package com.kusitms.kusitms5.dto;

import com.kusitms.kusitms5.domain.Store;
import com.kusitms.kusitms5.domain.StoreImage;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class StoreImageDto {
    private Long id;
    private String title;
    private String filePath;
    private Store store;

    public StoreImage toEntity(){
        StoreImage build = StoreImage.builder()
                .id(id)
                .title(title)
                .filePath(filePath)
                .store(store)
                .build();
        return build;
    }

    @Builder
    public StoreImageDto(String title, String filePath, Store store) {
        //this.id = id;
        this.title = title;
        this.filePath = filePath;
        this.store = store;
    }
}