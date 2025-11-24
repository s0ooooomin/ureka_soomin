<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도서 관리</title>
</head>
<body>
	<h1>도서 관리</h1>
	<table>
		<thead> <!-- table-header -->
		<tr><th>bookId</th><th>bookName</th><th>publisher</th><th>price</th></tr>
		</thead>
		
		<tbody id = "bookTbody"> <!-- table-body -->
		
		</tbody>
		
	</table>
	<hr>
	<form>
	<!-- servlet + jsp : form 객체를 전송 (action, post, submit)
			js : action 버튼 X, id를 통해  
	
	-->
		<input type="text" name="bookId" id="bookId"></input><br>
		<input type="text" name="bookName" id="bookName"></input><br>
		<input type="text" name="publisher" id="publisher"></input><br>
		<input type="text" name="price" id="price"></input><br>
				
	</form>
	<hr>
	<button id="btnInsert">등록</button> <button id="btnUpdate">수정</button> <button id="btnDelete">삭제</button> <button id="btnClear">초기화</button>
			
		
	<script>
		// 도서 목록 요청 -> json 데이터 수신 -> 화면 목록 ui 구성 (데이터목록)
		window.onload = function() {
			listBook();
			
			document.querySelector("#btnClear").onclick = clearForm; // 초기화 버튼
			document.querySelector("#btnInsert").onclick = insertBook; // 등록 버튼
			document.querySelector("#btnUpdate").onclick = updateBook; // 수정 버튼
			document.querySelector("#btnDelete").onclick = deleteBook; // 삭제 버튼
			
		}
		async function listBook() {
			
			// 데이터 요청
			let url = 'books/list';
			let response = await fetch(url); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			
			// 도서목록 화면 ui 
			makeListHtml(data);
		}
		//사용자 지정 속성은 data-~~
		function makeListHtml(list) {
			let listHtml = ``;
			list.forEach( book => { 
				listHtml += 
					`<tr style="cursor:pointer" data-bookId=\${book.bookId}> 
						<td>\${book.bookId}</td>
						<td>\${book.bookName}</td>
						<td>\${book.publisher}</td>
						<td>\${book.price}</td>
					</tr>`
			} );
			
			document.querySelector("#bookTbody").innerHTML = listHtml;
			
			document.querySelectorAll("#bookTbody tr").forEach( tr => {
				tr.onclick = function(){
					let bookId = this.getAttribute("data-bookId");
					console.log(bookId);
					detailBook(bookId);
				}
			} );
		}
		
		// ctrl+shift+c -> commend 처리
		async function detailBook(bookId) {
			alert(bookId);
			// 데이터 요청
			let url = '/books/detail/' + bookId;
			let response = await fetch(url); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			
			// detail 화면 ui 
			document.querySelector("#bookId").value = data.bookId;
			document.querySelector("#bookName").value = data.bookName;
			document.querySelector("#publisher").value = data.publisher;
			document.querySelector("#price").value = data.price;
		}
		
		// 버튼 함수
		function clearForm(){
			document.querySelector("#bookId").value = "";
			document.querySelector("#bookName").value = "";
			document.querySelector("#publisher").value = "";
			document.querySelector("#price").value = "";
		}
		
		async function insertBook() {
			// 사용자 입력 form -> js book 객체 생성 -> Post 전송
			// js property는 : , 으로 구분
			let book = {
				bookId: document.querySelector("#bookId").value, 
				bookName: document.querySelector("#bookName").value,
				publisher: document.querySelector("#publisher").value,
				price: document.querySelector("#price").value
			};
			
			let urlParams = new URLSearchParams(book);
			// 데이터 요청, Post 타입으로 urlParams를 변수로 넣어서
			let fetchOptions = {
					method: "Post",
					body: urlParams
			}
			let url = '/books/insert';
			let response = await fetch(url, fetchOptions); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			
			if (data.result == "success") {
				alert("도서 등록 성공 ㅎㅎ");
				listBook();
				clearForm();
			} else {
				alert("도서 등록 실패 ㅜㅜ");
			}
			
		}
		async function updateBook() {
			// 객체 생성 없이 바로 전달
			let urlParams = new URLSearchParams({
				bookId: document.querySelector("#bookId").value, 
				bookName: document.querySelector("#bookName").value,
				publisher: document.querySelector("#publisher").value,
				price: document.querySelector("#price").value
			});
			
			// 데이터 요청, Post 타입으로 urlParams를 변수로 넣어서
			let fetchOptions = {
					method: "Post",
					body: urlParams
			}
			
			let url = '/books/update';
			let response = await fetch(url, fetchOptions); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			if (data.result == "success") {
				alert("도서 수정 성공 ㅎㅎ");
				listBook();
				clearForm();
			} else {
				alert("도서 수정 실패 ㅜㅜ");
			}
			
		}
		// delete - Get방식
		async function deleteBook(){
            let bookId = document.querySelector("#bookId").value;
            let url = '/books/delete/' + bookId; // path variable 대응 코드
            let response = await fetch(url); // 비동기 요청
            let data = await response.json();
            
            console.log(data);
            if( data.result == "success"){
                alert("도서 삭제 성공!");
                listBook(); // 도서 목록 갱신
                clearForm(); // 입력 폼 초기화
            }else{
                alert("도서 삭제 실패!");
            }               
        }
		
		
		
		
		
	</script>
</body>
</html>