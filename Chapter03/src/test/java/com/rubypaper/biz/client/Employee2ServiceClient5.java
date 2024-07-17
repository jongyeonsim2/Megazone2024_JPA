package com.rubypaper.biz.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Employee2;

/*
 * p.188
 * 분리상태에서의 엔터티 수정
 * 
 * - hibernate.hbm2ddl.auto 의 값을 update 에서 create 로 변경
 * 
 * - 엔터티 생성 및 등록
 * - 분리 : em.clear()
 * - 엔터티 수정
 * 
 */

public class Employee2ServiceClient5 {

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
			em.persist(employee);

			/*
			 * 묵시적 flush 실행 =>  insert 생성됨.
			 * 
			 * persistence.xml 의 테이블이 생성되는 옵션
			 * DB의 테이블에도 없는 데이터임.
			 * 영속성 컨테이너에 없는 엔터티임.
			 */
			tx.commit();
			
			// 엔터티 분리
			em.clear();
			
			// 준영속 엔터티 수정
			tx.begin();
			employee.setName("이름수정");
			tx.commit();
			
			// 분리된 entity 를 수정하려면 어떻게.....?
			// Employee2ServiceClient6 로 계속됨.
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
			emf.close();
		}
	}

}
