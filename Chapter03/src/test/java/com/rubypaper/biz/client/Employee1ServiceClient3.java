package com.rubypaper.biz.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Employee1;

/*
 * 엔터티 분리 상태 실습
 */

public class Employee1ServiceClient3 {

	public static void main(String[] args) {
		EntityManagerFactory emf = 
				Persistence.createEntityManagerFactory("Chapter03");
		
		EntityManager em = emf.createEntityManager();
		
		// 엔터티 트랜잭션 생성
		EntityTransaction tx = em.getTransaction();
		
		try {
			// Entity 만 생성된 상태
			// 영속성 컨테이너에 등록이 되지 않은 상태
			Employee1 employee = new Employee1();
			//employee.setId(1L);
			employee.setName("홍길동");
			
			
			
			// 영속성 컨테이너에 엔터티를 등록.
			// DB 에 저장. 트랜잭션 내에서 persist() 호출되어야 함.
					
			// 트랜잭션 시작
			tx.begin();
			// 영속성 관리를 위한 엔터티 등록
			em.persist(employee);	
			// 트랜잭션 종료
			tx.commit();
			
			
			//***** 엔터티 분리 실습 시작 ******//
			if(em.contains(employee)) {
				System.out.println("영속성 컨테이너에 등록된 상태.");
			}
			
			// 엔터티 분리
			em.detach(employee);
			if(!em.contains(employee)) {
				System.out.println("영속성 컨테이너에서 분리된 상태.");
			}

			employee.setName("이름수정");
			
			// 분리 상태의 엔터티는 수정을 하더라도 반영이 안됨.
			
			// 분리 상태의 엔터티를 find() 를 사용하여 조회하면,
			// select 문장이 생성되어, DB 에서 조회가 됨.
			// 조회된 사원정보가 컨테이너에 엔터로 등록이 되었음을 의미함.
			Employee1 findEmp = em.find(Employee1.class, 1L);
			System.out.println(findEmp.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
			emf.close();
		}
	}

}
