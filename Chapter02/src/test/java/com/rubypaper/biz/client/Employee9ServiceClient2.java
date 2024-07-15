package com.rubypaper.biz.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Employee9;

/*
 * 식별자 Sequence 사용한 실습
 * 
 */

public class Employee9ServiceClient2 {

	public static void main(String[] args) {
		// <persistence-unit name="Chapter02"> 의 설정 정보를 참조
		EntityManagerFactory emf = 
				Persistence.createEntityManagerFactory("Chapter02");
		
		EntityManager em = emf.createEntityManager();
		
		// 엔터티 트랜잭션 생성
		EntityTransaction tx = em.getTransaction();
		
		try {
			
			
			// 트랜잭션 시작
			tx.begin();
			
			for (int i =0; i<=100; i++) {
				Employee9 employee = new Employee9();
				//employee.setId(1L);
				employee.setName("홍길동" + i);
				// 영속성 관리를 위한 엔터티 등록
				em.persist(employee);
			}

			// 트랜잭션 종료
			tx.commit();

			
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
			emf.close();
		}
	}

}
