package com.rubypaper.biz.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Employee12;
import com.rubypaper.biz.domain.Employee12Id;

public class Employee12ServiceClient {
	
	public static void main(String[] args) {
		// 엔터티 매니저 팩토리 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Chapter02");
		
		// 엔터티 매니저 생성
		EntityManager em = emf.createEntityManager();
		
		// 엔터티 트랜잭션 생성
		EntityTransaction tx = em.getTransaction();
		
		try {
			// 복합키 식별자 객체 생성
			Employee12Id empId = new Employee12Id(7L, "hong7");
			
			// 사원 객체 생성
			Employee12 employee = new Employee12();
			// 사원 객체의 식별자 멤버 초기화
			employee.setId(empId);
			employee.setName("홍길동");
			
			//트랜잭션 시작
			tx.begin();

			em.persist(employee);
			
			//트랜잭션 종료
			tx.commit();
			
			//등록 사원 정보 검색 : 식별자 클래스 활용
			Employee12Id searchEmpId = new Employee12Id(7L, "hong7");
			Employee12 findEmployee = em.find(Employee12.class, searchEmpId);
			
			System.out.println("검색된 사원 정보 : " + findEmployee.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
			emf.close();
		}
	}
}