package com.rubypaper.biz.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Employee1;

/*
 * 엔터티 분리 상태 -> 관리 상태 실습
 */

public class Employee1ServiceClient6 {

	static EntityManagerFactory emf = 
			Persistence.createEntityManagerFactory("Chapter03");
	
	// Entity 등록 후 준영속 상태로 변경
	static Employee1 createEmployee(String name) {
		System.out.println("============= createEmployee() start =============");
		// 첫 번째 영속성 컨테이너 시작
		EntityManager em1 = emf.createEntityManager();
		EntityTransaction tx1 = em1.getTransaction();
		
		// 트랜잭션 시작
		tx1.begin();
		
		// 엔터티 생성 및 등록
		Employee1 employee = new Employee1();
		employee.setName("홍길동");
		
		em1.persist(employee);
		
		// 트랜잭션 종료
		tx1.commit();
		
		// 첫 번째 영속성 컨테이너 종료
		// 먼저 영속성 컨테이너에 있는 entity 를 분리 후 컨테이너 종료됨
		em1.close();
		
		System.out.println("============= createEmployee() end =============");
		
		// 준영속 상태의 entity 가 반환됨.
		return employee;
	}
	
	// 준영속 상태의 메소드를 영속상태로 변경
	static void mergeEmployee(Employee1 employee) {
		System.out.println("============= mergeEmployee() end =============");
		
		// 두 번째 영속성 컨테이너 시작
		EntityManager em2 = emf.createEntityManager();
		EntityTransaction tx2 = em2.getTransaction();
		
		// 병합 : 준영속 상태의 employee 엔터티를 영속 상태로 변경
		// main() 에서 mergeEmployee() 를 호출하기 전에
		// 준영속 상태의 entity가 변경이 발생함. => 수정한 내용을 DB 에 반영하고 싶다는 의미가 내포됨.
		// 따라서, 새로운 transaction 을 시작해야 함.
		tx2.begin();
		
		// 병합 
		// 병합이 정상적으로 완료되면, 최종 결과물인 영속 상태의 entity 가 반환됨.
		// 두 가지 상태의 entity 가 공존하게 됨.
		// - 준연속 상태의 entity : mergeEmployee() 의 매개변수
		// - 영속 상태의 entity : merge() 의 반환 결과물
		Employee1 mergeEmployee =  em2.merge(employee);
		
		// 트랜잭션 종료.
		// 변경사항을 sql로 만들어서 H2 DB 에 전송해야 함. 
		// 최종적으로 DB 에 변경사항이 반영됨.
		tx2.commit();
		
		// 두 가지 상태의 entity 의 상태를 확인 및 비교
		System.out.println("employee(준영속 entity) : " + employee.toString());
		
		System.out.println("mergeEmployee(영속 entiry) : " + mergeEmployee.toString());
		
		
		
		System.out.println("============= mergeEmployee() start =============");
	}
	
	public static void main(String[] args) {
		
		
		EntityManager em = emf.createEntityManager();
				
		try {
			
			/*
			 * 영속(persist) -> 준영속(영속성 컨테이너 close) -> 영속(merge)
			 * 
			 * 이렇게 진행되는 변화에 대한 과정을 
			 * 반드시 콘솔 로그로 보고 이해를 해야함.
			 * 
			 */
			
			// 반환되는 entity 는 준영속 상태의  Entity
			Employee1 employee = createEmployee("홍길동");
			
			// 준영속 상태에서 변경이 발생
			employee.setName("이름수정");
			
			// 준영속 상태의 entity 를 영속상태로 변경.
			mergeEmployee(employee);

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
	}

}
