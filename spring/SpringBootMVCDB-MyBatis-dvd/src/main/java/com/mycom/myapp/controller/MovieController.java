package com.mycom.myapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycom.myapp.dto.MovieDto;
import com.mycom.myapp.service.MovieService;
@Controller
@ResponseBody
@RequestMapping("/movies")
public class MovieController {
	
	private final MovieService movieService;
	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}
	
	// 영화목록 data
	@GetMapping("/list")
	@ResponseBody
	public List<MovieDto> listMovie() {
		System.out.println("/movies/list");
		List<MovieDto> movieList = movieService.listMovie();
		return movieList;
	}
	// 영화상세 data
	@GetMapping("/detail/{movie_id}")
	@ResponseBody
	public MovieDto detailMovie(@PathVariable Integer movie_id) {
		System.out.println("/movies/detail");
		MovieDto movieDto = movieService.detailMovie(movie_id);
		return movieDto;
	}
	
	// Post
	// 영화 등록
	@PostMapping("/insert")
	@ResponseBody
	public Map<String, String> insertMovie(MovieDto movieDto) {
		int ret = movieService.insertMovie(movieDto);
		Map<String, String> map = new HashMap<>();
		
		if (ret == 1) { map.put("result", "success"); }
		else { map.put("result", "fail"); }
		
		return map;
	}
	// 영화 수정
	@PostMapping("/update")
	@ResponseBody
	public Map<String, String> updateMovie(MovieDto movieDto) {
		int ret = movieService.updateMovie(movieDto);
		Map<String, String> map = new HashMap<>();
		
		if (ret == 1) { map.put("result", "success"); }
		else { map.put("result", "fail"); }
		
		return map;
	}
	// Get
	// 영화 삭제
	@GetMapping("/delete/{movie_id}")
	@ResponseBody
	public Map<String, String> deleteMovie(@PathVariable Integer movie_id) {
		int ret = movieService.deleteMovie(movie_id);
		Map<String, String> map = new HashMap<>();
		
		if (ret == 1) { map.put("result", "success"); }
		else { map.put("result", "fail"); }
		
		return map;
	}
	
	// 검색
	@GetMapping("/listMovieLike") 
	public List<MovieDto> listMovieLike(@RequestParam String searchWord) { // requestParam : 반드시 parameter가 request에 담겨져 와야함
		return movieService.listMovieLike(searchWord);
	}
	
}
