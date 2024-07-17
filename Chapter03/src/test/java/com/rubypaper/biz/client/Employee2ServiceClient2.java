package com.rubypaper.biz.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Employee1;
import com.rubypaper.biz.domain.Employee2;

/*
 * 엔터티 조회 : 영속성 컨테이너에 존재하지 않는 엔터티를 조회하는 실습
 */

public class Employee2ServiceClient2 {

	public static void main(String[] args) {
		EntityManagerFactory emf = 
				Persistence.createEntityManagerFactory("Chapter03");
		
		EntityManager em = emf.createEntityManager();
		
		// 커밋할 때만 flush 가 동작하게 됨.
		em.setFlushMode(FlushModeType.COMMIT);
		
		// 엔터티 트랜잭션 생성
		EntityTransaction tx = em.getTransaction();
		
		try {
			// Entity 생성
			Employee2 employee = new Employee2();
			employee.setName("홍길동");
			
			// 트랜잭션 시작
			tx.begin();
			em.persist(employee);
			tx.commit(); //flush 동작, 묵시적 flush
			//em.flush(); // 명시적 flush 동작
			
			// 사원 검색
			Employee2 findEmp1 = em.find(Employee2.class, 1L);
			Employee2 findEmp2 = em.find(Employee2.class, 1L);
			
			// 엔터티에 대한 동일성 비교
			// ( 1차 캐시의 장점, 1차 캐시의 구조가 key, value 형태로 관리가 되고 있기 때문 )
			// find() 호출했지만, select 문장이 발생하지 않음.
			// => 1차 캐시를 사용하고 있음. => application 의 성능에 도움이 됨.
			if ( findEmp1 == findEmp2 ) {
				System.out.println("findEmp1 와 findEmp2 는 동일한 엔터티임.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
			emf.close();
		}
	}

}
