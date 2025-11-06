package com.mycom.myapp.dao;

import java.util.List;

import com.mycom.myapp.dto.MovieDTO;

public interface MovieDao {
	List<MovieDTO> listMovie();
	MovieDTO detailMovie( int movie_id );
	int insertMovie(MovieDTO movieDto);
	int updateMovie(int movie_id,int price);
	int deleteMovie(int movie_id);
	int borrowMovie(String cust_name, String cust_phone, int movie_id);
	
	List<MovieDTO> listMovie(String searchWord);
}
