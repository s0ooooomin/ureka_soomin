package com.mycom.myapp.user.dto;

// 사용자 표준 응답
// 등록, 수정, 삭제 : int -> Result (s/f)
// 목록, 상세 : List<Dto> Dto -> Result
// 위의 모든 처리를 하나의 REsultDto로 처리
public class UserResultDto {
	private String result; // success/fail (그치만 문자열이 아닌 resultDto 타입)

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
