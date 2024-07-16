package com.rubypaper.biz.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Employee10;

public class Employee10ServiceClient {
	
	public static void main(String[] args) {
		// 엔터티 매니저 팩토리 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Chapter02");
		
		// 엔터티 매니저 생성
		EntityManager em = emf.createEntityManager();
		
		// 엔터티 트랜잭션 생성
		EntityTransaction tx = em.getTransaction();
		
		try {
			// 직원 엔터티 생성

			Employee10 employee = new Employee10();
			employee.setName("홍길동");

			//트랜잭션 시작
			tx.begin();
			System.out.println("등록 전 id : " + employee.getId());
			
			// 직원 등록 처리
			em.persist(employee);
			
			System.out.println("등록 후 id : " + employee.getId());
			
			//트랜잭션 종료
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