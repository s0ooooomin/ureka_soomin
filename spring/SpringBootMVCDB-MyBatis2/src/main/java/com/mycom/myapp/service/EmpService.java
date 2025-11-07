package com.mycom.myapp.service;

import java.util.List;
import java.util.Map;

import com.mycom.myapp.dto.EmpDto;

public interface EmpService {
	// Emp-mapper.xml 에 구현한 것들
	List<EmpDto> listEmp();
	EmpDto detailEmp(int employeeId);
	int insertEmp(EmpDto empDto);
	int updateEmp(EmpDto empDto);
	int deleteEmp(int employeeId);
	
	// Emp.mapper-2.xml 에 구현한 것들
	List<EmpDto> listEmpLike(String searchWord);
	List<EmpDto> listEmpMap();
	List<EmpDto> listEmpWhereIf(Map<String, String> map);


}
