package com.rubypaper.biz.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Employee1;
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

public class Employee2ServiceClient6 {

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
			
			// 준영속 엔터티 수정 => DB 에 반영이 되지 않음을 확인.
//			tx.begin();
//			employee.setName("이름수정");
//			tx.commit();
			
			// merge() 이용해서 수정.
			tx.begin();
			employee.setName("이름수정");
			// 매개변수 : 준영속 상태의 엔터티
			// 반환결과 : 영속 상태의 엔터티
			Employee2 mergedEmp = em.merge(employee);
			
			/*
			 * 1. 준영속 상태의 entity 의 식별자를 이용해서
			 *    1차 캐시에서 확인 후 없으면, 식별자를 이용해서
			 *    DB 에서 조회 => select 전송됨.
			 *    
			 *    1차 캐시에 엔터티 등록
			 * 2. 준영속 상태의 엔터티를 1차 캐시에 반영.
			 *    스냅샷과 차이가 발생.
			 * 3. commit() -> 묵시적 flush 발생.
			 * 4. 스냅샷과의 차이를 DB 에 반영을 위해서
			 *    update sql 이 전송됨
			 */
			
			tx.commit();
			
			System.out.println("employee 의 상태 : " + em.contains(employee));
			System.out.println("mergedEmp 의 상태 : " + em.contains(mergedEmp));
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
			emf.close();
		}
	}

}
