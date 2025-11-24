<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
<title>영화 관리</title>
</head>
<body>
	<div class="container mt-4">
		<h1 class="text-center mb-3">영화 관리</h1>
		
		<div class="input-group mb-3">
		  <input type="text" id="searchWord" class="form-control" placeholder="검색어 입력">
		  <button id="btnSearch" class="btn btn-outline-primary">검색하기</button>
		</div>

		<div class="table-responsive">
			<table class="table table-hover align-middle">
				<thead class="table-light"> <tr>
						<th>ID</th>
						<th>제목</th>
						<th>감독</th>
						<th>가격</th>
					</tr>
				</thead>
				<tbody id="movieTbody"> </tbody>
			</table>
		</div>
		
		<hr>
		
		<h3>영화 정보</h3>
		<form class="mb-3">
			<div class="row g-3">
				<div class="col-md-6">
					<label for="movie_id" class="form-label">Movie ID</label>
					<input type="text" class="form-control" name="movie_id" id="movie_id">
				</div>
				<div class="col-md-6">
					<label for="movie_title" class="form-label">제목</label>
					<input type="text" class="form-control" name="movie_title" id="movie_title">
				</div>
				<div class="col-md-6">
					<label for="director" class="form-label">감독</label>
					<input type="text" class="form-control" name="director" id="director">
				</div>
				<div class="col-md-6">
					<label for="price" class="form-label">가격</label>
					<input type="text" class="form-control" name="price" id="price">
				</div>
			</div>
		</form>
		
		<div class="d-flex flex-wrap gap-2 my-3">
			<button id="btnInsert" class="btn btn-primary">등록</button> 
			<button id="btnUpdate" class="btn btn-success">수정</button> 
			<button id="btnDelete" class="btn btn-danger">삭제</button> 
			<button id="btnClear" class="btn btn-secondary">초기화</button>
			
			<button type="button" class="btn btn-info ms-auto" data-bs-toggle="modal" data-bs-target="#exampleModal">
			  대여하기
			</button>
		</div>

		<hr>
		<a href="/" class="btn btn-outline-secondary mb-4">돌아가기</a>
	</div>
	

	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="exampleModalLabel">회원 정보 입력</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <form>
	          <div class="mb-3">
	            <label for="cust_name" class="col-form-label">이름</label>
	            <input type="text" class="form-control" id="cust_name">
	          </div>
	          <div class="mb-3">
	            <label for="cust_phone" class="col-form-label">전화번호</label>
	            <input type="tel" class="form-control" id="cust_phone">
	          </div>
	        </form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
	        <button type="button" class="btn btn-primary" id="btnborrowMovie">대여하기</button>
	      </div>
	    </div>
	  </div>
	</div>
		
	<script>
		// 도서 목록 요청 -> json 데이터 수신 -> 화면 목록 ui 구성 (데이터목록)
		window.onload = function() {
			listMovie();
			
			document.querySelector("#btnClear").onclick = clearForm; // 초기화 버튼
			document.querySelector("#btnInsert").onclick = insertMovie; // 등록 버튼
			document.querySelector("#btnUpdate").onclick = updateMovie; // 수정 버튼
			document.querySelector("#btnDelete").onclick = deleteMovie; // 삭제 버튼
			document.querySelector("#btnborrowMovie").onclick = borrowMovie; // 대여 버튼
			
			document.querySelector("#btnSearch").onclick = listMovieLike; // 검색 버튼
			
		}
		async function listMovie() {
			
			// 데이터 요청
			let url = '/movies/list';
			let response = await fetch(url); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			
			// 도서목록 화면 ui 
			makeListHtml(data);
		}
		//사용자 지정 속성은 data-~~
		function makeListHtml(list) {
			let listHtml = ``;
			list.forEach( movie => { 
				listHtml += 
					`<tr style="cursor:pointer" data-movie_id=\${movie.movie_id}> 
						<td>\${movie.movie_id}</td>
						<td>\${movie.movie_title}</td>
						<td>\${movie.director}</td>
						<td>\${movie.price}</td>
					</tr>`
			} );
			
			document.querySelector("#movieTbody").innerHTML = listHtml;
			
			document.querySelectorAll("#movieTbody tr").forEach( tr => {
				tr.onclick = function(){
					let movie_id = this.getAttribute("data-movie_id");
					console.log(movie_id);
					detailMovie(movie_id);
				}
			} );
		}
		async function listMovieLike() {
			// input 태그에서 검색어 값을 가져옴
			let searchWord = document.querySelector("#searchWord").value;
			
			// 데이터 요청
			let url = '/movies/listMovieLike?searchWord=' + searchWord;
			let response = await fetch(url); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			// 수정한 listMovie 함수에 검색어를 전달하여 호출
			makeListHtml(data);
		}
		
		// ctrl+shift+c -> commend 처리
		async function detailMovie(movie_id) {
			alert(movie_id);
			// 데이터 요청
			let url = '/movies/detail/' + movie_id;
			let response = await fetch(url); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			
			// detail 화면 ui 
			document.querySelector("#movie_id").value = data.movie_id;
			document.querySelector("#movie_title").value = data.movie_title;
			document.querySelector("#director").value = data.director;
			document.querySelector("#price").value = data.price;
		}
		
		// 버튼 함수
		function clearForm(){
			document.querySelector("#movie_id").value = "";
			document.querySelector("#movie_title").value = "";
			document.querySelector("#director").value = "";
			document.querySelector("#price").value = "";
		}
		
		async function insertMovie() {
			// 사용자 입력 form -> js book 객체 생성 -> Post 전송
			// js property는 : , 으로 구분
			let movie = {
				movie_title: document.querySelector("#movie_title").value,
				director: document.querySelector("#director").value,
				price: document.querySelector("#price").value
			};
			
			let urlParams = new URLSearchParams(movie);
			// 데이터 요청, Post 타입으로 urlParams를 변수로 넣어서
			let fetchOptions = {
					method: "Post",
					body: urlParams
			}
			let url = '/movies/insert';
			let response = await fetch(url, fetchOptions); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			
			if (data.result == "success") {
				alert("영화 등록 성공 ㅎㅎ");
				listMovie();
				clearForm();
			} else {
				alert("영화 등록 실패 ㅜㅜ");
			}
			
		}
		async function updateMovie() {
			// 객체 생성 없이 바로 전달
			let urlParams = new URLSearchParams({
				movie_id: document.querySelector("#movie_id").value,
				price: document.querySelector("#price").value
			});
			
			// 데이터 요청, Post 타입으로 urlParams를 변수로 넣어서
			let fetchOptions = {
					method: "Post",
					body: urlParams
			}
			
			let url = '/movies/update';
			let response = await fetch(url, fetchOptions); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			if (data.result == "success") {
				alert("영화 수정 성공 ㅎㅎ");
				listMovie();
				clearForm();
			} else {
				alert("영화 수정 실패 ㅜㅜ");
			}
			
		}
		// delete - Get방식
		async function deleteMovie() {
			let movie_id = document.querySelector("#movie_id").value;
			
			let url = '/movies/delete/' + movie_id;
			let response = await fetch(url); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			if (data.result == "success") {
				alert("영화 삭제 성공 ㅎㅎ");
				listMovie();
				clearForm();
			} else {
				alert("영화 삭제 실패 ㅜㅜ");
			}
		}
		
		// insertOrder - Post
		async function borrowMovie() {
			// 사용자 입력 form -> js book 객체 생성 -> Post 전송
			// js property는 : , 으로 구분
			let order = {
				cust_name: document.querySelector("#cust_name").value,
				cust_phone: document.querySelector("#cust_phone").value,
				movie_id: document.querySelector("#movie_id").value
			};
			
			let urlParams = new URLSearchParams(order);
			// 데이터 요청, Post 타입으로 urlParams를 변수로 넣어서
			let fetchOptions = {
					method: "Post",
					body: urlParams
			}
			let url = '/orders/insert';
			let response = await fetch(url, fetchOptions); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			
			if (data.result == "success") {
				alert("대여 등록 성공 ㅎㅎ");
				listMovie();
				clearForm();
			} else {
				alert("대여 등록 실패 ㅜㅜ");
			}
			
		}
		
		
		
		
		
	</script>
</body>
</html>