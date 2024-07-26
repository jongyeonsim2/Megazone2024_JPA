package com.rubypaper.biz.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

/*
 * 다대다 단방양 용
 */

@Data
@Entity
@Table(name = "S_PRODUCT")
public class Product {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;			// 상품 아이디
	
	private String name;		// 상품 이름
	
	@Column(name = "SHORT_DESC")
	private String shortDesc;	// 상품 설명
	
	private String category;		// 카테고리

//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "S_ITEM",
//			joinColumns = @JoinColumn(name="PRODUCT_ID"),
//			inverseJoinColumns=@JoinColumn(name = "ORD_ID"),
//			uniqueConstraints = @UniqueConstraint(columnNames = {"PRODUCT_ID", "ORD_ID"})
//	)
//	private List<Order> productList = new ArrayList<Order>();
}
