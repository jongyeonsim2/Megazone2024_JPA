package com.rubypaper.biz.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Employee1;

/*
 * 엔터티 분리 상태 -> 관리 상태 실습
 */

public class Employee1ServiceClient5 {

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

			//***** 엔터티 분리 -> 관리 실습 시작 ******//
			em.merge(employee);
			
			if(em.contains(employee)) {
				System.out.println("영속성 컨테이너에 등록된 상태.");
			}
			
			// merge() 동작 방식
			/*
			 * 1차 캐시에서 해당 식별자가 있는 확인.
			 * 없으면, database 를 조회.
			 * 
			 * database에 값이 있으면, 객체 등록
			 * 
			 * entity 를 수정한 경우에 수정한 결과가 반영이 되지 않음.
			 * DB에서 조회된 내용으로 덮어쓰기가 됨.
			 * 
			 */
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
			emf.close();
		}
	}

}
