package com.rubypaper.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rubypaper.biz.domain.Employee;
import com.rubypaper.biz.persistence.EmployeeRepository;

@Service("empService")
public class EmployeeService {

	@Autowired
	private EmployeeRepository empRepository;
	
	public void insertEmployee(Employee employee) {
		empRepository.insertEmployee(employee);
	}
	
	public void updateEmployee(Employee employee) {
		empRepository.deleteEmployee(employee);
	}
	
	public Employee getEmployee(Employee employee) {
		return empRepository.getEmployee(employee);
	}
	
	public List<Employee> getEmployeeList() {
		return empRepository.getEmployeeList();
	}
	
}
