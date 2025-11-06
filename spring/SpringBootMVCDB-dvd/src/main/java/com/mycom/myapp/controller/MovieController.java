package com.mycom.myapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycom.myapp.dto.MovieDTO;
import com.mycom.myapp.service.MovieService;
@Controller
public class MovieController {
	@Autowired
	MovieService movieService;
	
	@GetMapping("/movies")
	public String movieMain() {
		System.out.println("/movies");
		return "movies";
	}
	
	// Get
	// 영화목록 data
	@GetMapping("/movies/list")
	@ResponseBody
	public List<MovieDTO> listMovie(@RequestParam(value = "searchWord", defaultValue = "") String searchWord) {
		System.out.println("/books/list");
		List<MovieDTO> movieList = movieService.listMovie(searchWord);
		return movieList;
	}
	// 영화상세 data
	@GetMapping("/movies/detail/{movie_id}")
	@ResponseBody
	public MovieDTO detailMovie(@PathVariable Integer movie_id) {
		System.out.println("/books/detail");
		MovieDTO movieDto = movieService.detailMovie(movie_id);
		return movieDto;
	}
	
	// Post
	// 영화 등록
	@PostMapping("/movies/insert")
	@ResponseBody
	public Map<String, String> insertMovie(MovieDTO movieDto) {
		int ret = movieService.insertMovie(movieDto);
		Map<String, String> map = new HashMap<>();
		
		if (ret == 1) { map.put("result", "success"); }
		else { map.put("result", "fail"); }
		
		return map;
	}
	// 영화 수정
	@PostMapping("/movies/update")
	@ResponseBody
	public Map<String, String> updateMovie(MovieDTO movieDto) {
		int ret = movieService.updateMovie(movieDto);
		Map<String, String> map = new HashMap<>();
		
		if (ret == 1) { map.put("result", "success"); }
		else { map.put("result", "fail"); }
		
		return map;
	}
	// Get
	// 영화 삭제
	@GetMapping("/movies/delete/{movie_id}")
	@ResponseBody
	public Map<String, String> deleteMovie(@PathVariable Integer movie_id) {
		int ret = movieService.deleteMovie(movie_id);
		Map<String, String> map = new HashMap<>();
		
		if (ret == 1) { map.put("result", "success"); }
		else { map.put("result", "fail"); }
		
		return map;
	}
	
}
