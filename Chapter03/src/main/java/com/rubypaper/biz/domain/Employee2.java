package com.rubypaper.biz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/*
 * 
 * 
 */

@Data
@Entity
@Table(name = "S_EMP")
public class Employee2 {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(length = 7, nullable = false)
	private Long id;
	
	@Column(length = 25, nullable = false)
	private String name;
}
