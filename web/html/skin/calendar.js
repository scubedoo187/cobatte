function montharr(m0, m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11)  
{  
this[0] = m0;  
this[1] = m1;  
this[2] = m2;  
this[3] = m3;  
this[4] = m4;  
this[5] = m5;  
this[6] = m6;  
this[7] = m7;  
this[8] = m8;  
this[9] = m9;  
this[10] = m10;  
this[11] = m11;  
}  
function calendar() {  
var monthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";  
var today = new Date();  
var thisDay;  
var monthDays = new montharr(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);  
year = today.getYear() +1900;  
thisDay = today.getDate();  
if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) monthDays[1] = 29;  
nDays = monthDays[today.getMonth()];  
firstDay = today;  
firstDay.setDate(1);  
testMe = firstDay.getDate();  
if (testMe == 2) firstDay.setDate(0);  
startDay = firstDay.getDay();  
document.write("<TABLE width='100%' BORDER='0' CELLSPACING='0' CELLPADDING='0' ALIGN='CENTER'>")  
document.write("<TR><TD><table width='100%' style='border:1px solid #c3c6ca' cellspacing='3' cellpadding='3' bgcolor='f8f8f8'>");  
document.write("<TR><th style='border:1px solid #c3c6ca' colspan='7' bgcolor='#f8f8f8'>달력");  
var dayNames = new Array("일","월","화","수","목","금","토");  
var monthNames = new Array("1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월");  

document.writeln("</TH></TR><TR><TH style='border:1px solid #ffffff' BGCOLOR='#ff0000'><font color=ffffff>일</font></TH>");  
document.writeln("<th style='border:1px solid #ffffff' bgcolor='#000000'><font color=ffffff>월</font></TH>");  
document.writeln("<th style='border:1px solid #ffffff' bgcolor='#000000'><font color=ffffff>화</font></TH>");  
document.writeln("<th style='border:1px solid #ffffff' bgcolor='#000000'><font color=ffffff>수</font></TH>");  
document.writeln("<th style='border:1px solid #ffffff' bgcolor='#000000'><font color=ffffff>목</font></TH>");  
document.writeln("<th style='border:1px solid #ffffff' bgcolor='#000000'><font color=ffffff>금</font></TH>");  
document.writeln("<th style='border:1px solid #ffffff' bgcolor='#0084ff'><font color=ffffff>토</font></TH>");  
document.writeln("</TR><TR>");  
column = 0;  
for (i=0; i<startDay; i++) {  
document.writeln("\n<TD bgcolor='#ffffff' style='border:1px solid #f1f1f1'> </TD>");  
column++;  
}  
for (i=1; i<=nDays; i++) {  
if (i == thisDay) {  
document.writeln("</TD><TD ALIGN='CENTER' BGCOLOR='#FF8040'><font color=ffffff><B>")  
}  
else {  
document.writeln("</font></TD><TD style='border:1px solid #f1f1f1' BGCOLOR='#FFFFFF' ALIGN='CENTER'>");  
}  
document.writeln(i);  
if (i == thisDay) document.writeln("</TD>")  
column++;  
if (column == 7) {  
document.writeln("<TR>");  
column = 0;  
}  
}  
document.writeln("<TR><TD COLSPAN='7' ALIGN='CENTER' VALIGN='TOP' BGCOLOR='#ffffff'></table></table>")  

}