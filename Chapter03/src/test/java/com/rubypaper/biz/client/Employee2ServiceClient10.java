package com.rubypaper.biz.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Employee2;

/*
 * p.198
 * 
 * refresh() 메소드 이용한 엔터티 갱신 실습
 * 
 * 테이블의 변화를 entity에 반영.
 * 테이블의 변화는 내가 누군가가 table 의 데이터를 수정한 경우, 
 * application 에서 사용중인 entity 를 table 기준으로 갱신.
 * 
 * - 실습환경 수정
 *   hibernate.hbm2ddl.auto 를 update 에서 create  로 수정
 * 
 */

public class Employee2ServiceClient10 {

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
			tx.commit();
			
			// 일정 시간 대기
			// 직접 table의 데이터 수정. ( 제 3자가 데이터 수정하는 것을 가정 )
			for(int i =0; i<30; i++) {
				Thread.sleep(1000);//1초
				System.out.println("다른 사용자가 데이터 수정중....");
			}
			
			// 현재 사용중인 entity 를 db 기준으로 갱신
			em.refresh(employee);
			System.out.println("갱신된 사원 정보 : " + employee.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
			emf.close();
		}
	}

}
