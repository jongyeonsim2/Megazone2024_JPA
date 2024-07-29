package com.rubypaper.biz.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "employeeList")
@Entity
@Table(name = "S_DEPT")
public class Department {

	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DEPT_ID")
	private Long deptId;

	private String name;
	
	/*
	 * @OneToMany
	 * - fetch 의 기본 속성값 : LAZY
	 *   Many 에 해당하는 데이터를 항상 EAGER 로 조회되면 성능상 문제가 발생함으로
	 *   필요할 때 사용하도록하기 위해, LAZY로 설정됨.
	 */
	@OneToMany(mappedBy = "dept", fetch = FetchType.EAGER)
	private List<Employee> employeeList = new ArrayList<Employee>();
}
