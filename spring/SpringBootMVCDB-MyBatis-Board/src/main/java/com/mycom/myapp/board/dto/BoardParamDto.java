package com.mycom.myapp.board.dto;

public class BoardParamDto {
	// 목록 (백 -> 프론트) 파라미터 전달
	private int limit;			// 페이지네이션
	private int offset;			// 페이지네이션
	private String searchWord; 	// 제목 검색
	
	// 상세
	private int boardId; // 게시글 key값
	private int userSeq; // 작성자-조회자 일치 확인
	
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public int getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	@Override
	public String toString() {
		return "BoardParamDto [limit=" + limit + ", offset=" + offset + ", searchWord=" + searchWord + ", boardId="
				+ boardId + ", userSeq=" + userSeq + "]";
	}
	
	
	
	
}
