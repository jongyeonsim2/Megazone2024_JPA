package com.rubypaper.biz.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Employee1;

/*
 * 엔터티 조회 : 영속성 컨테이너에 존재하지 않는 엔터티를 조회하는 실습
 */

public class Employee1ServiceClient2 {

	public static void main(String[] args) {
		EntityManagerFactory emf = 
				Persistence.createEntityManagerFactory("Chapter03");
		
		EntityManager em = emf.createEntityManager();
		
		// 엔터티 트랜잭션 생성
		EntityTransaction tx = em.getTransaction();
		
		try {
			
			/*
			 * 영속성 컨테이너에 없는 엔터티를 조회
			 * 
			 * find() -> context 내에서 검색 -> select 문장 생성 및 전송 ->
			 * H2 DB -> context 에 엔터티 생성 및 등록 -> application 쪽에
			 * 조회된 entity 를 전송
			 * 
			 * 실습시 주의 사항
			 * persistence.xml 의 hibernate.hbm2ddl.auto 의 value 를
			 * update 로 해야 함.
			 * 
			 * 실습이 완료되면 다시 hibernate.hbm2ddl.auto 의 value 를
			 * create 로 해야 함.
			 */
			
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
