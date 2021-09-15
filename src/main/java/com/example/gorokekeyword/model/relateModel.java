package com.example.gorokekeyword.model;

import java.util.List;

// 키워드 도구 API는 리스트로 받아야 하므로 따로 모델 생성
public class relateModel {
    List<keywordList> keywordList;

    public List<keywordList> getKeywordList() {
        return keywordList;
    }
    public void setKeywordList(List<keywordList> keywordList) {
        this.keywordList = keywordList;
    }
}


