package com.rubypaper.biz.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Employee1;
import com.rubypaper.biz.domain.Employee2;

/*
 * p.194
 * 
 * merge() 메소드 이용해서 persist() 처럼 동작이 되도록 실습
 * 
 * persist() => select 후 insert 가 발생.
 * 
 * merge() 어떻게 생성 상태(insert)인지 분리 상태(update) 인지를
 * 어떻게 구분하느냐? ( 중요 )
 * 
 */

public class Employee2ServiceClient8 {

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
			//식별자를 직접 설정
			employee.setId(1L);
			employee.setName("홍길동");
			
			tx.begin();
			// employee 엔터티는 생성상태
			/*
			 * 1. 1차 캐시에서 검색( 식별자가 있기 때문 )
			 * 2. DB에 식별자 값으로 검색 => select 작성 및 전송
			 * 3. DB에도 없는 엔터티임을 확인
			 * 4. DB에 등록(insert 작성 및 전송)
			 */
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
