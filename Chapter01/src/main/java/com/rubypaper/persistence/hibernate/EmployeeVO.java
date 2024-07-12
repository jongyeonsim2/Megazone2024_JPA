package com.rubypaper.persistence.hibernate;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity //하이버네이트에서 관리되는 엔터티 클래스. H2의 테이블과 연결
@Table(name="S_EMP")// EmployeeVO 는 S_EMP 테이블과 매핑
public class EmployeeVO {
	@Id// S_EMP 테이블의 PK 와 매핑
	private Long id;
	
	private String name;
	
	@Column(name="START_DATE")// 멤버명과 S_EMP 테이블의 칼럼명이 차이
								// 매핑될 칼럼명을 명시적으로 매핑
	private Timestamp startDate;
	
	private String title;
	
	@Column(name="DEPT_NAME")	
	private String deptName;
	
	private Double salary;	
	
	private String email;
}
