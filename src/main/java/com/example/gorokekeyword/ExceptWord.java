package com.example.gorokekeyword;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// 필터링 기능 클래스
// 페이크 경로등 보안 기능으로 인해 경로를 절대값으로 지정.
public class ExceptWord {

    // 필터 단어 리스트, 필터링 여부, 필터링 파일 경로
    // 필터링 파일은 column = 0부터, row = 2부터 추출
    List<String> wordlists = new ArrayList<String>();
    Boolean except = false;
    String path =  "C:/제외키워드.xlsx";

    int rowNum = 0, colNum = 0, rowCount = 0, colCount = 0;
    int rownullCount = 0, colnullCount = 0;

    // 필터 함수
    public Boolean filter(String str) {
        if (except == false) return true;
        for (String word : wordlists)
        {
            if(str.contains(word)) {
                System.out.println("검색어 제외 : " + str + " /  필터 (" + word +")");
                return false;
            }
        }
        return true;
    }

    // 필터 기능 끄기
    public Boolean exceptoff()
    {
        except = false;

        return except;
    }

    // 필터링 파일로 부터 필터 단어 추출
    public List<String> setWordlists() throws IOException {
        wordlists.clear();
        except = true;
        FileInputStream file = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);

        rowCount = sheet.getPhysicalNumberOfRows();
        rownullCount = 0;
        rowNum = 2;

        while( rownullCount < 3 )
        {
            XSSFRow row = sheet.getRow(rowNum);
            if(row != null)
            {
                colCount = row.getPhysicalNumberOfCells();
                colnullCount = 0;
                colNum = 0;
                while(colnullCount < 3)
                {
                    XSSFCell cell = row.getCell(colNum);
                    if(cell != null)
                    {
                        if (cell.getStringCellValue().trim().equals(""))
                            colnullCount++;
                        else {
                            rownullCount = 0;
                            colnullCount = 0;
                            System.out.println(rowNum + " / " + colNum + " : " + cell.getStringCellValue());
                            wordlists.add(cell.getStringCellValue() + "");
                        }
                    }
                    else
                        colnullCount++;
                    colNum++;
                }
            }
            else
                rownullCount++;
            rowNum++;
        }
        file.close();
        System.out.println(wordlists.size() + " 개의 단어를 저장하였습니다. ");
        return wordlists;
    }
}
