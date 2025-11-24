package com.mycom.myapp.board.controller;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycom.myapp.board.dto.BoardDto;
import com.mycom.myapp.board.dto.BoardParamDto;
import com.mycom.myapp.board.dto.BoardResultDto;
import com.mycom.myapp.board.service.BoardService;
import com.mycom.myapp.user.dto.UserDto;

import jakarta.servlet.http.HttpSession;

@Controller
@ResponseBody
@RequestMapping("/boards")
public class BoardController {
	// 생성자주입
	private final BoardService boardService;
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	// 목록
	// 파라미터에 searchWord O -> 검색목록, X -> 전체 목록
	// 전체 목록도 limit, offset을 가지도록 수정.
	@GetMapping("/list")
	public BoardResultDto listBoard(BoardParamDto boardParamDto) {
		BoardResultDto boardResultDto = null;
		if ( Strings.isEmpty ( boardParamDto.getSearchWord() ) ) {
			boardResultDto = boardService.listBoard(boardParamDto);
		}else {
			boardResultDto = boardService.listBoardSearchWord(boardParamDto);
		}
		return boardResultDto;
	}

	// 상세
	// 조회수 처리
	// #1. 무조건 증가
	// 		내가 내 글 여러번 & 남이 내 글 여러번 모두 계속 증가
	//		- 데이터의 왜곡 발생 (한사람이 조회수 조작O)
	// #2. 한 사용자는 한 게시글에 대해 조회수 1회만 증가
	//		- 데이터의 왜곡은 없지만 복잡
	// 		- db 테이블에 사용자-게시글 조회 여부 기록 (visit같은거)
	@GetMapping("/detail/{boardId}")
	public BoardResultDto detailBoard(@PathVariable("boardId") Integer boardId, HttpSession session) {
		BoardParamDto boardParamDto = new BoardParamDto();
		
		boardParamDto.setBoardId(boardId);
		int userSeq = ( (UserDto) session.getAttribute("userDto") ).getUserSeq();
		boardParamDto.setUserSeq(userSeq);
		
		return boardService.detailBoard(boardParamDto);
	}
	
	// 등록
	@PostMapping("/insert")
	public BoardResultDto insertBoard(BoardDto boardDto, HttpSession session) {
		int userSeq = ( (UserDto) session.getAttribute("userDto") ).getUserSeq(); // 현재 로그인한 사용자의 userSeq 가져오기
		boardDto.setUserSeq(userSeq); // 위에서 받아온 userSeq를 받아온 boradDto에 넣어줌
		return boardService.insertBoard(boardDto);
	}

	// 수정
	// 세션에서 userSeq가 같지 않은 사용자는 update 버튼이 존재하지 X (update버튼이 있다면 동일유저)
	@PostMapping("/update")
	public BoardResultDto updateBoard(BoardDto boardDto) {
		return boardService.updateBoard(boardDto);
	}

	// 삭제
	@GetMapping("/delete/{boardId}")
	public BoardResultDto deleteBoard(@PathVariable("boardId") Integer boardId) {
		return boardService.deleteBoard(boardId);
	}
	
}
