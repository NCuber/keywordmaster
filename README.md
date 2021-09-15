# keywordmaster

키워드 마스터
네이버에서 제공하는 API 들과 셀레니움을 이용한 키워드 마스터

기능
- 특정 단어들의 연관 단어들을 검색하여 기기별 검색수, 클릭수, 클릭률과 경쟁력, 쇼핑 상품 수와 비율 등을 추출, 계산하여 엑셀로 저장할 수 있게 합니다.
- 쇼핑 인사이트의 특정 분야의 인기 검색어 추출 기능.

사용
- SPRING BOOT, MAVEN, AJAX 통신 등..
- 네이버 키워드 도구 API
- 네이버 검색 API
- 셀레니움

디렉토리
- /src/..
  - APIController.java : 네이버 api 사용
  - Category.java : 쇼핑 인사이트 카테고리
  - DemoApplication.java 
  - ExceptWord.java : 필터링 단어
  - InsightSearch.java : 쇼핑 인사이트 검색
  - JavaConfig.java 
  - MainController.java
  - ServletInitializer.java
  - Signatures.java : 시그니처 키 생성
  - /model
    - blogItem.java : 블로그 게시물 저장용
    - blogModel.java : 검색 API 저장용
    - calculatorModel.java : 최종 결과
    - keywordList.java : 키워드 도구 API 결과
    - relateModel.java : 키워드 도구 API 저장용
    
- /webapp/..
  - insight.jsp : 쇼핑인사이트 검색창
  - main.jsp : 키워드 마스터 검색창
