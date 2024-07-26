package com.rubypaper.biz.domain;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name = "S_ORD")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 주문 아이디

	@Column(name = "CUSTOMER_ID")
	private Long customerId; // 고객 아이디

	@Column(name = "ORDER_DATE")
	private Date orderDate; // 주문 날짜

	private Double total; // 주문 금액

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "S_ITEM",
			joinColumns = @JoinColumn(name="ORD_ID"),
			inverseJoinColumns=@JoinColumn(name = "PRODUCT_ID"),
			uniqueConstraints = @UniqueConstraint(columnNames = {"ORD_ID", "PRODUCT_ID"})
	)
	private List<Product> productList = new ArrayList<Product>();
}
