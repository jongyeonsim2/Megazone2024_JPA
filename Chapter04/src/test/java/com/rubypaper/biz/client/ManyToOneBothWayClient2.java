package com.rubypaper.biz.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Department2;
import com.rubypaper.biz.domain.Employee2;

/*
 * 양방향 통신에서 영속성 전이를 테스트 프로그램
 */

public class ManyToOneBothWayClient2 {
	
	// 엔터티를 영속성 컨테이너에 등록
	private static void dataInsert(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		
		// 부서등록 => 비영속임.( 객체로만 존재하는  상태 )
		Department2 dept = new Department2();
		dept.setName("기구2");
		//em.persist(dept);
		
		// 사원등록 => 영속 상태임.
		Employee2 emp1 = new Employee2();
		emp1.setName("박문수5");
		emp1.setDept(dept);
		em.persist(emp1);
		
		// 두 entity 가 상태가 다른데,
		// 양방향 관계설정으로 사원 entity에서 
		// 부서정보를 설정하려고 함.
		
		// 따라서, 정상적으로 사원이 등록이 되려면,
		// 부서 정보가 영속상태임을 확인 후 사원을 등록하면 됨.
		// 그래서, 매번 확인할 것이냐?
		
		// => 영속성 전이를 활용해서 해결.
		/*
		 * 영속성 전이를 활용할 수 있는 케이스 ( 사원, 부서 entity )
		 * - 사원이 영속상태가 아니지만, 부서 엔터티에서 사용할 수 있는 경우
		 * - 부서가 영속상태가 아니지만, 사원 엔터티에서 사용할 수 있는 경우.
		 * - 부서가 삭제가 되면, 관련 사원 엔터티도 삭제가 된는 경우.
		 * 
		 * 
		 */
		
		tx.commit();
	}
	
	// 엔터티 조회
	private static void dataSelect(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		
		Department2 dept = em.find(Department2.class, 1L);
		
		// 부서정보
		System.out.println("부서명 : " + dept.getName());
		
		// 사원정보
//		System.out.println("====== 사원 정보 List ======");
//		for (Employee2 emp : dept.getEmpList()) {
//			System.out.println(emp.getName());
//		}
		
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EntityManagerFactory emf = 
				Persistence.createEntityManagerFactory("Chapter04");

		try {
			//dataSelect(emf);
			dataInsert(emf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			emf.close();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	


}
