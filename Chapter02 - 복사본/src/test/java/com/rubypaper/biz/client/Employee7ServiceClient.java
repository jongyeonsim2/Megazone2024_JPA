package com.rubypaper.biz.client;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Employee7;

public class Employee7ServiceClient {

	public static void main(String[] args) {
		// <persistence-unit name="Chapter02"> 의 설정 정보를 참조
		EntityManagerFactory emf = 
				Persistence.createEntityManagerFactory("Chapter02");
		
		EntityManager em = emf.createEntityManager();
		
		// 엔터티 트랜잭션 생성
		EntityTransaction tx = em.getTransaction();
		
		try {
			// 영속성 관리 엔터티 생성
			
			
			// lombok 의 @Data 를 삭제하여,
			// setter 를 사용할 수 없는 상태임으로, 에러가 발생.
			// 따라서, lombok에서 만들어주는 생성자를 사용해야 함.
			
			// searchCondition, serarchKeyword 는 
			// JPA @Transient 설정으로 등록시 searchCondition, serarchKeyword
			// 제외 되는 것으로 되어 있으나, 생성자는 lombok 가 만든 것으로
			// JPA 어노테이션과 lombok 의 어노테이션은 서로 관계가 없음.
			// 따라서, lombok 에서 생성해주 생성자에 맞게끔 
			// 멤버변수를 초기화해야함으로 searchCondition, serarchKeyword
			// 멤버의 초기화를 위해서 null 을 매개변수로 전달해야 함.
			Employee7 employee = 
					new Employee7(1L, "홍길동", "hong", new Date(), 
							"대리", "개발부", 2500.00, 12.50,
							null, null);
			
			/*
			Employee7 employee = new Employee7();
			
			employee.setId(1L);
			employee.setName("홍길동");
			employee.setMailId("hong");
			employee.setStartDate(new Date());
			employee.setTitle("대리");
			employee.setDeptName("개발부");
			employee.setSalary(2500.00);
			*/
			
			// commisionPct의 허용 값 : 10, 12.5, 15, 17.5, 20 
			// Check constraint violation: "CONSTRAINT_4351
			//employee.setCommisionPct(12.50);
			
			// 트랜잭션 시작
			tx.begin();
			
			// 영속성 관리를 위한 엔터티 등록
			em.persist(employee);
			
			// 트랜잭션 종료
			tx.commit();
			
			// 등록된 사원 조회
			Employee7 findEmployee = em.find(Employee7.class, 1L);
			System.out.println("검색한 사원 정보");
			System.out.println(findEmployee.toString());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
			emf.close();
		}
	}

}
