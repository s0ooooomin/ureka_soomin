package com.mycom.myapp.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycom.myapp.common.DBManager;
import com.mycom.myapp.dto.MovieDTO;
@Repository
public class MovieDaoImpl implements MovieDao{
	

	@Autowired
	DataSource dataSource; // Connection pool

	@Override
	public List<MovieDTO> listMovie() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String listSql = "SELECT * FROM movies;";
		List<MovieDTO> list = new ArrayList<>();
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(listSql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				MovieDTO dto = new MovieDTO();
				
				dto.setMovie_id(rs.getInt("movie_id"));
				dto.setMovie_title(rs.getString("movie_title"));
				dto.setDirector(rs.getString("director"));
				dto.setPrice(rs.getInt("price"));
				
				list.add(dto);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return list;		
	}

	@Override
	public MovieDTO detailMovie(int movie_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String detailSql = "SELECT * FROM movies WHERE movie_id = ?;";
		MovieDTO dto = null;
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(detailSql);
			
			pstmt.setInt(1, movie_id);
			rs = pstmt.executeQuery();
			
			// rs -> dto 객체로 만들기
			if (rs.next()) {
				dto = new MovieDTO();
				
				dto.setMovie_id(rs.getInt("movie_id"));
				dto.setMovie_title(rs.getString("movie_title"));
				dto.setDirector(rs.getString("director"));
				dto.setPrice(rs.getInt("price"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return dto;
	}

	@Override
	public int insertMovie(MovieDTO movieDto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String insertSql = "insert into movies values (?, ?, ?, ?);";
		int ret = 0;
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(insertSql);	// query
			
			// 파라미터
			pstmt.setInt(1,movieDto.getMovie_id());
			pstmt.setString(2,movieDto.getMovie_title());
			pstmt.setString(3,movieDto.getDirector());
			pstmt.setInt(4, movieDto.getPrice());
			
			ret = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret ;
	}

	@Override
	public int updateMovie(int movie_id, int price) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String updateSql = "update movies set price = ? where movie_id = ?;";
		int ret = 0;
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(updateSql);	// query
			
			// 파라미터
			pstmt.setInt(1, price);
			pstmt.setInt(2, movie_id);
			
			ret = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret ;
	}

	@Override
	public int deleteMovie(int movie_id) {
		String deleteSql = "delete from movies where movie_id = ?;";
		
		int ret = -1;
		
		// try-with-resources 블럭 (autoclosable 객체가 생성됨)
		try (
				Connection con = dataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(deleteSql);
			){
			
			// 파라미터
			pstmt.setInt(1, movie_id);
			
			ret = pstmt.executeUpdate();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}

	// 대여하기 (movie -> order 추가)
	@Override
	public int borrowMovie(String cust_name, String cust_phone, int movie_id) {
		Connection con = null;
 		CallableStatement cstmt = null;
 		
 		int ret = -1;
        // 2. 프로시저 수행
        String procedureSql = "CALL Order_Movie(?,?,?);";
        try {
 			con = dataSource.getConnection();
 			cstmt = con.prepareCall(procedureSql);	// query
 			
 			// 파라미터
 			cstmt.setString(1, cust_name);
 			cstmt.setString(2, cust_phone);
 			cstmt.setInt(3, movie_id);
 			
 			ret = cstmt.executeUpdate();
 			
 		} catch (SQLException e) {
 			e.printStackTrace();
 		} finally {
 			try {
 	            if (cstmt != null) cstmt.close();
 	            if (con != null) con.close();
 	        } catch (SQLException e) {
 	            e.printStackTrace();
 	        }
 		}
        
        return ret;
	}

	// 검색
	@Override
	public List<MovieDTO> listMovie(String searchWord) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String listSql = "SELECT * FROM movies"
				+ " WHERE movie_id LIKE ?"
				+ " OR movie_title LIKE ?"
				+ " OR director LIKE ?"
				+ " OR price LIKE ?;";
		List<MovieDTO> list = new ArrayList<>();
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(listSql);
			// 파라미터
			pstmt.setString(1, "%" + searchWord + "%");
			pstmt.setString(2, "%" + searchWord + "%");
			pstmt.setString(3, "%" + searchWord + "%");
			pstmt.setString(4, "%" + searchWord + "%");

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				MovieDTO dto = new MovieDTO();
				
				dto.setMovie_id(rs.getInt("movie_id"));
				dto.setMovie_title(rs.getString("movie_title"));
				dto.setDirector(rs.getString("director"));
				dto.setPrice(rs.getInt("price"));
				
				list.add(dto);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return list;
	}

}
