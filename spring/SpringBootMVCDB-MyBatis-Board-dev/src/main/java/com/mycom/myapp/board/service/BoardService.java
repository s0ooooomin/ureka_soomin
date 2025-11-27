package com.mycom.myapp.board.service;

import java.util.List;

import com.mycom.myapp.board.dto.BoardDto;
import com.mycom.myapp.board.dto.BoardParamDto;
import com.mycom.myapp.board.dto.BoardResultDto;

public interface BoardService {

	// 목록
	BoardResultDto listBoard();
	// 상세
	BoardResultDto detailBoard(BoardParamDto boardParamDto);
	// 등록, 수정, 삭제
	BoardResultDto insertBoard(BoardDto boardDto);
	BoardResultDto updateBoard(BoardDto boardDto);
	BoardResultDto deleteBoard(int boardId);
	
	BoardResultDto listBoardSearch (BoardParamDto boardParamDto);

	
}
