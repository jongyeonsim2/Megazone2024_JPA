package com.rubypaper.biz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "S_EMP")
public class Employee {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 25, nullable = false)
	private String name;

	@ManyToOne//다:1 관계( 다:사원, 1:부서, 여러명의 사원이 한 부서에 속함)
	@JoinColumn(name="DEPT_ID")//S_EMP table 생성시 FK 설정
	private Department dept;
	
}
