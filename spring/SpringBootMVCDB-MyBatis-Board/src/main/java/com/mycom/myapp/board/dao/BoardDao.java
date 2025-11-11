package com.mycom.myapp.board.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.myapp.board.dto.BoardDto;

@Mapper
public interface BoardDao {

	// 등록
	int insertBoard(BoardDto boardDto);
}
