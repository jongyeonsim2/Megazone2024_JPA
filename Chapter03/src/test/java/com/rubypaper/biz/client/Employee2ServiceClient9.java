package com.rubypaper.biz.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Employee2;

/*
 * p.196
 * 
 * merge() 메소드 이용해서 update sql 이 발생되는 실습
 * 
 * merge() 어떻게 생성 상태(insert)인지 분리 상태(update) 인지를
 * 어떻게 구분하느냐? ( 중요 )
 * 
 * 1. select 또는 insert 가 발생하는 경우
 *    merge() 에 전달되는 매개변수 entity 에 식별자 값의 유무
 *    
 *    - 식별자가 없는 경우
 *      insert 가 발생.
 *    - 식별자가 있는 경우
 *      select 발생 후 insert 가발생
 *      
 * 2. update 가 발생하는 경우 
 *    merge() 에 전달되는 매개변수 entity 가 DB에 존재하고
 *    엔터티가 수정이 된 경우
 * 
 * 
 * - 실습을 위한 환경 설정
 *   영속 메타정보를 수정
 *   
 *   hibernate.hbm2ddl.auto 를 create 에서 update 로 수정
 * 
 */

public class Employee2ServiceClient9 {

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
			// 기존에 있는 데이터를 기준으로 식별자를 지정.
			employee.setId(1L);
			employee.setName("이름수정");
			
			tx.begin();
			// employee 엔터티는 생성상태
			/*
			 * 1. 1차 캐시에서 검색( 식별자가 있기 때문 )
			 * 2. DB에 식별자 값으로 검색 => select 작성 및 전송
			 * 3. DB에 존재하는 엔터티임을 확인
			 * 4. DB에 수정(update 작성 및 전송)
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
