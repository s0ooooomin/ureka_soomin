package com.mycom.myapp.service;

import java.util.List;

import com.mycom.myapp.dto.MovieDTO;

public interface MovieService {
	List<MovieDTO> listMovie(String searchWord);
	MovieDTO detailMovie( int movie_id );
	int insertMovie(MovieDTO movieDto);
	int updateMovie(MovieDTO movieDto);
	int deleteMovie( int movie_id );
	int borrowMovie(String cust_name, String cust_phone, int movie_id);
	
}
