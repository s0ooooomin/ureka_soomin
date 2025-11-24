<%@page import="com.mycom.myapp.dto.CarDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>viewTest4.jsp예요</h1>
	
	<%
	String seq = (String) request.getAttribute("seq");
	CarDto carDto = (CarDto) request.getAttribute("carDto");
	%>
	<hr>
	<p>seq : <%= seq %></p>
	<p>carName : <%= carDto.getName() %></p>
	<p>carPrice : <%= carDto.getPrice() %></p>
	<p>carOwner : <%= carDto.getOwner() %></p>
</body>
</html>