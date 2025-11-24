<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
 	String username = (String) session.getAttribute("username");
%>
<div>
<%if (username == null) { %>
	<div>로그링하세요.</div>
<%} else{ %>
	<div>로그인상태입니다. <br> [ <%= username %> ] 님 반갑습니다.</div>
<%} %>
</div>