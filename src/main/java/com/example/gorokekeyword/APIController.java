package com.example.gorokekeyword;

import com.example.gorokekeyword.model.blogModel;
import com.example.gorokekeyword.model.relateModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;

// API 사용 클래스
public class APIController {
    private RestTemplate restTemplate;

    // API 키 입력.
    private String clientId_search =  "Input your ID";
    private String clientSecret_search =  "Input your Secret";

    private String clientId_relate =  "Input your ID";
    private String clientSecret_relate =  "Input your Secret";
    private String customerId_relate = "Input your customerID";
    private String signatureID_relate = null;

    // 생성자
    public APIController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        System.out.println("api 컨트롤러 성공");
    }

    // 블로그, 쇼핑 검색 API
    public blogModel search(String keyword, String cate) {
        System.out.println("키워드 검색 : " + keyword);

        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("openapi.naver.com")
                .path("v1/search/"+cate)
                .queryParam("query","{a}")
                .encode(StandardCharsets.UTF_8)
                .buildAndExpand(keyword);

        // 헤더 정보 주입
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId_search);
        headers.set("X-Naver-Client-Secret", clientSecret_search);
        Charset utf8 = Charset.forName("UTF-8");
        MediaType type = new MediaType("application", "json", utf8);
        headers.setContentType(type);


        final HttpEntity<String> entity = new HttpEntity<>(headers);

        System.out.println(uri.toString());
        blogModel result = new blogModel();

        try {
            result = restTemplate.exchange(uri.toUri(), HttpMethod.GET, entity, blogModel.class).getBody();
        }
        catch(HttpClientErrorException e){
            System.out.println("요청 빈도수 오류 발생, 0.5초 대기합니다.");

            try {
                Thread.sleep(500);
                result = search(keyword, cate);

            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }

        }
        catch(Exception e){
            System.out.println("알수 없는 오류 발생, 3초 대기합니다.");
            e.printStackTrace();

            try {
                Thread.sleep(3000);
                result = search(keyword, cate);

            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        return result;

    }

    public relateModel relate(String keyword)
    {
        System.out.println("관련 키워드 검색 : " + keyword);
        String time = Long.toString(System.currentTimeMillis());

        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.naver.com")
                .path("keywordstool")
                .queryParam("hintKeywords", "{a}")
                .queryParam("includeHintKeywords",1)
                .queryParam("showDetail",1)
                .encode(StandardCharsets.UTF_8)
                .buildAndExpand(keyword);

        String path = "/keywordstool";

        try {
            signatureID_relate = Signatures.of(time, "GET", path, clientSecret_relate);
        } catch (SignatureException e) {
            e.printStackTrace();
            System.out.println("시그니처 생성 실패");
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-Timestamp", time);
        headers.set("X-API-KEY", clientId_relate);
        headers.set("X-Customer", customerId_relate);
        headers.set("X-Signature", signatureID_relate);

        final HttpEntity<String> entity = new HttpEntity<>(headers);
        System.out.println(uri.toString());
        relateModel result = new relateModel();

        try {
            result = restTemplate.exchange(uri.toUri(), HttpMethod.GET, entity, relateModel.class).getBody();
        }
        catch(HttpClientErrorException e){
            System.out.println("관련 검색어 오류 발생");

            try {
                Thread.sleep(500);
                result = relate(keyword);

            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }

        }
        return result;
    }

}
