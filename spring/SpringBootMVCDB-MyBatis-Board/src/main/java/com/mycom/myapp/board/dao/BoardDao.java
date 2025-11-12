package com.mycom.myapp.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.myapp.board.dto.BoardDto;
import com.mycom.myapp.board.dto.BoardParamDto;

@Mapper
public interface BoardDao {

	// 목록 & 목록별 전체 건수
	List<BoardDto> listBoard(BoardParamDto boardParamDto); // 전체 목록 ( limit, offset )
	int listBoardTotalCount(); // 전체 건수
	
	List<BoardDto> listBoardSearchWord(BoardParamDto boardParamDto); // 서치 결과 목록 (limit, offset, searchWord)
	int listBoardSearchWordTotalCount(BoardParamDto boardParamDto); // 검색 전체 건수 (searchWord 필요) 
	
	// 상세
	BoardDto detailBoard(BoardParamDto boardParamDto);
	// 등록
	int insertBoard(BoardDto boardDto);
	int updateBoard(BoardDto boardDto);
	int deleteBoard(int boardId);
}
