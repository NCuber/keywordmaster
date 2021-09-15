package com.example.gorokekeyword.model;

// 키워드 도구 검색 결과
public class keywordList {

    String relKeyword; // 연관검색어
    String monthlyPcQcCnt; // 30일간 pc 검색수
    String monthlyMobileQcCnt; // 30일간 모바일 검색수
    String monthlyAvePcClkCnt;  // 4주간 평균 pc클릭수
    String monthlyAveMobileClkCnt; // 4주간 평균 모바일 클릭수
    String monthlyAvePcCtr; // 4주간 pc 클릭율
    String monthlyAveMobileCtr; // 4주간 모바일 클릭율
    String plAvgDepth; // 4주간 평균 pc광고 수
    String compIdx; //pc기반 광고 경쟁력

    public String getRelKeyword() {
        return relKeyword;
    }

    public void setRelKeyword(String relKeyword) {
        this.relKeyword = relKeyword;
    }

    public String getMonthlyPcQcCnt() {
        return monthlyPcQcCnt;
    }

    public void setMonthlyPcQcCnt(String monthlyPcQcCnt) {
        this.monthlyPcQcCnt = monthlyPcQcCnt;
    }

    public String getMonthlyMobileQcCnt() {
        return monthlyMobileQcCnt;
    }

    public void setMonthlyMobileQcCnt(String monthlyMobileQcCnt) {
        this.monthlyMobileQcCnt = monthlyMobileQcCnt;
    }

    public String getMonthlyAvePcClkCnt() {
        return monthlyAvePcClkCnt;
    }

    public void setMonthlyAvePcClkCnt(String monthlyAvePcClkCnt) {
        this.monthlyAvePcClkCnt = monthlyAvePcClkCnt;
    }

    public String getMonthlyAveMobileClkCnt() {
        return monthlyAveMobileClkCnt;
    }

    public void setMonthlyAveMobileClkCnt(String monthlyAveMobileClkCnt) {
        this.monthlyAveMobileClkCnt = monthlyAveMobileClkCnt;
    }

    public String getMonthlyAvePcCtr() {
        return monthlyAvePcCtr;
    }

    public void setMonthlyAvePcCtr(String monthlyAvePcCtr) {
        this.monthlyAvePcCtr = monthlyAvePcCtr;
    }

    public String getMonthlyAveMobileCtr() {
        return monthlyAveMobileCtr;
    }

    public void setMonthlyAveMobileCtr(String monthlyAveMobileCtr) {
        this.monthlyAveMobileCtr = monthlyAveMobileCtr;
    }

    public String getPlAvgDepth() {
        return plAvgDepth;
    }

    public void setPlAvgDepth(String plAvgDepth) {
        this.plAvgDepth = plAvgDepth;
    }

    public String getCompIdx() {
        return compIdx;
    }

    public void setCompIdx(String compIdx) {
        this.compIdx = compIdx;
    }

    @Override
    public String toString() {
        return "keywordList{" +
                "relKeyword='" + relKeyword + '\'' +
                ", monthlyPcQcCnt='" + monthlyPcQcCnt + '\'' +
                ", monthlyMobileQcCnt='" + monthlyMobileQcCnt + '\'' +
                ", monthlyAvePcClkCnt='" + monthlyAvePcClkCnt + '\'' +
                ", monthlyAveMobileClkCnt='" + monthlyAveMobileClkCnt + '\'' +
                ", monthlyAvePcCtr='" + monthlyAvePcCtr + '\'' +
                ", monthlyAveMobileCtr='" + monthlyAveMobileCtr + '\'' +
                ", plAvgDepth='" + plAvgDepth + '\'' +
                ", compIdx='" + compIdx + '\'' +
                '}';
    }
}
