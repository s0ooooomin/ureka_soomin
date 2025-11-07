package com.mycom.myapp.service;

import java.util.List;

import com.mycom.myapp.dto.MovieDto;

public interface MovieService {
	List<MovieDto> listMovie();
	MovieDto detailMovie( int movie_id );
	int insertMovie(MovieDto movieDto);
	int updateMovie(MovieDto movieDto);
	int deleteMovie( int movie_id );
	int borrowMovie(String cust_name, String cust_phone, int movie_id);
	
	List<MovieDto> listMovieLike(String searchWord);
}
