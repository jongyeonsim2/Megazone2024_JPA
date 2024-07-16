package com.rubypaper.biz.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Employee8;

/*
 * 본 테스트 케이스를 실행하면 오류가 발생함.
 * 
 * 현재 사용중이 H2 최신 버전에 버그가 있음.
 * 
 * - 오류 해결 방법
 *   1. h2 1.4.200 버전을 사용하면 오류가 발생하지 않음.
 *   2. persistence.xml 에서 jdbc url 정보를 수정
 *      2.1 변경 전
 *          jdbc:h2:tcp://localhost/./test
 *      2.2 변경 후 
 *          jdbc:h2:tcp://localhost/./test;MODE=MySQL
 *   3. strategy 변경
 *      3.1 변경 전
 *          GenerationType.IDENTITY
 *      3.2 변경 후
 *          GenerationType.SEQUENCE
 */

public class Employee8ServiceClient {

	public static void main(String[] args) {
		// <persistence-unit name="Chapter02"> 의 설정 정보를 참조
		EntityManagerFactory emf = 
				Persistence.createEntityManagerFactory("Chapter02");
		
		EntityManager em = emf.createEntityManager();
		
		// 엔터티 트랜잭션 생성
		EntityTransaction tx = em.getTransaction();
		
		try {
			Employee8 employee = new Employee8();
			//employee.setId(1L);
			employee.setName("홍길동");
			
			// 트랜잭션 시작
			tx.begin();
			// 영속성 관리를 위한 엔터티 등록
			em.persist(employee);
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
