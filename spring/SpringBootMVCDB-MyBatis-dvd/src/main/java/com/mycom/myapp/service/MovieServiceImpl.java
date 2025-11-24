package com.mycom.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycom.myapp.dao.MovieDao;
import com.mycom.myapp.dto.MovieDto;

@Service
public class MovieServiceImpl implements MovieService {

	private final MovieDao movieDao;
	public MovieServiceImpl(MovieDao movieDao) {
		this.movieDao = movieDao;
	}

	@Override
	public List<MovieDto> listMovie() {
		return movieDao.listMovie();
	}

	@Override
	public MovieDto detailMovie(int movie_id) {
		return movieDao.detailMovie(movie_id);
	}

	@Override
	public int insertMovie(MovieDto movieDto) {
		return movieDao.insertMovie(movieDto);
	}

	@Override
	public int updateMovie(MovieDto movieDto) {
	    // movieDto 객체에서 movie_id와 price를 꺼냅니다.
	    return movieDao.updateMovie(movieDto.getMovie_id(), movieDto.getPrice());
	}

	@Override
	public int deleteMovie(int movie_id) {
		return movieDao.deleteMovie(movie_id);
	}

//	@Override
//	public int borrowMovie(String cust_name, String cust_phone, int movie_id) {
//		return movieDao.borrowMovieByParams(cust_name, cust_phone, movie_id);
//	}

	@Override
	public List<MovieDto> listMovieLike(String searchWord) {
		return movieDao.listMovieLike(searchWord);
	}
	

}
