package com.mycom.myapp.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.myapp.board.dto.BoardDto;
import com.mycom.myapp.board.dto.BoardParamDto;

@Mapper
public interface BoardDao {

	// 목록
	List<BoardDto> listBoard();
	// 상세
	BoardDto detailBoard(BoardParamDto boardParamDto);
	// 등록
	int insertBoard(BoardDto boardDto);
	int updateBoard(BoardDto boardDto);
	int deleteBoard(int boardId);
	
	List<BoardDto> listBoardSearch (String searchWord);
}
