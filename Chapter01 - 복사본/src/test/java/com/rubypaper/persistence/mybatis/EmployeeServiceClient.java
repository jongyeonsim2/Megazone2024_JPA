package com.rubypaper.persistence.mybatis;

import java.sql.Timestamp;
import java.util.List;

public class EmployeeServiceClient {
	
	public static void main(String[] args) {
		
		// 신규 사원 정보
		EmployeeVO vo = new EmployeeVO();
		vo.setName("둘리");
		vo.setStartDate(new Timestamp(System.currentTimeMillis()));
		vo.setTitle("사원");
		vo.setDeptName("영업부");
		vo.setSalary(1700.00);
		
		EmployeeDAO employeeDAO = new EmployeeDAO();
		employeeDAO.insertEmployee(vo);
				
		List<EmployeeVO> employeeList = employeeDAO.getEmployeeList();
		for (EmployeeVO employee : employeeList) {
			System.out.println("---> " + employee.toString());
		}
	}
}
