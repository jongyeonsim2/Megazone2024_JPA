package com.rubypaper.biz.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Employee2;

/*
 * 1차 캐시의 장점에 대한 실습
 * Employee2ServiceClient2 의 실습과 달리
 * select 문장이 발생되는 경우를 실습
 * 
 * 1. 기존의 table이 유지가 되도록 해야함.
 *    영속성 메타데이터를 수정.(persistence.xml 수정)
 *    
 *    hibernate.hbm2ddl.auto 값을 
 *    기존 create 에서 update로 변경해야 함.
 *    
 * 2. 기존 데이터를 검색
 *    - 영속성 컨테이너가 비어있는 상태임.
 *    - entity 를 검색.
 *    - 1차 캐시에 해당되는 entity 가 없는 상태임.
 *    - H2 로 가서 검색을 해야하기 때문에 select 가 작성되게 됨.
 */

public class Employee2ServiceClient3 {

	public static void main(String[] args) {
		EntityManagerFactory emf = 
				Persistence.createEntityManagerFactory("Chapter03");
		
		EntityManager em = emf.createEntityManager();
		
		// 커밋할 때만 flush 가 동작하게 됨.
		em.setFlushMode(FlushModeType.COMMIT);
		
		// 엔터티 트랜잭션 생성
		EntityTransaction tx = em.getTransaction();
		
		try {
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
