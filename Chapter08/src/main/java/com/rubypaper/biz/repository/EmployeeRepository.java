package com.rubypaper.biz.repository;

import org.springframework.data.repository.CrudRepository;

import com.rubypaper.biz.domain.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
