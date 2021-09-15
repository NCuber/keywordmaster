package com.example.gorokekeyword.model;


// 블로그 검색 결과, 검색 결과 총 개수, 저장 개수, 저장된 게시물 리스트
public class blogModel {
    int total;
    int display;

    blogItem[] items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public blogItem[] getItems() {
        return items;
    }

    public void setItems(blogItem[] items) {
        this.items = items;
    }
}
