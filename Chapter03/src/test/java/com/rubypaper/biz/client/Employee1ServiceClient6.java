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
		
		return null;
	}
	
	// 준영속 상태의 메소드를 영속상태로 변경
	static void mergeEmployee(Employee1 employee) {
		
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
