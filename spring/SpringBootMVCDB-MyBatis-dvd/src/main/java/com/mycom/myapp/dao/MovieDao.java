package com.mycom.myapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.myapp.dto.MovieDto;

@Mapper
public interface MovieDao {
	List<MovieDto> listMovie();
	MovieDto detailMovie( int movie_id );
	int insertMovie(MovieDto movieDto);
	int updateMovie(int movie_id,int price);
	int deleteMovie(int movie_id);
	int borrowMovie(String cust_name, String cust_phone, int movie_id);
	
	List<MovieDto> listMovieLike(String searchWord);

}