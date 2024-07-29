package com.rubypaper.biz.client;

import java.util.List;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.rubypaper.biz.domain.Department;
import com.rubypaper.biz.domain.Employee;
import com.rubypaper.biz.service.DepartmentService;
import com.rubypaper.biz.service.EmployeeService;

public class EmployeeSerivceClient {

	public static void main(String[] args) {
		/* spring container 생성
		 *
		 * - 개발자가 객체 생성 및 관리를 다른 누군가에게 위임을 하고 싶음.
		 *   => new 연산자를 사용하지 않게 되어, 약결합으로 구현이 가능해짐.
		 */
		GenericXmlApplicationContext container = 
				new GenericXmlApplicationContext("spring/business-layer.xml");
		
		/*
		 * spring container 에게 객체 생성을 위임하여,
		 * 필요한 객체는 불러서 사용하면 됨.
		 */
		DepartmentService deptService = 
				(DepartmentService) container.getBean("deptService");
		EmployeeService employeeService = 
				(EmployeeService) container.getBean("empService");

		dataInsert(deptService, employeeService);
		dataSelect(employeeService);
		dataSelect(deptService);

		container.close();
	}
	
	private static void dataSelect(EmployeeService employeeService) {
		List<Employee> employeeList = employeeService.getEmployeeList();
		
		System.out.println("검색된 직원 목록");
		for(Employee employee : employeeList) {
			System.out.println( "----->" +
					employee.getName() + " 의 부서명 : "
					+ employee.getDept().getName()
					);
		}
	}
	
	private static void dataSelect(DepartmentService departmentService) {
		Department department = new Department();
		department.setDeptId(1L);
		Department findDept = departmentService.getDepartment(department);
		
		System.out.println("부서명 : " + findDept.getName());
		
		System.out.println("사원 정보");
		for (Employee employee : findDept.getEmployeeList()) {
			System.out.println("---->" + employee.toString());
		}
	}

	private static void dataInsert(DepartmentService deptService, EmployeeService employeeService) {
		Department department1 = new Department();
		department1.setName("개발부");
		deptService.insertDepartment(department1);

		Department department2 = new Department();
		department2.setName("영업부");
		deptService.insertDepartment(department2);

		for (int i = 1; i <= 5; i++) {
			Employee employee = new Employee();
			employee.setName("개발직원 " + i);
			employee.setSalary(i * 12700.00);
			employee.setMailId("Dev-" + i);
			employee.setDept(department1);
			employeeService.insertEmployee(employee);
		}

		for (int i = 1; i <= 7; i++) {
			Employee employee = new Employee();
			employee.setName("영업직원 " + i);
			employee.setSalary(i * 24300.00);
			employee.setMailId("Sales-" + i);
			employee.setDept(department2);
			employeeService.insertEmployee(employee);
		}
	}
}
