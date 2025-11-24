<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 관리</title>
</head>
<body>
	<h1>사원 관리</h1>
	<table>
		<thead> <!-- table-header -->
		<tr><th>employeeId</th><th>firstName</th><th>lastName</th><th>email</th><th>hireDate</th></tr>
		</thead>
		
		<tbody id = "empTbody"> <!-- table-body -->
		
		</tbody>
		
	</table>
	<hr>
	<form>
	<!-- servlet + jsp : form 객체를 전송 (action, post, submit)
			js : action 버튼 X, id를 통해  
	
	-->
		<input type="text" name="employeeId" id="employeeId"></input><br>
		<input type="text" name="firstName" id="firstName"></input><br>
		<input type="text" name="lastName" id="lastName"></input><br>
		<input type="text" name="email" id="email"></input><br>
		<input type="text" name="hireDate" id="hireDate"></input><br>
				
	</form>
	<hr>
	<button id="btnInsert">등록</button> <button id="btnUpdate">수정</button> <button id="btnDelete">삭제</button> <button id="btnClear">초기화</button>
	<input>	
		
	<script>
		// 사원 목록 요청 -> json 데이터 수신 -> 화면 목록 ui 구성 (데이터목록)
		window.onload = function() {
			listEmp();
			
			document.querySelector("#btnClear").onclick = clearForm; // 초기화 버튼
			document.querySelector("#btnInsert").onclick = insertEmp; // 등록 버튼
			document.querySelector("#btnUpdate").onclick = updateEmp; // 수정 버튼
			document.querySelector("#btnDelete").onclick = deleteEmp; // 삭제 버튼
			
		}
		async function listEmp() {
			
			// 데이터 요청
			let url = 'emps/list';
			let response = await fetch(url); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			
			// 사원목록 화면 ui 
			makeListHtml(data);
		}
		//사용자 지정 속성은 data-~~
		function makeListHtml(list) {
			let listHtml = ``;
			list.forEach( emp => { 
				listHtml += 
					`<tr style="cursor:pointer" data-employeeId=\${emp.employeeId}> 
						<td>\${emp.employeeId}</td>
						<td>\${emp.firstName}</td>
						<td>\${emp.lastName}</td>
						<td>\${emp.email}</td>
						<td>\${emp.hireDate}</td>
					</tr>`
			} );
			
			document.querySelector("#empTbody").innerHTML = listHtml;
			
			document.querySelectorAll("#empTbody tr").forEach( tr => {
				tr.onclick = function(){
					let employeeId = this.getAttribute("data-employeeId");
					console.log(employeeId);
					detailEmp(employeeId);
				}
			} );
		}
		
		// ctrl+shift+c -> commend 처리
		async function detailEmp(employeeId) {
			alert(employeeId);
			// 데이터 요청
			let url = '/emps/detail/' + employeeId;
			let response = await fetch(url); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);

			// detail 화면 ui 
			document.querySelector("#employeeId").value = data.employeeId;
			document.querySelector("#firstName").value = data.firstName;
			document.querySelector("#lastName").value = data.lastName;
			document.querySelector("#email").value = data.email;
			document.querySelector("#hireDate").value = data.hireDate;
		}
		
		// 버튼 함수
		function clearForm(){
			document.querySelector("#employeeId").value = ""; // 단순 변수로 해둘 수도 있음
			document.querySelector("#firstName").value = "";
			document.querySelector("#lastName").value = "";
			document.querySelector("#email").value = "";
			document.querySelector("#hireDate").value = "";
		}
		
		async function insertEmp() {
			// 사용자 입력 form -> js book 객체 생성 -> Post 전송
			// js property는 : , 으로 구분
			// 객체 하나 만들기 (input으로 받음)
			let emp = {
				employeeId: document.querySelector("#employeeId").value, 
				firstName: document.querySelector("#firstName").value,
				lastName: document.querySelector("#lastName").value,
				email: document.querySelector("#email").value,
				hireDate: document.querySelector("#hireDate").value
			};
			
			let urlParams = new URLSearchParams(emp);
			// 데이터 요청, Post 타입으로 urlParams를 변수로 넣어서
			let fetchOptions = {
					method: "Post",
					body: urlParams
			}
			let url = '/emps/insert';
			let response = await fetch(url, fetchOptions); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			
			if (data.result == "success") {
				alert("사원 등록 성공 ㅎㅎ");
				listEmp();
				clearForm();
			} else {
				alert("사원 등록 실패 ㅜㅜ");
			}
			
		}
		async function updateEmp() {
			// 객체 생성 없이 바로 전달
			let urlParams = new URLSearchParams({
				employeeId: document.querySelector("#employeeId").value, 
				firstName: document.querySelector("#firstName").value,
				lastName: document.querySelector("#lastName").value,
				email: document.querySelector("#email").value,
				hireDate: document.querySelector("#hireDate").value
			});
			
			// 데이터 요청, Post 타입으로 urlParams를 변수로 넣어서
			let fetchOptions = {
					method: "Post",
					body: urlParams
			}
			
			let url = '/emps/update';
			let response = await fetch(url, fetchOptions); // 비동기 요청 (다음 코드를 바로 수행하지 않고 기다임)
			let data = await response.json();// response json화
			
			console.log(data);
			if (data.result == "success") {
				alert("사원 수정 성공 ㅎㅎ");
				listEmp();
				clearForm();
			} else {
				alert("사원 수정 실패 ㅜㅜ");
			}
			
		}
		// delete - Get방식
		async function deleteEmp(){
            let employeeId = document.querySelector("#employeeId").value;
            let url = '/emps/delete/' + employeeId; // path variable 대응 코드
            let response = await fetch(url); // 비동기 요청
            let data = await response.json();
            
            console.log(data);
            if( data.result == "success"){
                alert("사원 삭제 성공!");
                listEmp(); // 사원 목록 갱신
                clearForm(); // 입력 폼 초기화
            }else{
                alert("사원 삭제 실패!");
            }               
        }
		
		
		
		
		
	</script>
</body>
</html>