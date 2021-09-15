package com.example.gorokekeyword;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

// 쇼핑 인사이트 분야별 인기검색어 추출.
// 셀레니움 사용
public class InsightSearch {

    private WebDriver driver;
    private WebElement element;
    private ChromeOptions options;

    public static String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static String WEB_DRIVER_PATH = "C:/chromedriver.exe";
    public static String URL = "https://datalab.naver.com/shoppingInsight/sCategory.naver";

    //생성자
    public InsightSearch() {
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        options = new ChromeOptions();
        options.setCapability("ignoreProtectedModeSettings", true);

    }

    // 인기검색어 추출, 셀레니움 작동
    public String[] search(String maintag, String twotag, String threetag, String fourtag, String start, String end, int maxcount, String[] device, String[] gender, String[] age){

        List<String> list = new ArrayList<String>();
        try {
        driver = new ChromeDriver(options);
        driver.get(URL);


        WebDriverWait wd = new WebDriverWait(driver, 20);

        List<WebElement> list_ul = driver.findElements(By.className("select_list"));
        List<WebElement> list_li;

        // 1분류
        wd.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[1]/div/div[1]"))).click();
        list_li = list_ul.get(0).findElements(By.tagName("li"));
        clicker_list(list_li, maintag);


        if(!twotag.equals("소분류 사용안함")){
            // 2분류
            wd.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[1]/div/div[2]"))).click();
            list_li = list_ul.get(1).findElements(By.tagName("li"));
            clicker_list(list_li, twotag);

            if(!threetag.equals("소분류 사용안함") && !threetag.equals("소분류 없음")){ // 조건 분류 없을때도
                // 3분류
                wd.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[1]/div/div[3]"))).click();
                WebElement list_ul3 = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[1]/div/div[3]/ul"));
                List<WebElement> list_li3 = list_ul3.findElements(By.tagName("li"));


                clicker_list(list_li3, threetag);
                if(!fourtag.equals("소분류 사용안함") && !fourtag.equals("소분류 없음")){
                    // 4분류
                    wd.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[1]/div/div[4]"))).click();
                    WebElement list_ul4 = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[1]/div/div[4]/ul"));
                    List<WebElement> list_li4 = list_ul4.findElements(By.tagName("li"));
                    clicker_list(list_li4, fourtag);
                }
            }
        }

        String[] date;

        // 시작 년 월 일 설정
        date = start.split("/");

        wd.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[2]/div[2]/span[1]/div[1]"))).click();
        list_li = list_ul.get(3).findElements(By.tagName("li"));
        clicker_list(list_li, date[0]);

        wd.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[2]/div[2]/span[1]/div[2]"))).click();
        list_li = list_ul.get(4).findElements(By.tagName("li"));
        clicker_list(list_li, date[1]);

        wd.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[2]/div[2]/span[1]/div[3]"))).click();
        list_li = list_ul.get(5).findElements(By.tagName("li"));
        clicker_list(list_li, date[2]);


        // 종료 년 월 일 설정
        date = end.split("/");

        wd.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[2]/div[2]/span[3]/div[1]"))).click();
        list_li = list_ul.get(6).findElements(By.tagName("li"));
        clicker_list(list_li, date[0]);

        wd.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[2]/div[2]/span[3]/div[2]"))).click();
        list_li = list_ul.get(7).findElements(By.tagName("li"));
        clicker_list(list_li, date[1]);

        wd.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[2]/div[2]/span[3]/div[3]"))).click();
        list_li = list_ul.get(8).findElements(By.tagName("li"));
        clicker_list(list_li, date[2]);




        // 기종
        if(device[0].equals("true"))
            clicker_check(By.xpath("//*[@id=\"18_device_1\"]"));
        if(device[1].equals("true"))
            clicker_check(By.xpath("//*[@id=\"18_device_2\"]"));

        // 성별
        if(gender[0].equals("true"))
            clicker_check(By.xpath("//*[@id=\"19_gender_1\"]"));
        if(gender[1].equals("true"))
            clicker_check(By.xpath("//*[@id=\"19_gender_2\"]"));

        // 나이대
        if(age[0].equals("true"))
            clicker_check(By.xpath("//*[@id=\"20_age_1\"]"));
        if(age[1].equals("true"))
            clicker_check(By.xpath("//*[@id=\"20_age_2\"]"));
        if(age[2].equals("true"))
            clicker_check(By.xpath("//*[@id=\"20_age_3\"]"));
        if(age[3].equals("true"))
            clicker_check(By.xpath("//*[@id=\"20_age_4\"]"));
        if(age[4].equals("true"))
            clicker_check(By.xpath("//*[@id=\"20_age_5\"]"));
        if(age[5].equals("true"))
            clicker_check(By.xpath("//*[@id=\"20_age_6\"]"));

        // 검색
        clicker_check(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/a"));
        Thread.sleep(2000);


        // 인기 검색어 단어 추출, 카운트
        int count = 0;
        for ( int i = 0; i < 25; i++) {
            List<WebElement> ul = driver.findElements(By.xpath("//*[@id=\"content\"]/div[2]/div/div[2]/div[2]/div/div/div[1]/ul/li"));

            for (WebElement li : ul) {
                String temp = li.findElement(By.tagName("a")).getText().split("\n")[1];
                System.out.println(count + " : " + temp);
                list.add(temp);
                count++;

                if (count >= maxcount) break;
            }

            if (count >= maxcount) break;

            clicker_check(By.xpath("//*[@id=\"content\"]/div[2]/div/div[2]/div[2]/div/div/div[2]/div/a[2]"));
            Thread.sleep(100);
        }
        driver.quit();

        } catch (InterruptedException e) {
            e.printStackTrace();
            driver.quit();
        }

        return list.toArray(new String[0]);
    }


    // 카테고리 추출용
    public void cate(){
        String k1, k2, k3;
        try {
            driver = new ChromeDriver(options);
            driver.get(URL);

            WebDriverWait wd = new WebDriverWait(driver, 20);

            // 1분류
            List<String> result1 = new ArrayList<>();
            wd.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[1]/div/div[1]"))).click();
            WebElement list_ul1 = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[1]/div/div[1]/ul"));
            List<WebElement> list_li1 = list_ul1.findElements(By.tagName("li"));
            Thread.sleep(1000);

            // 1분류 클릭 후, 2분류 리스트, 마지막에 1분류 단어 추가
            for (int a = list_li1.size()-1; a >= 0 ; a--)
            {

                List<String> result2 = new ArrayList<>();
                k1 = list_li1.get(a).findElement(By.tagName("a")).getText();
                list_li1.get(a).findElement(By.tagName("a")).click();
                Thread.sleep(100);

                wd.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[1]/div/div[2]"))).click();
                WebElement list_ul2 = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[1]/div/div[2]/ul"));
                List<WebElement> list_li2 = list_ul2.findElements(By.tagName("li"));

                // 2분류 클릭 후, 3분류 리스트, 마지막에 2분류 단어 추가
                for (int b = 0; b < list_li2.size(); b++)
                {

                    List<String> result3 = new ArrayList<>();
                    k2 = list_li2.get(b).findElement(By.tagName("a")).getText();
                    list_li2.get(b).findElement(By.tagName("a")).click();
                    Thread.sleep(100);

                    // 3분류가 있는지 확인
                    if (!driver.findElements(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[1]/div/div[3]")).isEmpty()) {
                        wd.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[1]/div/div[3]"))).click();
                        WebElement list_ul3 = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[1]/div/div[3]/ul"));
                        List<WebElement> list_li3 = list_ul3.findElements(By.tagName("li"));

                        // 3분류 클릭 후, 4분류 리스트, 마지막에 3분류 단어 추가
                        for (int c = 0; c < list_li3.size(); c++)
                        {

                            List<String> result4 = new ArrayList<>();
                            k3 = list_li3.get(c).findElement(By.tagName("a")).getText();
                            list_li3.get(c).findElement(By.tagName("a")).click();
                            Thread.sleep(100);

                            // 4분류 있는지 확인
                            if (!driver.findElements(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[1]/div/div[4]")).isEmpty()) {
                                wd.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[1]/div/div[4]"))).click();
                                WebElement list_ul4 = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[1]/div/div[4]/ul"));
                                List<WebElement> list_li4 = list_ul4.findElements(By.tagName("li"));

                                // 4분류 클릭 후 4분류 리스트 저장
                                for (int d = 0; d < list_li4.size(); d++)
                                {
                                    result4.add(list_li4.get(d).findElement(By.tagName("a")).getText());
                                }

                                System.out.println(k1 + " - " + k2 + " - " + k3 + " # sub" + String.format("%02d", a+1) + String.format("%02d", b+1) + String.format("%02d", c+1) + " = " + result4);
                                result4.clear();

                            }
                            wd.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[1]/div/div[3]"))).click();
                            Thread.sleep(100);
                            result3.add(list_li3.get(c).findElement(By.tagName("a")).getText());
                        }
                        System.out.println(k1 + " - " + k2 + " # sub" + String.format("%02d", a+1) + String.format("%02d", b+1) + "00 = " + result3);
                        result3.clear();

                    }
                    wd.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[1]/div/div[2]"))).click();
                    Thread.sleep(100);
                    result2.add(list_li2.get(b).findElement(By.tagName("a")).getText());
                }
                System.out.println(k1 + " # sub" + String.format("%02d", a+1) + "0000 = " + result2);
                result2.clear();

                wd.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/div[2]/div/div[1]/div/div/div[1]/div/div[1]"))).click();
                Thread.sleep(100);
                result1.add(list_li1.get(a).findElement(By.tagName("a")).getText());
            }

            System.out.println(" 상위 : " + result1);
            driver.quit();

        }catch(InterruptedException e){
            driver.quit();
        }
    }




    // 리스트 클릭
    public void clicker_list(List<WebElement> list, String text)
    {
        for(WebElement i:list)
        {
            System.out.println(i.findElement(By.tagName("a")).getText() + " - " + text);

            if(i.findElement(By.tagName("a")).getText().equals(text))
            {
                i.findElement(By.tagName("a")).click();
                break;
            }
        }
    }

    // 체크박스 클릭
    public void clicker_check(By address)
    {
        element = driver.findElement(address);
        element.click();
    }

}
