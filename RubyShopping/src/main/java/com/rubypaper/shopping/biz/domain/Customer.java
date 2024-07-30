package com.rubypaper.shopping.biz.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "orderList")
@Table(name = "S_CUSTOMER")
public class Customer {
	
	// 회원 아이디
	@Id	@GeneratedValue
	@Column(name = "CUSTOMER_ID")
	private Long id;			

	// 회원 이름
	private String name;		

	// 회원 전화번호
	private String phone;		

	// 회원 특징 설명
	private String comments;		

	// 회원 주소
	/*
	 * @Embeddable로 정의된 객체를 참조. => @Embedded
	 */
	@Embedded
	private Address address; 	
	
	// 주문 목록
	// Order entity 가 생성자를 통해서 객체로 생성되면,
	// 주문 entity 가 바로 설정됨.( 연관관계 유지를 위해서 )
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	private List<Order> orderList = new ArrayList<Order>();

}
