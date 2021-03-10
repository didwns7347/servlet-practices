<%@page import="com.bitacademy.guestbook.vo.GuestbookVo"%>

<%@page import="com.bitacademy.guestbook.dao.GuestbookDao"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<%
GuestbookDao dao = new GuestbookDao();
List<GuestbookVo> list = dao.findAll();
pageContext.setAttribute("cn", "\n"); //Space, Enter
pageContext.setAttribute("br", "<br/>"); //br 태그

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>방명록</title>
</head>
<body>
	<form action="/guestbook01/add.jsp" method="post">
		<table border=1 width=500>
			<tr>
				<td>이름</td>
				<td><input type="text" name="name"></td>
				<td>비밀번호</td>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<td colspan=4><textarea name="contents" cols=60 rows=5></textarea></td>
			</tr>
			<tr>
				<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
			</tr>
		</table>
	</form>
	<br>
	<%
	for (GuestbookVo vo : list) {
		String comment=vo.getContents();
	%>
	<table width=510 border=1>
		<tr>
			<td align=center>[<%=vo.getNo()%>]
			</td>
			<td><%=vo.getName()%></td>
			<td><%=vo.getDate()%></td>
			<td><a href="/guestbook01/deleteform.jsp?no=<%=vo.getNo()%>">삭제</a></td>
		</tr>
		<tr>
			<td colspan=4 style="white-space:pre-line">
				<p style="white-space: pre-line;"><%=comment %></p>
			</td>
		</tr>
	</table>
	<%
	}
	%>
</body>
</html>