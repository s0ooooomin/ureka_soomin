<%@page import="com.mycom.myapp.user.dto.UserDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	UserDto userDto = (UserDto) session.getAttribute("userDto");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
<title>게시판</title>
</head>
<body>
<!-- 	<h1>board.jsp예요</h1> -->
<nav class="navbar navbar-expand-lg bg-light" >
  <div class="container">
    <a class="navbar-brand" href="#">
    	<!-- 프로필 이미지 보여주기 -->
  		<img src = "/assets/img/user/<%= userDto.getUserProfileImage() %>" style="width:24px; height:24px; border-radius:50%">
  		</img>
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">Link</a>
        </li>        
      </ul>
		
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" href="/pages/logout">Logout</a>
        </li>
      </ul>
		
    </div>
  </div>
</nav>

<div class="container mt-5">
	<h4 class="text-center">게시판</h4>
	
	<!-- 검색창 -->
	<div class="input-group mt-3">
	  <input type="text" class="form-control" placeholder="검색어를 입력하세요.">
	  <button class="btn btn-outline-secondary" type="button" id="button-addon2">검색</button>
	</div>
	
	
	<table class="table table-hover mt-3">
	  <thead>
	    <tr>
	      <th scope="col">#</th>
	      <th scope="col">제목</th>
	      <th scope="col">작성자</th>
	      <th scope="col">작성일시</th>
	      <th scope="col">조회수</th>
	    </tr>
	  </thead>
	  <tbody>
	    <tr>
	      <td scope="row">1</td>
	      <td>Mark</td>
	      <td>Otto</td>
	      <td>@mdo</td>
	      <td>@mdo</td>
	    </tr>
	    <tr>
	      <td scope="row">2</td>
	      <td>Jacob</td>
	      <td>Thornton</td>
	      <td>@fat</td>
	      <td>@fat</td>
	    </tr>
	    <tr>
	      <td scope="row">3</td>
	      <td>Larry the Bird</td>
	      <td>@twitter</td>
	      <td>@twitter</td>
	      <td>@twitter</td>
	    </tr>
	  </tbody>
	</table>


	<!-- Button trigger modal -->
	<!-- bootstrap을 통한 모달창 띄우기는 static -> 변화X --><!-- 
	<button type="button" class="btn btn-light" data-bs-toggle="modal" data-bs-target="#insertBoardModal">
	  글쓰기
	</button> -->
	
	<button type="button" class="btn btn-light" id="btnInsertPage">
	  글쓰기
	</button>

</div>

<div class="container">

	<!-- Modal 구현 -->
	<div class="modal fade" id="insertBoardModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5">글쓰기</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      
	      <div class="modal-body">
	        <div class="mb-3">
			  <label for="titleInsert" class="form-label">제목</label>
			  <input type="email" class="form-control" id="titleInsert" placeholder="제목을 입력하세요">
			</div>
			<div class="mb-3">
			  <label for="contentInsert" class="form-label">내용</label>
			  <textarea class="form-control" id="contentInsert" rows="10"></textarea>
			</div>
	      </div>
	      
	      <div class="modal-footer">
	      <!-- 입력 유효성 검사 후 창이 자동으로 닫히지 않도록 data-bs-dismiss 항목 제거 -->
	        <button id="btnBoardInsert"type="button" class="btn btn-primary"> <!-- data-bs-dismiss="modal" -->등록</button>
	      </div>
	    </div>
	  </div>
	</div>
	
</div>

<script>
	window.onload = function() {
		// 글 목록
		
		// 글 등록 모달 창 띄우기
		document.querySelector("#btnInsertPage").onclick = function() {
			// 입력창 정리
			document.querySelector("#titleInsert").value = '';
			document.querySelector("#contentInsert").value = '';
			
			// 모달 창 띄우기
			let modal = new bootstrap.Modal( document.querySelector("#insertBoardModal"));
			modal.show();
		}	
		
		// 글 등록
		document.querySelector("#btnBoardInsert").onclick = function() {
			// validation check
			if ( document.querySelector("#titleInsert").value == '' 
					|| document.querySelector("#contentInsert").value == '' ) {
				alert("제목 또는 내용을 모두 입력하세요.");
				return;
			}
			// 등록
			insertBoard();
		}			
	}
	
	async function insertBoard() {
		console.log("insertBoard! 제목: " + document.querySelector("#titleInsert").value);
		console.log("insertBoard! 내용: " + document.querySelector("#contentInsert").value);
	}
	
</script>



</body>
</html>