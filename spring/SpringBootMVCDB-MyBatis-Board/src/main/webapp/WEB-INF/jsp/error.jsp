<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
<title>error</title>
</head>
<body>
	<h1>error.jsp예요</h1>
	
	<%-- @ExceptionHandler 없이 테스트 --%>
<%-- 	<p>요청하신 URL : <%= request.getRequestURI() %></p>
	<p>발생한 예외 : <%= exception.getMessage()	 %> </p>
	 --%>
	
	<%-- @ExceptionHandler 와 함께 테스트 --%>
	<p>요청하신 URL : <%= request.getAttribute("requestURI") %></p>
	<p>발생한 예외 : <%= exception.getMessage()	 %> </p>
	<p>발생한 예외 : <%= request.getAttribute("exception")	 %> </p>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
</body>
</html>