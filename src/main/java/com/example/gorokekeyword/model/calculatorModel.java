package com.example.gorokekeyword.model;


// 최종 결과 저장
// 인덱스 번호, 검색된 상품 갯수, 상품 비율
// 블로그 게시물 수, 비율은 미사용으로 인해 주석 처리
public class calculatorModel {


    int number;
    int shop_result;
    float shop_ratio;
    keywordList keyword_list;
    ////int blog_result;
    ////float blog_ratio;

    public keywordList getKeyword_list() {
        return keyword_list;
    }
    public void setKeyword_list(keywordList keyword_list) {
        this.keyword_list = keyword_list;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public int getShop_result() {
        return shop_result;
    }
    public void setShop_result(int shop_result) {
        this.shop_result = shop_result;
    }

    public float getShop_ratio() {
        return shop_ratio;
    }
    public void setShop_ratio(float shop_ratio) {
        this.shop_ratio = shop_ratio;
    }
        /*
    public int getBlog_result() {
        return blog_result;
    }

    public void setBlog_result(int blog_result) {
        this.blog_result = blog_result;
    }
    public float getBlog_ratio() {
        return blog_ratio;
    }

    public void setBlog_ratio(float blog_ratio) {
        this.blog_ratio = blog_ratio;
    }
*/
}
