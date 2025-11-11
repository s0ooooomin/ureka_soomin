package com.mycom.myapp.board.service;

import org.springframework.stereotype.Service;

import com.mycom.myapp.board.dao.BoardDao;
import com.mycom.myapp.board.dto.BoardDto;
import com.mycom.myapp.board.dto.BoardResultDto;

@Service
public class BoardServiceImpl implements BoardService {

	private final BoardDao boardDao;
	public BoardServiceImpl (BoardDao boardDao) {
		this.boardDao = boardDao;
	}
	
	@Override
	public BoardResultDto insertBoard(BoardDto boardDto) {
		BoardResultDto boardResultDto = new BoardResultDto();
		
		try {
			// boardDao를 이용한 insert
			int ret = boardDao.insertBoard(boardDto);
			if (ret == 1) {
				boardResultDto.setResult("success");
			}else {
				boardResultDto.setResult("fail");
			}
		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult("fail");
		}
		
		
		return boardResultDto;
	}

	
}
