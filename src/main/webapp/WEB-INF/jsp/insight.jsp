<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021-09-01
  Time: 오전 12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>

        table{

            width: 950px;
            height:300px;
            border: 2px solid black;
            table-layout: fixed;

        }
        th{
            width:50px;
            border-right: 2px solid dimgray;
            font-size: 23px;
        }
        td{
            padding-left:10px;
            width: 70px;
            height: 60px;
        }
        textarea{
            width:300px;
            margin-top: 50px;
            border: 2px solid black;
            padding: 20px;
        }
        select, input{
            height:40px;
            width:180px;

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
        .bd{
            display:flex;
            flex-direction: column;
            position: relative;
        }
        .check{
            width:20px;
            height:20px;
            vertical-align: -5px;
        }
    </style>

    <script src="https://code.jquery.com/jquery-3.4.1.js"></script>
    <script>
        $(document).ready(function(){
           var dateEnd = document.getElementById("date_end");
           var date = new Date();
           dateEnd.value = date.getFullYear() + "/" + (date.getMonth()+1) + "/" + date.getDate();
        });

        function changedate() {
            var date = new Date();
            var dateStart = document.getElementById("date_start");
            var dateEnd = document.getElementById("date_end");
            var select = document.getElementById("date_select");
            var data = select.options[select.selectedIndex].value;
            dateEnd.value = date.getFullYear() + "/" + ("0" + (date.getMonth() + 1)).slice(-2) + "/" + ("0" + (date.getDate())).slice(-2);

            switch(data)
            {
                case "1일":
                    date.setDate(date.getDate() - 1);
                    break;
                case "1주":
                    date.setDate(date.getDate() - 7);
                    break;
                case "1개월":
                    date.setMonth(date.getMonth() - 1);
                    break;
                case "3개월":
                    date.setMonth(date.getMonth()+ - 3);
                    break;
                case "6개월":
                    date.setMonth(date.getMonth()+ - 6);
                    break;
                case "1년":
                    date.setFullYear(date.getFullYear() - 1);
                    break;
                default:
                    break;
            }

            if (data != "수동 입력")
            {
                dateStart.value = date.getFullYear() + "/" + ("0" + (date.getMonth() + 1)).slice(-2) + "/" + ("0" + (date.getDate())).slice(-2);
            }
            else
            {
                dateStart.value = "";
            }
        }
        function twotag() {
            $.ajax({
                url: "/twotag",
                type: "GET",
                data:{
                    pretag:$("#category_main").val()
                },
                success: function(list){
                    var target = document.getElementById("category_two");
                    target.options.length = 0;

                    for(i in list) {
                        var option = document.createElement("option");
                        option.value = list[i];
                        option.innerHTML = list[i];
                        target.appendChild(option);
                    }


                },
                error: function(){
                    alert("통신 오류");
                }
            });
        }
        function threetag() {
            $.ajax({
                url: "/threetag",
                type: "GET",
                data:{
                    pretag:$("#category_two").val(),
                    main:$("#category_main").val()
                },
                success: function(list){
                    var target = document.getElementById("category_three");
                    target.options.length = 0;

                    for(i in list) {
                        var option = document.createElement("option");
                        option.value = list[i];
                        option.innerHTML = list[i];
                        target.appendChild(option);
                    }


                },
                error: function(){
                    alert("통신 오류");
                }
            });
        }
        function fourtag() {
            $.ajax({
                url: "/fourtag",
                type: "GET",
                data:{
                    pretag:$("#category_three").val(),
                    sectag:$("#category_two").val()
                },
                success: function(list){
                    var target = document.getElementById("category_four");
                    target.options.length = 0;

                    for(i in list) {
                        var option = document.createElement("option");
                        option.value = list[i];
                        option.innerHTML = list[i];
                        target.appendChild(option);
                    }


                },
                error: function(){
                    alert("통신 오류");
                }
            });
        }
        function search_insight() {

            if($("#category_main").val() == "제 1분류")
            {
                alert("분야를 선택해 주세요")
                return;
            }

            if($("#date_start").val() == "")
            {
                alert("기간을 입력해 주세요")
                return;
            }

            var devicelist = [];
            var genderlist = [];
            var agelist =  []

            $("input[name='list_device']").each(function(i){
                devicelist.push($(this).is(":checked"));
            });

            $("input[name='list_gender']").each(function(i){
                genderlist.push($(this).is(":checked"));
            });

            $("input[name='list_age']").each(function(i){
                agelist.push($(this).is(":checked"));
            });

            $.ajax({
                url: "/insightsearch",
                type: "GET",
                data:{
                    maintag:$("#category_main").val(),
                    twotag:$("#category_two").val(),
                    threetag:$("#category_three").val(),
                    fourtag:$("#category_four").val(),
                    start:$("#date_start").val(),
                    end:$("#date_end").val(),
                    count:$("#count_search").val(),
                    device:devicelist,
                    gender:genderlist,
                    age:agelist

                },
                success: function(list){
                    var target = document.getElementById("list_result");
                    target.value = "";

                    for(data in list)
                    {
                        target.value = target.value + "\n" + list[data];
                    }

                    alert(list.length + "개의 결과를 받아왔습니다.");

                },
                error: function(){
                    alert("통신 오류");
                }
            });
        }
    </script>
</head>
<body>

<p>
<a href="/"> <- 키워드 분석 검색 </a>
</p>
<div class="bd">
    <table>
        <tr>
            <th>분야</th>
            <td colspan="2">
                <select id="category_main" onchange="twotag()">
                    <option>제 1분류</option>
                    <option>패션의류</option>
                    <option>패션잡화</option>
                    <option>화장품/미용</option>
                    <option>디지털/가전</option>
                    <option>가구/인테리어</option>
                    <option>출산/육아</option>
                    <option>식품</option>
                    <option>스포츠/레저</option>
                    <option>생활/건강</option>
                    <option>여가/생활편의</option>
                    <option>면세점</option>
                    <option>도서</option>
                </select>
            </td>
            <td colspan="2">
                <select id="category_two" onchange="threetag()">
                    <option>제 2분류</option>
                </select>
            </td>
            <td colspan="2">
                <select id="category_three" onchange="fourtag()">
                    <option>제 3분류</option>
                </select>
            </td>
            <td colspan="2">
                <select id="category_four">
                    <option>제 4분류</option>
                </select>
            </td>
        </tr>
        <tr>
            <th>기간</th>
            <td colspan="2">
                <select id="date_select" onchange="changedate()">
                    <option>수동 입력</option>
                    <option>1일</option>
                    <option>1주</option>
                    <option>1개월</option>
                    <option>3개월</option>
                    <option>6개월</option>
                    <option>1년</option>
                </select>
            </td>
            <td colspan="2" style="text-align: right">
                <input type="text" placeholder="2017년 8월부터 조회 가능" id="date_start"/>
            </td>
            <td style="text-align: left">부터</td>
            <td colspan="2" style="text-align: right">
                <input type="text" placeholder="양식 : 0000/00/00" id="date_end"/>
            </td>
            <td style="text-align: left">까지</td>
        </tr>
        <tr>
            <th>기기별</th>
            <td colspan="2">
                <input type="checkbox" class="check" id="device_pc" name="list_device">PC
            </td>
            <td colspan="2">
                <input type="checkbox" class="check" id="device_mobile" name="list_device">모바일
            </td>

        </tr>
        <tr>
            <th>성별</th>
            <td colspan="2">
                <input type="checkbox" class="check" id="gender_female" name="list_gender">여성
            </td>
            <td colspan="2">
                <input type="checkbox" class="check" id="gender_male" name="list_gender">남성
            </td>
            <td style="text-align: right" colspan="2">
                찾을 단어 갯수 입력 (MAX 500)
            </td>
            <td colspan="2">
                <input type="text" placeholder="찾을 개수를 입력해주세요" id="count_search" value="500"/>
            </td>

        </tr>
        <tr>
            <th>나이</th>
            <td>
                <input type="checkbox" class="check" id="age_10" name="list_age">10대
            </td>
            <td>
                <input type="checkbox" class="check" id="age_20" name="list_age">20대
            </td>
            <td>
                <input type="checkbox" class="check" id="age_30" name="list_age">30대
            </td>
            <td>
                <input type="checkbox" class="check" id="age_40" name="list_age">40대
            </td>
            <td>
                <input type="checkbox" class="check" id="age_50" name="list_age">50대
            </td>
            <td>
                <input type="checkbox" class="check" id="age_60" name="list_age">60대
            </td>
            <td/>
            <td><input style="width:100%; height:100%" type="button"  onclick="search_insight()" value="검색 시작"> </td>
        </tr>
    </table>

<textarea id="list_result">
    결과가 표시됩니다.
</textarea>
</div>
</body>
</html>
