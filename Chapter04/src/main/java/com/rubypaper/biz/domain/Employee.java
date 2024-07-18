package com.rubypaper.biz.domain;

import lombok.Data;

@Data
public class Employee {
	
	private Long id;

	private String name;

	private Department dept;
	
}
