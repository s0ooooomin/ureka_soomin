<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
<title>회원 관리</title>
</head>
<body>
	<div class="container mt-4">
		<h1 class="text-center mb-3">회원 관리</h1>

		<div class="input-group mb-3">
		  <input type="text" id="searchWord" class="form-control" placeholder="고객명 입력">
		  <button id="btnSearch" class="btn btn-outline-primary">검색</button>
		</div>

		<div class="table-responsive">
			<table class="table table-hover align-middle">
				<thead class="table-light"> <tr>
						<th>cust_id</th>
						<th>cust_name</th>
						<th>cust_phone</th>
					</tr>
				</thead>
				<tbody id = "customerTbody"> </tbody>
			</table>
		</div>
		
		<hr>
		
		<h3>회원 정보</h3>
		<form class="mb-3">
			<div class="row g-3">
				<div class="col-md-6">
					<label for="cust_id" class="form-label">Customer ID</label>
					<input type="text" class="form-control" name="cust_id" id="cust_id">
				</div>
				<div class="col-md-6">
					<label for="cust_name" class="form-label">이름</label>
					<input type="text" class="form-control" name="cust_name" id="cust_name">
				</div>
				<div class="col-md-6">
					<label for="cust_phone" class="form-label">전화번호</label>
					<input type="text" class="form-control" name="cust_phone" id="cust_phone">
				</div>
			</div>
		</form>
		
		<div class="d-flex flex-wrap gap-2 my-3">
			<button id="btnInsert" class="btn btn-primary">등록</button> 
			<button id="btnUpdate" class="btn btn-success">수정</button> 
			<button id="btnDelete" class="btn btn-danger">삭제</button> 
			<button id="btnClear" class="btn btn-secondary">초기화</button>
		</div>
		
		<hr>
		<a href="/" class="btn btn-outline-secondary mb-4">돌아가기</a>
	</div>
		
	<script>
		// 도서 목록 요청 -> json 데이터 수신 -> 화면 목록 ui 구성 (데이터목록)
		window.onload = function() {
			listCustomer();
			
			document.querySelector("#btnClear").onclick = clearForm; // 초기화 버튼
			document.querySelector("#btnInsert").onclick = insertCustomer; // 등록 버튼
			document.querySelector("#btnUpdate").onclick = updateCustomer; // 수정 버튼
			document.querySelector("#btnDelete").onclick = deleteCustomer; // 삭제 버튼

			document.querySelector("#btnSearch").onclick = listCustomerLike; // 검색 버튼
			
		}
		async function listCustomer() {
			
			// 데이터 요청
			let url = '/customers/list';
			let response = await fetch(url); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			
			// 회원 화면 ui 
			makeListHtml(data);
		}
		//사용자 지정 속성은 data-~~
		function makeListHtml(list) {
			let listHtml = ``;
			list.forEach( customer => { 
				listHtml += 
					`<tr style="cursor:pointer" data-cust_id=\${customer.cust_id}> 
						<td>\${customer.cust_id}</td>
						<td>\${customer.cust_name}</td>
						<td>\${customer.cust_phone}</td>
					</tr>`
			} );
			
			document.querySelector("#customerTbody").innerHTML = listHtml;
			
			document.querySelectorAll("#customerTbody tr").forEach( tr => {
				tr.onclick = function(){
					let cust_id = this.getAttribute("data-cust_id");
					console.log(cust_id);
					detailCustomer(cust_id);
				}
			} );
		}
		async function listCustomerLike() {
			// input 태그에서 검색어 값을 가져옴
			let searchWord = document.querySelector("#searchWord").value;
			
			// 데이터 요청
			let url = '/customers/listCustomerLike?searchWord=' + searchWord;
			let response = await fetch(url); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			// 수정한 listCustomer 함수에 검색어를 전달하여 호출
			makeListHtml(data);
		}
		
		// ctrl+shift+c -> commend 처리
		async function detailCustomer(cust_id) {
			alert(cust_id);
			// 데이터 요청
			let url = '/customers/detail/' + cust_id;
			let response = await fetch(url); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			
			// detail 화면 ui 
			document.querySelector("#cust_id").value = data.cust_id;
			document.querySelector("#cust_name").value = data.cust_name;
			document.querySelector("#cust_phone").value = data.cust_phone;
		}
		
		// 버튼 함수
		function clearForm(){
			document.querySelector("#cust_id").value = "";
			document.querySelector("#cust_name").value = "";
			document.querySelector("#cust_phone").value = "";
		}
		
		async function insertCustomer() {
			// 사용자 입력 form -> js book 객체 생성 -> Post 전송
			// js property는 : , 으로 구분
			let customer = {
				cust_id: document.querySelector("#cust_id").value,
				cust_name: document.querySelector("#cust_name").value,
				cust_phone: document.querySelector("#cust_phone").value
			};
			
			let urlParams = new URLSearchParams(customer);
			// 데이터 요청, Post 타입으로 urlParams를 변수로 넣어서
			let fetchOptions = {
					method: "Post",
					body: urlParams
			}
			let url = '/customers/insert';
			let response = await fetch(url, fetchOptions); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			
			if (data.result == "success") {
				alert("회원 등록 성공 ㅎㅎ");
				listCustomer();
				clearForm();
			} else {
				alert("회원 등록 실패 ㅜㅜ");
			}
			
		}
		async function updateCustomer() {
			// 객체 생성 없이 바로 전달
			let urlParams = new URLSearchParams({
				cust_id: document.querySelector("#cust_id").value,
				cust_phone: document.querySelector("#cust_phone").value
			});
			
			// 데이터 요청, Post 타입으로 urlParams를 변수로 넣어서
			let fetchOptions = {
					method: "Post",
					body: urlParams
			}
			
			let url = '/customers/update';
			let response = await fetch(url, fetchOptions); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			if (data.result == "success") {
				alert("회원 수정 성공 ㅎㅎ");
				listCustomer();
				clearForm();
			} else {
				alert("회원 수정 실패 ㅜㅜ");
			}
			
		}
		// delete - Get방식
		async function deleteCustomer() {
			let cust_id = document.querySelector("#cust_id").value;
			
			let url = '/customers/delete/' + cust_id;
			let response = await fetch(url); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			if (data.result == "success") {
				alert("회원 삭제 성공 ㅎㅎ");
				listCustomer();
				clearForm();
			} else {
				alert("회원 삭제 실패 ㅜㅜ");
			}
		}
		
	</script>
</body>
</html>