package com.mycom.myapp.board.controller;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@GetMapping("/list")
	public BoardResultDto listBoard(BoardParamDto boardParamDto) {
		BoardResultDto boardResultDto = null;
		
		if (Strings.isEmpty( boardParamDto.getSearchWord() ) ) {
			boardResultDto = boardService.listBoard();
		}else {
			boardResultDto = boardService.listBoardSearch(boardParamDto);
		}
		
		return boardResultDto;
	}

	// 상세
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
