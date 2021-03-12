<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>값 받아보기</h1>
	${ival }
	
	<h1>객체 받아보기</h1>
	-${vo.no}--<br/>
	-${vo.name}--<br/>
	-${obj}--<br/>
	
	<h1>요청 파라미터 받아오기</h1>
	-${param.a }-<br/>
	-${param.email }-<br/>
	
	<h1>MAP으로 값 받아오기</h1>
	-${map.ival }-<br/>
	-${map.sval }-<br/>
	-${map.bval }-<br/>
	-${map.fval }-<br/>
</body>
</html>