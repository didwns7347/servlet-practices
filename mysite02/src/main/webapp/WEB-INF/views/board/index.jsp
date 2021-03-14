<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<c:set var="count" value="${fn:length(list) }"/>
				<table class="tbl-ex">
				<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
				</tr>
				<c:forEach items="${list}" var="vo" varStatus="status">
						<c:choose>
							<c:when test="${vo.depth==0}">
								<tr>		
									<td align=center>[${count-status.index}]</td>
									<td><a href="${pageContext.request.contextPath }/board?a=view" style="test-align:left; padding-left:0px">${vo.title}</a></td>
									<td>${vo.writer}</td>
									<td>3</td>
									<td>${vo.date }</td>
									<td><a href="${pageContext.request.contextPath }/board?a=" class="del">삭제</a></td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>		
									<td align=center>[${count-status.index}]</td>
									<td><a href="${pageContext.request.contextPath }/board?a=view" style="test-align:left; padding-left:${vo.depth*20}px"><img src="${pageContext.request.contextPath }/assets/images/reply.png" />${vo.title}</a></td>
									<td>${vo.writer}</td>
									<td>3</td>
									<td>${vo.date }</td>
									<td><a href="${pageContext.request.contextPath }/board?a=" class="del">삭제</a></td>
								</tr>
							</c:otherwise>
						</c:choose>
					
				</c:forEach>
				</table>
				
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<li><a href="">◀</a></li>
						<li><a href="">1</a></li>
						<li class="selected">2</li>
						<li><a href="">3</a></li>
						<li>4</li>
						<li>5</li>
						<li><a href="">▶</a></li>
					</ul>
				</div>					
				<!-- pager 추가 -->
				<div class="bottom">
					
					<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">
					글쓰기
					</a>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>