package com.rubypaper.biz.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Department;
import com.rubypaper.biz.domain.Employee;

/*
 * 양방향 통신하는 테스트 프로그램
 */

public class ManyToOneBothWayClient {
	
	// 엔터티를 영속성 컨테이너에 등록
	private static void dataInsert(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		
		// 부서등록
		Department dept = new Department();
		dept.setName("기구");
		/*
		 * 기존에는 양방향 관계를 유지 하기 위해서
		 * 영속성 컨테이너에 등록하는 것을 별개고
		 * Department.empList 에 사원정보를 등록해야함.
		 * 
		 * 그래서, 현재는 Department.empList 에
		 * 사원정보를 등록하지 않고 있어, 양방향관계가 단절된 상태임.
		 */
		//dept.getEmpList().add(emp1);
		//dept.getEmpList().add(emp1);
		
		em.persist(dept);
		
		// 사원등록
		Employee emp1 = new Employee();
		emp1.setName("박문수5");
		// 사원 entity에 부서를 초기화하는 것과 동시에
		// 부서 entity에 사원이 추가가 됨.
		// => 자동으로 양방향이 유지가 됨.
		emp1.setDept(dept);
		em.persist(emp1);
		
		Employee emp2 = new Employee();
		emp2.setName("이기대5");
		emp2.setDept(dept);
		em.persist(emp2);
		
		// 부서 엔터티에서 사원 정보 등록 기능을 부서 엔터티에서 구현하면 좋겠다.
		//dept.getEmpList().add(emp1);
		//dept.getEmpList().add(emp2);
		
		System.out.println(dept.getName() + "의 직원수 : " +
				dept.getEmpList().size());

		tx.commit();
	}
	
	// 엔터티 조회
	private static void dataSelect(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		
		Department dept = em.find(Department.class, 1L);
		
		// 부서정보
		System.out.println("부서명 : " + dept.getName());
		
		// 사원정보
//		System.out.println("====== 사원 정보 List ======");
//		for (Employee emp : dept.getEmpList()) {
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
