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
	  <input type="text" class="form-control" id="searchWord" placeholder="검색어를 입력하세요.">
	  <button class="btn btn-outline-secondary" type="button" id="btnsearch">검색</button>
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
	  <tbody id="boardTbody">
	    
	    
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

<!-- Modal 구현 (insert Modal) -->
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

<!-- Modal 구현 (detail Modal) -->
<div class="container">

	<div class="modal fade" id="detailBoardModal" tabindex="-1" aria-labelledby="detailBoardModal" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5">글 상세</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      
	      <div class="modal-body">
	         <div class="example table-responsive">
	            <table class="table">
	              <tbody>
	                <tr><td>글번호</td><td id="boardIdDetail">#</td></tr>
	                <tr><td>제목</td><td id="titleDetail">#</td></tr>
	                <tr><td>내용</td><td id="contentDetail">#</td></tr>
	                <tr><td>작성자</td><td id="userNameDetail">#</td></tr>
	                <tr><td>작성일시</td><td id="regDtDetail">#</td></tr>
	                <tr><td>조회수</td><td id="readCountDetail">#</td></tr>
	              </tbody>
	            </table>
	        </div> 
	      </div>
	      
	      <div class="modal-footer" id="detailBoardModalFooter">
	      <!-- 입력 유효성 검사 후 창이 자동으로 닫히지 않도록 data-bs-dismiss 항목 제거 -->
	        <button id="btnBoardUpdatePage"type="button" class="btn btn-primary"> <!-- data-bs-dismiss="modal" -->수정하기</button>
	        <button id="btnBoardDeleteConfirm"type="button" class="btn btn-light"> <!-- data-bs-dismiss="modal" -->삭제하기</button>
	      </div>
	    </div>
	  </div>
	</div>
	
</div>
<!-- Modal 구현 (update Modal) -->
<div class="container">

	<div class="modal fade" id="updateBoardModal" tabindex="-1" aria-labelledby="updateBoardModal" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5">글 수정</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      
	      <div class="modal-body">
	        <div class="mb-3">
			  <label for="titleInsert" class="form-label">제목</label>
			  <input type="email" class="form-control" id="titleUpdate" placeholder="제목을 입력하세요">
			</div>
			<div class="mb-3">
			  <label for="contentInsert" class="form-label">내용</label>
			  <textarea class="form-control" id="contentUpdate" rows="10"></textarea>
			</div>
	      </div>
	      
	      <div class="modal-footer" id="updateBoardModalFooter">
	      <!-- 입력 유효성 검사 후 창이 자동으로 닫히지 않도록 data-bs-dismiss 항목 제거 -->
	        <button id="btnBoardUpdate"type="button" class="btn btn-primary"> <!-- data-bs-dismiss="modal" -->수정하기</button>
	        <button id="btnBoardCancel"type="button" class="btn btn-light"> <!-- data-bs-dismiss="modal" -->삭제하기</button>
	      </div>
	    </div>
	  </div>
	</div>
	
</div>

