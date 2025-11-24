package com.mycom.myapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycom.myapp.dto.MovieDto;

@Mapper
public interface MovieDao {
	List<MovieDto> listMovie();
	MovieDto detailMovie( int movie_id );
	int insertMovie(MovieDto movieDto);
	int updateMovie(int movie_id,int price);
	int deleteMovie(int movie_id);
	
	List<MovieDto> listMovieLike(String searchWord);

}