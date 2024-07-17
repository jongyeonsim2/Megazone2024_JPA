package com.rubypaper.biz.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Employee1;
import com.rubypaper.biz.domain.Employee2;

/*
 * p.193
 * 
 * merge() 메소드 이용해서 persist() 처럼 동작이 되도록 실습
 * 
 * persist() => insert 가 발생.
 * 
 * merge() 어떻게 생성 상태(insert)인지 분리 상태(update) 인지를
 * 어떻게 구분하느냐? ( 중요 )
 * 
 */

public class Employee2ServiceClient7 {

	public static void main(String[] args) {
		EntityManagerFactory emf = 
				Persistence.createEntityManagerFactory("Chapter03");
		
		EntityManager em = emf.createEntityManager();
		
		// 커밋할 때만 flush 가 동작하게 됨.
		em.setFlushMode(FlushModeType.COMMIT);
		
		// 엔터티 트랜잭션 생성
		EntityTransaction tx = em.getTransaction();
		
		try {
			Employee2 employee = new Employee2();
			employee.setName("홍길동");
			
			tx.begin();
			// employee 엔터티는 생성상태
			em.merge(employee);
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
