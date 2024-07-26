package com.rubypaper.biz.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/*
 * Chapter03 의 JPA 개발 및 테스트 환경 테스트 엔터티 클래스
 * Chapter02 에서 복사함.
 * 
 */

@Data
@Entity
public class Employee_Test {
	@Id// S_EMP 테이블의 PK 와 매핑
	private Long id;
	
	private String name;
	
	private String mailId;
	
	@Column(name="START_DATE")// 멤버명과 S_EMP 테이블의 칼럼명이 차이
								// 매핑될 칼럼명을 명시적으로 매핑
	private Date startDate;
	
	private String title;
	
	@Column(name="DEPT_NAME")	
	private String deptName;
	
	private Double salary;	
	
	@Column(name = "COMMISITION_PCT")
	private Double commisionPct;
}
