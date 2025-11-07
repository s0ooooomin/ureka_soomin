<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>영화 관리</title>
</head>
<body>
	<h1>영화 관리</h1>
	<table>
		<thead> <!-- table-header -->
		<tr><th>movie_id</th><th>movie_title</th><th>director</th><th>price</th>
		</thead>
		
		<tbody id = "movieTbody"> <!-- table-body -->
		
		</tbody>
		
	</table>
	<hr>
	<form>
	<!-- servlet + jsp : form 객체를 전송 (action, post, submit)
			js : action 버튼 X, id를 통해  
	
	-->
		<input type="text" name="movie_id" id="movie_id"></input><br>
		<input type="text" name="movie_title" id="movie_title"></input><br>
		<input type="text" name="director" id="director"></input><br>
		<input type="text" name="price" id="price"></input><br>
				
	</form>
	<hr>
	<button id="btnInsert">등록</button> <button id="btnUpdate">수정</button> <button id="btnDelete">삭제</button> <button id="btnClear">초기화</button>
	<button id="btnborrowMovie">대여하기</button> 
	<hr>
	<input type="text" id="searchWord" placeholder="검색어 입력">
    <button id="btnSearch">검색하기</button>
	<hr>
	<a href="/">돌아가기</a>
		
	<script>
		// 도서 목록 요청 -> json 데이터 수신 -> 화면 목록 ui 구성 (데이터목록)
		window.onload = function() {
			listMovie();
			
			document.querySelector("#btnClear").onclick = clearForm; // 초기화 버튼
			document.querySelector("#btnInsert").onclick = insertMovie; // 등록 버튼
			document.querySelector("#btnUpdate").onclick = updateMovie; // 수정 버튼
			document.querySelector("#btnDelete").onclick = deleteMovie; // 삭제 버튼
			// document.querySelector("#btnborrowMovie").onclick = borrowMovie; // 대여 버튼
			
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
				movie_id: document.querySelector("#movie_id").value,
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
			let movie = {
				movie_id: document.querySelector("#movie_id").value,
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