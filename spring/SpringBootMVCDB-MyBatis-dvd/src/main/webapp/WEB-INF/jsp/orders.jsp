<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>대여 관리</title>
</head>
<body>
	<h1>대여 관리</h1>
	<table>
		<thead> <!-- table-header -->
		<tr>
			<th>order_id</th>
			<th>cust_id</th>
			<th>movie_id</th>
			<th>title</th>
			<th>price</th>
			<th>name</th>
			<th>phone</th>
			<th>borrow</th>
			<th>return</th>
		</thead>
		
		<tbody id = "orderTbody"> <!-- table-body -->
		
		</tbody>
		
	</table>
	<hr>
	<form>
	
		<input type="text" name="order_id" id="order_id"></input><br>
		<input type="text" name="borrowdate" id="borrowdate"></input><br>
		<input type="text" name="returndate" id="returndate"></input><br>
				
	</form>
	<hr>
	<button id="btnUpdate">수정</button> <button id="btnDelete">삭제</button> <button id="btnClear">초기화</button>
	<hr>
	<input type="text" id="searchWord" placeholder="검색어 입력">
    <button id="btnSearch">검색하기</button>
	<hr>
	<a href="/">돌아가기</a>
		
	<script>
		window.onload = function() {
			// alert("로드됨");
			listOrder();
			
			document.querySelector("#btnClear").onclick = clearForm; // 초기화 버튼
			document.querySelector("#btnUpdate").onclick = updateOrder; // 수정 버튼
			document.querySelector("#btnDelete").onclick = deleteOrder; // 삭제 버튼
			
			document.querySelector("#btnSearch").onclick = listOrderLike; // 검색 버튼
			
		}
		async function listOrder() {
			
			// 데이터 요청
			let url = '/orders/list';
			let response = await fetch(url); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			
			// 도서목록 화면 ui 
			makeListHtml(data);
		}
		//사용자 지정 속성은 data-~~
		function makeListHtml(list) {
			let listHtml = ``;
			list.forEach( order => { 
				listHtml += 
					`<tr style="cursor:pointer" data-order_id=\${order.order_id}> 
						<td>\${order.order_id}</td>
						<td>\${order.cust_id}</td>
						<td>\${order.movie_id}</td>
						<td>\${order.movie_title}</td>
						<td>\${order.saleprice}</td>
						<td>\${order.cust_name}</td>
						<td>\${order.cust_phone}</td>
						<td>\${order.borrowdate}</td>
						<td>\${order.returndate}</td>
					</tr>`
			} );
			
			document.querySelector("#orderTbody").innerHTML = listHtml;
			
			document.querySelectorAll("#orderTbody tr").forEach( tr => {
				tr.onclick = function(){
					let order_id = this.getAttribute("data-order_id");
					console.log(order_id);
					detailOrder(order_id);
				}
			} );
		}
		async function listOrderLike() {
			// input 태그에서 검색어 값을 가져옴
			let searchWord = document.querySelector("#searchWord").value;
			
			// 데이터 요청
			let url = '/orders/listOrderLike?searchWord=' + searchWord;
			let response = await fetch(url); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			// 수정한 listOrder 함수에 검색어를 전달하여 호출
			makeListHtml(data);
		}
		
		// ctrl+shift+c -> commend 처리
		async function detailOrder(order_id) {
			alert(order_id);
			// 데이터 요청
			let url = '/orders/detail/' + order_id;
			let response = await fetch(url); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			
			// detail 화면 ui 
			document.querySelector("#order_id").value = data.order_id;
			document.querySelector("#borrowdate").value = data.borrowdate;
			document.querySelector("#returndate").value = data.returndate;
		}
		
		// 버튼 함수
		function clearForm(){
			document.querySelector("#order_id").value = "";
			document.querySelector("#borrowdate").value = "";
			document.querySelector("#returndate").value = "";
		}
		
		async function updateOrder() {
			// 객체 생성 없이 바로 전달
			let urlParams = new URLSearchParams({
				order_id: document.querySelector("#order_id").value,
				borrowdate: (document.querySelector("#borrowdate").value == "" ) ? null : document.querySelector("#borrowdate").value,
				returndate: (document.querySelector("#returndate").value == "" ) ? null : document.querySelector("#returndate").value
			});
			
			// 데이터 요청, Post 타입으로 urlParams를 변수로 넣어서
			let fetchOptions = {
					method: "Post",
					body: urlParams
			}
			
			let url = '/orders/update';
			let response = await fetch(url, fetchOptions); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			if (data.result == "success") {
				alert("영화 수정 성공 ㅎㅎ");
				listOrder();
				clearForm();
			} else {
				alert("영화 수정 실패 ㅜㅜ");
			}
			
		}
		// delete - Get방식
		async function deleteOrder() {
			let order_id = document.querySelector("#order_id").value;
			
			let url = '/orders/delete/' + order_id;
			let response = await fetch(url); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			if (data.result == "success") {
				alert("영화 삭제 성공 ㅎㅎ");
				listOrder();
				clearForm();
			} else {
				alert("영화 삭제 실패 ㅜㅜ");
			}
		}
		
		
		
	</script>
</body>
</html>