<script src="/assets/js/util.js"></script>
<script>
	const insertModal = new bootstrap.Modal( document.querySelector("#insertBoardModal"));
	const detailModal = new bootstrap.Modal( document.querySelector("#detailBoardModal"));
	const updateModal = new bootstrap.Modal( document.querySelector("#updateBoardModal"));
	
	// 창이 띄워져 있는 동안 발생할 수 있는 일
	window.onload = function() {
		// 글 목록
		listBoard();
		
		// 글 등록 모달 창 띄우기
		document.querySelector("#btnInsertPage").onclick = function() {
			// 입력창 정리
			document.querySelector("#titleInsert").value = '';
			document.querySelector("#contentInsert").value = '';
			
			// 모달 창 띄우기
			insertModal.show();
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
		
		// 글 수정 modal 띄우기
		document.querySelector("#btnBoardUpdatePage").onclick = function() {
			let boardId = document.querySelector("#detailBoardModal").getAttribute("data-boardId");
			document.querySelector("#updateBoardModal").setAttribute("data-boardId", boardId);
			// 상세 모달 ( 제목, 내용 ) -> 수정 모달 ( 제목, 내용 ) 내용 넣어줌
			document.querySelector("#titleUpdate").value = document.querySelector("#titleDetail").innerHTML;
			document.querySelector("#contentUpdate").value = document.querySelector("#contentDetail").innerHTML;
			
			detailModal.hide();
			updateModal.show();
			
		}	
		// 글 수정
		document.querySelector("#btnBoardUpdate").onclick = function() {
			if ( document.querySelector("#titleUpdate").value == '' 
				|| document.querySelector("#contentUpdate").value == '' ) {
			alert("제목 또는 내용을 모두 입력하세요.");
			return;
			}
			
			updateBoard();
		}	
		
		// 글 삭제
		document.querySelector("#btnBoardDeleteConfirm").onclick = function() {
			deleteBoard();
		}

		// 글 검색
		document.querySelector("#btnsearch").onclick = function() {
			listBoardSearch();
		}
		
	}
	
	async function listBoard() {
		let url = "/boards/list";
		let response = await fetch(url);
		let data = await response.json();

		console.log(data);
		
		if (data.result == "success") {
			makeListHtml(data.list);
		}else if (data.result == "fail") {
			alert("글 목록 로드 중 오류가 발생하였습니다ㅜ.ㅜ");
		}
	}
	
    function makeListHtml(list){
        let listHtml = ``;
        
        list.forEach( el => {
            let boardId = el.boardId;
            let userName = el.userName;
            let title = el.title;
            
            let regDt = new Date(el.regDt); 
            // "2025-11-11T09:30:05" -> javascript Date 객체       
            // 마지막 파라미터 : 구분자
            let regDtStr = makeDateStr(regDt.getFullYear(), regDt.getMonth() + 1, regDt.getDate(), '.');
            let readCount = el.readCount;
            
            listHtml += `
                <tr style="cursor:pointer" data-boardId="\${boardId}">
                    <td>\${boardId}</td>
                    <td>\${title}</td>
                    <td>\${userName}</td>
                    <td>\${regDtStr}</td>
                    <td>\${readCount}</td>
                </tr>
            `;
        } );
        
        document.querySelector("#boardTbody").innerHTML = listHtml;
        
        // tr 항목에 대한 click 이벤트 핸들러
        document.querySelectorAll("#boardTbody tr").forEach(el => {
        	el.onclick = function () {
        		let boardId = this.getAttribute("data-boardId");
        		detailBoard(boardId);
        	}
        });
    }
   // 상세
    async function detailBoard(boardId) {
    	let url = "/boards/detail/" + boardId;
		let response = await fetch(url);
		let data = await response.json();
		
		console.log(data);
		
		if (data.result == "success") {
			makeDetailHtml(data.dto);
		}else if (data.result == "fail") {
			alert("글 상세 로드 중 오류가 발생하였습니다ㅜ.ㅜ");
		}
    }
   
    function makeDetailHtml(dto) {
    	console.log(dto);
    	
    	let regDt = new Date(dto.regDt); 
        let regDtStr = makeDateStr(regDt.getFullYear(), regDt.getMonth() + 1, regDt.getDate(), '/') + ' ' +
						makeTimeStr(regDt.getHours(), regDt.getMinutes(), regDt.getSeconds(), ':');        
        
		document.querySelector("#boardIdDetail").innerHTML 	= "#" + dto.boardId;        
		document.querySelector("#titleDetail").innerHTML 	= dto.title;        
		document.querySelector("#contentDetail").innerHTML 	= dto.content;        
		document.querySelector("#userNameDetail").innerHTML = dto.userName;        
		document.querySelector("#regDtDetail").innerHTML 	= regDtStr;      
		document.querySelector("#readCountDetail").innerHTML = dto.readCount; 
		// 수정, 삭제를 위해 boardId를 Modal에 속성 추가
		document.querySelector("#detailBoardModal").setAttribute("data-boardId", dto.boardId);
    	
		// sameuser
		if (dto.sameUser) {
			document.querySelector("#detailBoardModalFooter").style.display="block";
		}else {
			document.querySelector("#detailBoardModalFooter").style.display="none";
		}
		
		detailModal.show();
    }
	
	// 등록
	// post, x-www-url~~ 방식으로 (URLSearchParams 객체)
	async function insertBoard() {
		console.log("insertBoard! 제목: " + document.querySelector("#titleInsert").value);
		console.log("insertBoard! 내용: " + document.querySelector("#contentInsert").value);
		let urlParams = new URLSearchParams ({
			title: document.querySelector("#titleInsert").value,
			content: document.querySelector("#contentInsert").value
		});
		
		let fetchOptions = {
				method: "post",
				body:  urlParams
		}
		
		let url = "/boards/insert";
		let response = await fetch(url, fetchOptions);
		let data = await response.json();
		
		console.log(data);
	
		if (data.result == "success") {
			alert("글이 등록되었습니다.");
			listBoard();
		}else if (data.result == "fail") {
			alert("글 등록 중 오류가 발생하였습니다ㅜ.ㅜ");
		}
		
		// 모달 창 닫기
		insertModal.hide();
	}
	
	// 수정
	// post, x-www-url~~ 방식으로 (URLSearchParams 객체)
	// 등록과 달리, boardId 전달 필요
	async function updateBoard() {
		
		let boardId = document.querySelector("#updateBoardModal").getAttribute("data-boardId");
		
		let urlParams = new URLSearchParams ({
			boardId: boardId,
			title: document.querySelector("#titleUpdate").value,
			content: document.querySelector("#contentUpdate").value
		});
		
		let fetchOptions = {
				method: "post",
				body:  urlParams
		}
		
		let url = "/boards/update";
		let response = await fetch(url, fetchOptions);
		let data = await response.json();
		
		console.log(data);
	
		if (data.result == "success") {
			alert("글이 수정되었습니다.");
			// 작업이 성공한 경우 modal 닫음
			updateModal.hide();
			listBoard();
		}else if (data.result == "fail") {
			alert("글 수정 중 오류가 발생하였습니다ㅜ.ㅜ");
		}
		
		// 모달 창 닫기
		insertModal.hide();
	}
	
	async function deleteBoard() {
		let boardId = document.querySelector("#detailBoardModal").getAttribute("data-boardId");
		
		let url = "/boards/delete/" + boardId
		let response = await fetch(url);
		let data = await response.json();
		
		console.log(data);
	
		if (data.result == "success") {
			alert("글이 삭제되었습니다.");
		}else if (data.result == "fail") {
			alert("글 삭제 중 오류가 발생하였습니다ㅜ.ㅜ");
		}
		detailModal.hide();
		listBoard();
	}
	
	async function listBoardSearch() {
		let searchWord = document.querySelector("#searchWord").value;
		
		// 데이터 요청
		let url = '/boards/listBoardSearch?searchWord=' + searchWord;
		let response = await fetch(url); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
		let data = await response.json();// response json화
		
		// 수정한 listMovie 함수에 검색어를 전달하여 호출
		if (data.result == "success") {
			makeListHtml(data.list);
		}else if (data.result == "fail") {
			alert("글 목록 로드 중 오류가 발생하였습니다ㅜ.ㅜ");
		}
		
	}
	
	
	
	
	
</script>



</body>
</html>