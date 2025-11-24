package com.mycom.myapp.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.mycom.myapp.board.dao.BoardDao;
import com.mycom.myapp.board.dto.BoardDto;
import com.mycom.myapp.board.dto.BoardParamDto;
import com.mycom.myapp.board.dto.BoardResultDto;

@Service
public class BoardServiceImpl implements BoardService {

	private final BoardDao boardDao;
	public BoardServiceImpl (BoardDao boardDao) {
		this.boardDao = boardDao;
	}
	
	// 예외처리테스트
	// #1. try-catch 밖
	// #2. try-catch 안
	
	// 목록
	@Override
	public BoardResultDto listBoard(BoardParamDto boardParamDto) {
		BoardResultDto boardResultDto = new BoardResultDto();
		
		// #1. try-catch 밖
//		String s = null;
//		s.length();
		
		
		try {
//			// #2. try-catch 안
//			String s = null;
//			s.length();
			
			List<BoardDto> list = boardDao.listBoard(boardParamDto);
			boardResultDto.setList(list);
			
			int count = boardDao.listBoardTotalCount();
			boardResultDto.setCount(count);
			
			boardResultDto.setResult("success");
		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult("fail");
		}
		
		return boardResultDto;
	}
	// 검색
	@Override
	public BoardResultDto listBoardSearchWord(BoardParamDto boardParamDto) {
		BoardResultDto boardResultDto = new BoardResultDto();
		try {
			List<BoardDto> list = boardDao.listBoardSearchWord(boardParamDto);
			boardResultDto.setList(list);
			
			int count = boardDao.listBoardSearchWordTotalCount(boardParamDto);
			boardResultDto.setCount(count);
			
			boardResultDto.setResult("success");
		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult("fail");
		}
		
		return boardResultDto;
	}
	
	// 상세 
	// 1. 조회 
	// 2. sameUser 
	// 3. 조회수 처리
	// 트랜잭션 테스트
	// 1. try-catch 안에서 예외발생 :: rollback X
	// 2. try-catch 없이 예외발생 :: rollback X
	// 3. try-catch 없이 예외발생 + @Transactional 추가 <= AOP매니저가 Pointcut으로 관리 (Proxy)
	// 4. try-catch 안에서 예외발생 + @Transactional 추가 <= Proxy가 만들어졌지만 인식X why? 메소드 내의 try-catch에 의해 밖으로 예외 전달X
	// 5. try-catch 안에서 예외발생 + @Transactional 추가 -> catch에서 throw 예외 => Rollback O
	// 6. try-catch 안에서 + @Transactional 추가 + catch 에서 TansactionAspectSupport 를 통한 rollback 정책 세팅 → result:fail 처리 O
	@Override
	@Transactional
    public BoardResultDto detailBoard(BoardParamDto boardParamDto) {
        BoardResultDto boardResultDto = new BoardResultDto();
        
        try {
        	// 조회수 처리
        	
        	// board_user_read에 board_id & user_seq의 행의 개수 COUNT
        	int userReadCnt = boardDao.countBoardUserRead(boardParamDto); 
        	if (userReadCnt == 0) {  // 현재 사용자가 현재 게시글을 처음 조회하는 경우
        		// -> 조회함 표시 (board_user_read 에 user_seq, boardId 새로 등록)
        		boardDao.insertBoardUserRead(boardParamDto);
        		// 조회수 증가
        		// 트랜잭션 테스트
        		// 예외 발생
        		String s = null;
        		s.length();
        		// 트랜잭션
        		boardDao.updateBoardUserRead(boardParamDto.getBoardId());
		
        	}
        	
            BoardDto boardDto = boardDao.detailBoard(boardParamDto); // 게시글 1건 데이터
            // same user
            // boardDto.getUserSeq() <= 현재 게시글의 글 작성자 userSeq
            // boardParamDto.getUserSeq() <= 현재 게시글의 조회자 userSeq
            if( boardDto.getUserSeq() == boardParamDto.getUserSeq() ) { // 내가 쓴 글이라면
                boardDto.setSameUser(true);
            }else {
                boardDto.setSameUser(false);
            }
            boardResultDto.setDto(boardDto);
            boardResultDto.setResult("success");
        }catch(Exception e) {
            e.printStackTrace();
            boardResultDto.setResult("fail");
            
            // Proxy 에게 예외 직접 전달
//            throw new RuntimeException("~~~~");
            
            // Proxy 에게 정책 전달 (Spring 제안 방법)
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return boardResultDto;
    }
	
	// 등록, 수정, 삭제
	@Override
	public BoardResultDto insertBoard(BoardDto boardDto) {
		BoardResultDto boardResultDto = new BoardResultDto();
		
		try {
			// boardDao를 이용한 insert
			int ret = boardDao.insertBoard(boardDto);
			if (ret == 1) boardResultDto.setResult("success");
			else boardResultDto.setResult("fail");
		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult("fail");
		}
		
		
		return boardResultDto;
	}

	@Override
	public BoardResultDto updateBoard(BoardDto boardDto) {
		BoardResultDto boardResultDto = new BoardResultDto();
		
		try {
			// boardDao를 이용한 insert
			int ret = boardDao.updateBoard(boardDto);
			if (ret == 1) boardResultDto.setResult("success");
			else boardResultDto.setResult("fail");

		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult("fail");
		}
		
		
		return boardResultDto;
	}

	@Override
	public BoardResultDto deleteBoard(int boardId) {
		BoardResultDto boardResultDto = new BoardResultDto();
		
		try {
			// boardDao를 이용한 insert
			int ret = boardDao.deleteBoard(boardId);
			if (ret == 1) boardResultDto.setResult("success");
			else boardResultDto.setResult("fail");

		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult("fail");
		}
		
		
		return boardResultDto;
	}



	



	
}
