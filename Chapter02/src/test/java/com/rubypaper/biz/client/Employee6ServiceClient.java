package com.rubypaper.biz.client;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Employee6;

public class Employee6ServiceClient {

	public static void main(String[] args) {
		// <persistence-unit name="Chapter02"> 의 설정 정보를 참조
		EntityManagerFactory emf = 
				Persistence.createEntityManagerFactory("Chapter02");
		
		EntityManager em = emf.createEntityManager();
		
		// 엔터티 트랜잭션 생성
		EntityTransaction tx = em.getTransaction();
		
		try {
			// 영속성 관리 엔터티 생성
			Employee6 employee = new Employee6();
			
			employee.setId(1L);
			employee.setName("홍길동");
			employee.setMailId("hong");
			employee.setStartDate(new Date());
			employee.setTitle("대리");
			employee.setDeptName("개발부");
			employee.setSalary(2500.00);
			
			// commisionPct의 허용 값 : 10, 12.5, 15, 17.5, 20 
			// Check constraint violation: "CONSTRAINT_4351
			employee.setCommisionPct(12.50);
			
			// 트랜잭션 시작
			tx.begin();
			
			// 영속성 관리를 위한 엔터티 등록
			em.persist(employee);
			
			// 트랜잭션 종료
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
	}

}
