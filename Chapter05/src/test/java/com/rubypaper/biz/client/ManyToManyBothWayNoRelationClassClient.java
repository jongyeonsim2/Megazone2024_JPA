package com.rubypaper.biz.client;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Order;
import com.rubypaper.biz.domain.Product;

public class ManyToManyBothWayNoRelationClassClient {
	public static void main(String[] args) {
		EntityManagerFactory emf = 
			Persistence.createEntityManagerFactory("Chapter05");
		try {		
			dataInsert(emf);
			dataSelect(emf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			emf.close();
		}
	}
	
	private static void dataSelect(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		
		// 검색한 Order를 통해 Product 목록을 출력한다.
		Order order = em.find(Order.class, 1L);
		
		System.out.println(order.getId() + "번 주문에 대한 상품 목록");
		List<Product> productList = order.getProductList();
		for (Product product : productList) {
			System.out.println("---> " + product.getName());
		}

	}
	
	private static void dataInsert(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();		
		
		// 1번 상품 등록
		Product product1 = new Product();
		product1.setName("LG 통돌이 세탁기");
		em.persist(product1);
		
		// 2번 상품 등록
		Product product2 = new Product();
		product2.setName("다이슨 청소기");
		em.persist(product2);
		
		// 1번 주문 등록
		Order order = new Order();
		order.setOrderDate(new Date());
		order.getProductList().add(product1);
		order.getProductList().add(product2);
		em.persist(order);

		em.getTransaction().commit();
		em.close();
	}
}
