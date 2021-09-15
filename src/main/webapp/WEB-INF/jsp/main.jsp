<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021-08-21
  Time: 오전 1:24
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-3.4.1.js"></script>
    <script>
        function stopsearch() {
            $.ajax({
                url: "/stop",
                type: "GET",
                data:{
                },
                success: function(result){
                    if (result == false)
                        alert(" 검색을 중지하였습니다. ");
                    else
                        alert(" 검색을 중지하지 못했습니다. ");
                },
                error: function(){
                    alert("통신 오류");
                }
            });
        }
        function exceptoff() {

            $.ajax({
                url: "/exceptoff",
                type: "GET",
                data: {
                },
                    success: function(result){
                    if (result == false)
                        alert(" 필터를 중지하였습니다 ");
                    else
                        alert(" 필터를 중지하지 못했습니다. ");
                },
                error: function(){
                    alert("통신 오류");
                }
            });
        }
        function exceptword() {
            $.ajax({
                url: "/exceptword",
                type: "GET",
                data:{
                    path:$("#except").val(),
                },
                success: function(result){
                    if (result != null) {
                        alert(" 제외 단어 목록이 입력되었습니다. ");
                        alert(result);
                    }

                    else
                        alert(" 파일을 찾지 못하였습니다. ");
                },
                error: function(){
                    alert("통신 오류");
                }
            });
        }
    </script>
    <style>

        body{
            position: relative;
            text-align: center;
            font-size: 15px;
            font-family: goth
        }
        table{
            align-items: center;
            border-top: 1px solid black;
            border-collapse: collapse;
            margin: 0 auto;
        }
        th, td{
            border-bottom: 1px solid black;
            border-left: 1px solid dimgray;
            border-right: 1px solid dimgray;
            height: 30px;
            text-align: center;
        }

        td{
            width: 100px;
        }

        th{
            width: 150px;
        }
        .no
        {
            border: none;
        }
        a
        {
            font-size: 15pt;
            font-weight: bold;
            text-decoration: none;
        }
        a:visited{
            color: black;
        }
        a:hover{
            font-size: 1.4rem;
        }
        a:link{
            color:black;
        }
    </style>
</head>
<body>
<p style="text-align: right;">
    <a href="insight"> 쇼핑 인사이트 인기 검색어 -> </a>
</p>
    <Form action="search" method="post">
        <table style="border: 0px">
            <tr>
                <th class="no"> 검색 단어 </th>
                <th class="no"> 연관단어 수 </th>
                <th class="no"> 검색 버튼 </th>
            </tr>

            <tr>
                <td class="no"> <textarea style="width: 200px; height: 100px; " name="input_keyword" rows="4" cols="50">검색어 입력</textarea></td>
                <td class="no"> <input style="height: 30px;" Type="Text" value="100" name="input_number"/> </td>
                <td class="no">
                    <input style="width: 80px; height: 30px; " type="submit" value="검색" />
                    <input style="width: 80px; height: 30px; " type="button" onclick="stopsearch()" value="중지" />
                    <input style="width: 80px; height: 30px;" id="except" type="button" onclick="exceptword()" value="필터 사용">
                    <input style="width: 80px; height: 30px;" type="button" onclick="exceptoff()" value="필터 제거">
                </td>
            </tr>
        </table>
    </Form>

<%--결과창--%>
    <div>

        <input style="width:150px; height: 50px; " type="button"  onclick="location='download'" value="엑셀 파일 다운" />
        <table style="margin: 10px auto">
            <tr>
                <th style="width:70px;" rowspan="2"> 번호 </th>
                <th rowspan="2"> 검색어 </th>
                <th colspan="2"> 검색수 </th>
                <th colspan="2"> 클릭수 </th>
                <th colspan="2"> 클릭률 </th>
                <th rowspan="2"> 경쟁력 </th>
<%--                <th rowspan="2"> 블로그 게시물 수 </th>--%>
<%--                <th rowspan="2"> 블로그 비율 </th>--%>
                <th rowspan="2"> 쇼핑 상품 수 </th>
                <th rowspan="2"> 쇼핑 비율 </th>
            </tr>
            <tr style="border-bottom:  2px solid black">

                <td> PC </td>
                <td> MOBILE </td>
                <td> PC </td>
                <td> MOBILE </td>
                <td> PC </td>
                <td> MOBILE </td>

            </tr>
            <c:forEach var="calculator" items="${calculator_result}" varStatus="status">
                <tr>
                    <td> ${calculator.number} </td>
                    <td> ${calculator.keyword_list.relKeyword} </td>
                    <td> ${calculator.keyword_list.monthlyPcQcCnt} </td>
                    <td> ${calculator.keyword_list.monthlyMobileQcCnt} </td>
                    <td> ${calculator.keyword_list.monthlyAvePcClkCnt} </td>
                    <td> ${calculator.keyword_list.monthlyAveMobileClkCnt} </td>
                    <td> ${calculator.keyword_list.monthlyAvePcCtr} </td>
                    <td> ${calculator.keyword_list.monthlyAveMobileCtr} </td>
                    <td> ${calculator.keyword_list.compIdx} </td>
<%--                    <td> ${calculator.blog_result}</td>--%>
<%--                    <td> ${calculator.blog_ratio}</td>--%>
                    <td> ${calculator.shop_result}</td>
                    <td> ${calculator.shop_ratio}</td>
                </tr>
            </c:forEach>
        </table>

    </div>
</body>
</html>
