package com.kusitms.kusitms5.dto;

import com.kusitms.kusitms5.domain.User;

public class QuestionDto {

    private Long id;

    private String title;

    private String content;

    private String username;

    public QuestionDto(){

    }
    public QuestionDto(Long id, String title, String content, String username) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
