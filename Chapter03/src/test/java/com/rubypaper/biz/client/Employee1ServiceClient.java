package com.rubypaper.biz.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Employee1;

/*
 * 
 */

public class Employee1ServiceClient {

	public static void main(String[] args) {
		EntityManagerFactory emf = 
				Persistence.createEntityManagerFactory("Chapter03");
		
		EntityManager em = emf.createEntityManager();
		
		// 엔터티 트랜잭션 생성
		EntityTransaction tx = em.getTransaction();
		
		try {
			// Entity 만 생성된 상태
			// 영속성 컨테이너에 등록이 되지 않은 상태
			Employee1 employee = new Employee1();
			//employee.setId(1L);
			employee.setName("홍길동");
			
			
			
			// 영속성 컨테이너에 엔터티를 등록.
			// DB 에 저장. 트랜잭션 내에서 persist() 호출되어야 함.
					
			// 트랜잭션 시작
			tx.begin();
			// 영속성 관리를 위한 엔터티 등록
			em.persist(employee);	
			// 트랜잭션 종료
			tx.commit();
			
			// Entity 수정
			// 결과적으로 update 문장이 h2 db에 전송.
			/*
			 * Ditry Check(더티 체크)
			 * 영속성 컨테이너는 관리중인 엔터티의 변경이 되는 순간, 변경을
			 * 감지하여 데이터베이스에 update 문장을 전송.
			 * 
			 */
			tx.begin();
			employee.setName("이름수정1");
			// 트랜잭션 종료
			tx.commit();
			
			// DB 변경 사항을 반영하려면,
			// 트랜잭션 내부에서 수행을 해야함.
			employee.setName("이름수정2");
			
			
			//*********** 영속성 컨테이너 등록, Entity Manager 의 find() ****//
			Employee1 findEmp = em.find(Employee1.class, 1L);
			System.out.println(findEmp.toString());
			
			/*
			 * 1. find() 메소드를 사용한다고 해서 항상 DB에서 조회하는건 아님.
			 * 	  영속성 컨테이너에서 먼저 조회 후 없으면 DB 에서 조회.
			 * 2. 조회된 사원정보의 이름과 DB의 사원정보의 이름이 다름.
			 *   entity는 컨테이너에서 관리되고 있지만,
			 *   변경사항이 DB로 전송되지 않은 상태.
			 * 
			 */
			
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
			emf.close();
		}
	}

}
