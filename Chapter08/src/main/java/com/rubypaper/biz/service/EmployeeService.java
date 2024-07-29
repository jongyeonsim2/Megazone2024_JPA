package com.rubypaper.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rubypaper.biz.domain.Employee;
//import com.rubypaper.biz.persistence.EmployeeRepository;
import com.rubypaper.biz.repository.EmployeeRepository;

@Service("empService")
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository empRepository;
	
	public void insertEmployee(Employee employee) {
		//empRepository.insertEmployee(employee);
		empRepository.save(employee);
	}
	
	public void updateEmployee(Employee employee) {
		//empRepository.deleteEmployee(employee);
		empRepository.save(employee);
	}
	
	public Employee getEmployee(Employee employee) {
		//return empRepository.getEmployee(employee);
		return empRepository.findById(employee.getId()).get();	
	}
	
	public List<Employee> getEmployeeList() {
		//return empRepository.getEmployeeList();
		return (List<Employee>)empRepository.findAll();
	}
	
}
