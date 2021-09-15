package com.example.gorokekeyword.model;

// 블로그 게시물 저장용, 제목, 작성자, 링크
public class blogItem {
    String title;
    String blogggername;
    String bloggerlink;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBlogggername() {
        return blogggername;
    }

    public void setBlogggername(String blogggername) {
        this.blogggername = blogggername;
    }

    public String getBloggerlink() {
        return bloggerlink;
    }

    public void setBloggerlink(String bloggerlink) {
        this.bloggerlink = bloggerlink;
    }

}
