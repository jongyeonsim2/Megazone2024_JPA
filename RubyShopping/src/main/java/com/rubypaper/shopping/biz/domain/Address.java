package com.rubypaper.shopping.biz.domain;

import javax.persistence.Embeddable;

import lombok.Data;

/*
 * Entity class 가 아님.
 * 다른  entity에 포함되는 클래스. => @Embeddable
 */
@Embeddable
@Data
public class Address {

	// 도시
	private String city;		

	// 도로명
	private String roadName;		

	// 우편번호
	private String zipCode;		
}
