package com.example.gorokekeyword;
import com.example.gorokekeyword.model.calculatorModel;
import com.example.gorokekeyword.model.keywordList;
import com.example.gorokekeyword.model.relateModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller

public class MainController {
    List<calculatorModel> calculator_result;
    ExceptWord exceptWord;
    InsightSearch insightSearch;
    Category category;
    private AnnotationConfigApplicationContext ctx;
    private final APIController apicontroller;
    private Boolean searching;

    // 생성자, Autuwired로 대체 가능
    public MainController() {
        ctx = new AnnotationConfigApplicationContext(JavaConfig.class);
        this.apicontroller = (APIController) ctx.getBean("apicontroller");
        this.insightSearch = (InsightSearch) ctx.getBean("insightSearch");
        this.calculator_result = (List<calculatorModel>) ctx.getBean("calculatorModel");
        this.exceptWord = (ExceptWord) ctx.getBean("exceptWord");
        this.category = (Category) ctx.getBean("category");
        searching = false;
        System.out.println("메인 컨트롤러 성공");
    }


    // 메인 페이지
    @GetMapping("/")
    public String mainPage()
    {
        Date date = new Date();
        System.out.println(date);
        return "main";
    }

    // 검색 결과 및 검색 페이지
    @PostMapping("/search")
    public String searchKeyword(Model model, @RequestParam(value = "input_keyword") String keywords, @RequestParam(value = "input_number") String number)
    {
        // 값 초기화, 검색 단어 나누기
        int count;
        int max = Integer.parseInt(number);
        String[] list = keywords.split("\n");
        ArrayList<String> keyword_list = new ArrayList<String>();
        calculator_result.clear();
        searching = true;

        // 검색
        for(String keyword: list) {
            try {
                // 공백제거, 개행문자 포함 공백 단어 패스, 검색 중지 확인
                keyword.replaceAll(" ", "");
                if(keyword.trim().equals("")) continue;
                if(searching == false) break;

                // 단어 개행문자 제거, 검색할 연관 단어 갯수 정의 후 키워드 도구 검색 실행
                keyword = keyword.trim();
                max = Integer.parseInt(number);
                count = 0;

                Thread.sleep(150);
                relateModel relate_results = apicontroller.relate(keyword);

                if (relate_results.getKeywordList().size() < max)
                    max = relate_results.getKeywordList().size();

                for (keywordList item : relate_results.getKeywordList()) {

                    // 필터, 중복, 중지 확인 후 스토어 검색 실행
                    if(searching == false) break;
                    if(keyword_list.contains(item.getRelKeyword()) || !exceptWord.filter(item.getRelKeyword()))
                        continue;
                    else
                        keyword_list.add(item.getRelKeyword());

                    Thread.sleep(150);
                    int shop_temp = apicontroller.search(item.getRelKeyword(), "shop").getTotal();

                    float pc_count = 0;
                    float mobile_count = 0;

                    // 키워드 도구 API 사용의 경우 10회 이하는 '< 10'로 표시, 계산을 위해 5로 치환
                    if (item.getMonthlyPcQcCnt().equals("< 10")) {
                        pc_count = 5;
                        item.setMonthlyPcQcCnt("5");
                    } else
                        pc_count = Float.parseFloat(item.getMonthlyPcQcCnt());

                    if (item.getMonthlyMobileQcCnt().equals("< 10")) {
                        mobile_count = 5;
                        item.setMonthlyMobileQcCnt("5");
                    } else
                        mobile_count = Float.parseFloat(item.getMonthlyMobileQcCnt());

                    // 값 주입
                    calculatorModel temp = new calculatorModel();
                    temp.setNumber(count + 1);
                    temp.setKeyword_list(item);
                    // temp.setBlog_ratio(blog_temp / (pc_count + mobile_count));
                    temp.setShop_ratio(shop_temp / (pc_count + mobile_count));
                    // temp.setBlog_result(blog_temp);
                    temp.setShop_result(shop_temp);
                    calculator_result.add(temp);

                    if (++count >= max) break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("calculator_result", calculator_result);
        return "main";
    }

    // 엑셀 다운로드
    @GetMapping("/download")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("첫번째 시트");
        for(int i = 0; i < 15; i++) {
            sheet.setColumnWidth(i, 3500);
        }
        Row row = null;
        Cell cell = null;
        int rowIndex = 1;

        Font font = wb.createFont();
        font.setFontHeight((short)(13*20));
        font.setFontName("맑은 고딕");
        font.setBold(true);

        Font font2 = wb.createFont();
        font2.setFontName("맑은 고딕");

        CellStyle color_style = wb.createCellStyle();
        CellStyle line_style = wb.createCellStyle();
        CellStyle align_style = wb.createCellStyle();


        color_style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        color_style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        color_style.setAlignment(HorizontalAlignment.CENTER);
        color_style.setFont(font);
        color_style.setWrapText(true);

        line_style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        line_style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        line_style.setBorderBottom(BorderStyle.MEDIUM);
        line_style.setAlignment(HorizontalAlignment.CENTER);
        line_style.setWrapText(true);
        line_style.setFont(font);

        align_style.setAlignment(HorizontalAlignment.CENTER);
        align_style.setFont(font2);

        row = sheet.createRow(rowIndex++);


        sheet.addMergedRegion(new CellRangeAddress(1,2,1,1));
        sheet.addMergedRegion(new CellRangeAddress(1,2,2,2));
        sheet.addMergedRegion(new CellRangeAddress(1,1,3,4));
        sheet.addMergedRegion(new CellRangeAddress(1,1,5,6));
        sheet.addMergedRegion(new CellRangeAddress(1,1,7,8));
        sheet.addMergedRegion(new CellRangeAddress(1,2,9,9));
        sheet.addMergedRegion(new CellRangeAddress(1,2,10,10));
        sheet.addMergedRegion(new CellRangeAddress(1,2,11,11));
//        sheet.addMergedRegion(new CellRangeAddress(1,2,12,12));
//        sheet.addMergedRegion(new CellRangeAddress(1,2,13,13));

        cell = row.createCell(1);
        cell.setCellStyle(color_style);
        cell.setCellValue("번호");

        cell = row.createCell(2);
        cell.setCellStyle(color_style);
        cell.setCellValue("검색어");

        cell = row.createCell(3);
        cell.setCellStyle(color_style);
        cell.setCellValue("검색수");

        cell = row.createCell(4);
        cell.setCellStyle(color_style);

        cell = row.createCell(5);
        cell.setCellStyle(color_style);
        cell.setCellValue("클릭수");

        cell = row.createCell(6);
        cell.setCellStyle(color_style);


        cell = row.createCell(7);
        cell.setCellStyle(color_style);
        cell.setCellValue("클릭률");

        cell = row.createCell(8);
        cell.setCellStyle(color_style);


        cell = row.createCell(9);
        cell.setCellStyle(color_style);
        cell.setCellValue("경쟁력");

        cell = row.createCell(10);
        cell.setCellStyle(color_style);
        cell.setCellValue("쇼핑\n 상품 수");

        cell = row.createCell(11);
        cell.setCellStyle(color_style);
        cell.setCellValue("쇼핑\n 상품 비율");

//        cell = row.createCell(12);
//        cell.setCellStyle(color_style);
//        cell.setCellValue("블로그\n 게시물 수");
//
//        cell = row.createCell(13);
//        cell.setCellStyle(color_style);
//        cell.setCellValue("블로그\n 게시물 비율");

        row = sheet.createRow(rowIndex++);
        cell = row.createCell(1);
        cell.setCellStyle(line_style);

        cell = row.createCell(2);
        cell.setCellStyle(line_style);

        cell = row.createCell(3);
        cell.setCellStyle(line_style);
        cell.setCellValue("PC");

        cell = row.createCell(4);
        cell.setCellStyle(line_style);
        cell.setCellValue("MOBILE");

        cell = row.createCell(5);
        cell.setCellStyle(line_style);
        cell.setCellValue("PC");

        cell = row.createCell(6);
        cell.setCellStyle(line_style);
        cell.setCellValue("MOBILE");

        cell = row.createCell(7);
        cell.setCellStyle(line_style);
        cell.setCellValue("PC");

        cell = row.createCell(8);
        cell.setCellStyle(line_style);
        cell.setCellValue("MOBILE");

        cell = row.createCell(9);
        cell.setCellStyle(line_style);

        cell = row.createCell(10);
        cell.setCellStyle(line_style);

        cell = row.createCell(11);
        cell.setCellStyle(line_style);

//        cell = row.createCell(12);
//        cell.setCellStyle(line_style);
//
//        cell = row.createCell(13);
//        cell.setCellStyle(line_style);

        for(calculatorModel data : calculator_result)
        {

            row = sheet.createRow(rowIndex++);

            cell = row.createCell(1);
            cell.setCellValue(data.getNumber());
            cell.setCellStyle(align_style);

            cell = row.createCell(2);
            cell.setCellValue(data.getKeyword_list().getRelKeyword());
            cell.setCellStyle(align_style);

            cell = row.createCell(3);
            cell.setCellValue(Integer.parseInt(data.getKeyword_list().getMonthlyPcQcCnt()));
            cell.setCellStyle(align_style);

            cell = row.createCell(4);
            cell.setCellValue(Integer.parseInt(data.getKeyword_list().getMonthlyMobileQcCnt()));
            cell.setCellStyle(align_style);

            cell = row.createCell(5);
            cell.setCellValue(Double.parseDouble(data.getKeyword_list().getMonthlyAvePcClkCnt()));
            cell.setCellStyle(align_style);

            cell = row.createCell(6);
            cell.setCellValue(Double.parseDouble(data.getKeyword_list().getMonthlyAveMobileClkCnt()));
            cell.setCellStyle(align_style);

            cell = row.createCell(7);
            cell.setCellValue(Double.parseDouble(data.getKeyword_list().getMonthlyAvePcCtr()));
            cell.setCellStyle(align_style);

            cell = row.createCell(8);
            cell.setCellValue(Double.parseDouble(data.getKeyword_list().getMonthlyAveMobileCtr()));
            cell.setCellStyle(align_style);

            cell = row.createCell(9);
            cell.setCellValue(data.getKeyword_list().getCompIdx());
            cell.setCellStyle(align_style);

            cell = row.createCell(10);
            cell.setCellValue(data.getShop_result());
            cell.setCellStyle(align_style);

            cell = row.createCell(11);
            cell.setCellValue(data.getShop_ratio());
            cell.setCellStyle(align_style);

//            cell = row.createCell(12);
//            cell.setCellValue(data.getBlog_result());
//            cell.setCellStyle(align_style);
//
//            cell = row.createCell(13);
//            cell.setCellValue(data.getBlog_ratio());
//            cell.setCellStyle(align_style);

        }
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + timeStamp + " result.xlsx");

        wb.write(response.getOutputStream());
        wb.close();

    }

    // 쇼핑 인사이트
    @GetMapping("/insight")
    public String insightSearch()
    {
        return "insight";
    }

    // AJAX, 카테고리 통신용
    @GetMapping("/twotag")
    @ResponseBody
    public String[] getSecondtag(@RequestParam(value = "pretag") String pretag)
    {
        System.out.println("2분류 카테고리 통신");

        return category.getSecondtag(pretag);
    }
    @GetMapping("/threetag")
    @ResponseBody
    public String[] getThreetag(@RequestParam(value = "pretag") String pretag, @RequestParam(value = "main") String maintag)
    {
        System.out.println("3분류 카테고리 통신");

        return category.getThreetag(pretag, maintag);
    }

    @GetMapping("/fourtag")
    @ResponseBody
    public String[] getFourtag(@RequestParam(value = "pretag") String pretag, @RequestParam(value = "sectag") String sectag)
    {
        System.out.println("4분류 카테고리 통신");

        return category.getFourtag(pretag, sectag);
    }

    // AJAX 인사이트 검색
    @GetMapping("/insightsearch")
    @ResponseBody
    public String[] searchInsight(@RequestParam(value = "maintag") String main,
                                  @RequestParam(value = "twotag") String two,
                                  @RequestParam(value = "threetag") String three,
                                  @RequestParam(value = "fourtag") String four,
                                  @RequestParam(value = "start") String start,
                                  @RequestParam(value = "end") String end,
                                  @RequestParam(value = "count") String count,
                                  @RequestParam(value = "device[]") String[] device,
                                  @RequestParam(value = "gender[]") String[] gender,
                                  @RequestParam(value = "age[]") String[] age) {
        System.out.println("인사이트 셀레니움 통신");
        String[] result = {};


        System.out.println("시작");

        for(String i : device)
        {
            System.out.println(i);
        }
        System.out.println(device);
        System.out.println(gender);
        System.out.println(age);

        result = insightSearch.search(main, two, three, four, start, end, Integer.parseInt(count), device ,gender, age);
        return result;
    }

    // AJAX, 필터 기능 사용
    @GetMapping("/exceptword")
    @ResponseBody
    public String[] exceptword() throws IOException {
        System.out.println(" 필터 사용");
        List<String> result = exceptWord.setWordlists();
        return result.toArray(new String[result.size()]);
    }

    // AJAX, 필터 기능 끄기
    @GetMapping("/exceptoff")
    @ResponseBody
    public Boolean exceptoff(){
        System.out.println(" 필터 사용 중지 ");
        return exceptWord.exceptoff();
    }

    // AJAX, 검색 중단
    @GetMapping("/stop")
    @ResponseBody
    public Boolean stopSearch() throws InterruptedException {
        searching = false;
        Thread.sleep(300);

        return searching;
    }

    // TEST, 카테고리 추출용
    @GetMapping("/test")
    @ResponseBody
    public void test() {
        insightSearch.cate();
    }

}
