package com.mycom.myapp.board.service;

import com.mycom.myapp.board.dto.BoardDto;
import com.mycom.myapp.board.dto.BoardResultDto;

public interface BoardService {
	
	// 등록
	BoardResultDto insertBoard(BoardDto boardDto);
}
