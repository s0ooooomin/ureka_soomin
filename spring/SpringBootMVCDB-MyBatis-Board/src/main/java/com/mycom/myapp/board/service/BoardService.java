package com.mycom.myapp.board.service;

import java.util.List;

import com.mycom.myapp.board.dto.BoardDto;
import com.mycom.myapp.board.dto.BoardParamDto;
import com.mycom.myapp.board.dto.BoardResultDto;

public interface BoardService {

	// 목록 
	// 전체 건수를 가져오는 메소드는 SERVICE에 필요없다. 
	// controller->service->dao 2번(X) controller->service 1번, service->dao 2번
	// service에서 boardResultDto 의 count까지 마무리 후 return해줌
	BoardResultDto listBoard(BoardParamDto boardParamDto);
	BoardResultDto listBoardSearchWord(BoardParamDto boardParamDto); 
	
	// 상세
	BoardResultDto detailBoard(BoardParamDto boardParamDto);
	
	// 조회수 처리는 이 인터페이스에 X
	// controller는 그냥 조회 요청 (business logic)
	// 조회 (B.L) 을 처리하는 boardServiceImpl 에서 상세 업무의 부분으로서 조회수 처리 포함되도록 구현
	
	// 등록, 수정, 삭제
	BoardResultDto insertBoard(BoardDto boardDto);
	BoardResultDto updateBoard(BoardDto boardDto);
	BoardResultDto deleteBoard(int boardId);
	
}
