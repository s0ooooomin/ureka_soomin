package com.mycom.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycom.myapp.dao.MovieDao;
import com.mycom.myapp.dto.MovieDTO;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	MovieDao movieDao;

	@Override
	public List<MovieDTO> listMovie(String searchWord) {
		return movieDao.listMovie(searchWord);
	}

	@Override
	public MovieDTO detailMovie(int movie_id) {
		return movieDao.detailMovie(movie_id);
	}

	@Override
	public int insertMovie(MovieDTO movieDto) {
		return movieDao.insertMovie(movieDto);
	}

	@Override
	public int updateMovie(MovieDTO movieDto) {
	    // movieDto 객체에서 movie_id와 price를 꺼냅니다.
	    return movieDao.updateMovie(movieDto.getMovie_id(), movieDto.getPrice());
	}

	@Override
	public int deleteMovie(int movie_id) {
		return movieDao.deleteMovie(movie_id);
	}

	@Override
	public int borrowMovie(String cust_name, String cust_phone, int movie_id) {
		return movieDao.borrowMovie(cust_name, cust_phone, movie_id);
	}
	

}
