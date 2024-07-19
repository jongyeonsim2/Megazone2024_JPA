package com.rubypaper.biz.client;

import java.util.List;

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
	
	
	private static void dataDeleteForNull(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		
		// 삭제할 부서 검색
		Department2 dept = em.find(Department2.class, 5L);
		
		// 사원 삭제
		List<Employee2> empList = dept.getEmpList();
		
		for (Employee2 emp : empList) {
			//emp.standby();
			emp.setDept(null);
		}
		
		em.remove(dept);
		
		tx.commit();
	}
	
	
	// 고아 객체 삭제 
	// orphanRemoval = true
	private static void dataDeleteOrphanRemoval(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		
		// 삭제할 부서 검색
		Department2 dept = em.find(Department2.class, 6L);
		
		// 사원 삭제
		List<Employee2> empList = dept.getEmpList();
		// 영속성 컨테이너 에서 부서와 직원의 연관관계를 제거
		/*
		 * === 연관 관계 제거 ====
		 * 제거한 위치 : 영속성 컨테이너
		 *            에서 부서와 직원의 연관관계를 제거 => empList.clear();
		 * 차이점이 발생 : Table 에는 아직 해당 부서에 속한 사원 정보가 존재함.
		 * 
		 * 그래서, 부서 엔터티와 DB의 table 의 데이터가 불일치함.
		 * table에서 해당 부서 속한 사원정보들을 영속성 컨테이너가 자동을 삭제.
		 * 부서 엔터티에 사원 멤버에 orphanRemoval = true 설정되어 있기 때문임.
		 * => 고아 제거 속성
		 * 
		 */
		empList.clear();
		
		tx.commit();
	}
	
	// 영속성 전이를 활용한 엔터티 삭제
	private static void dataDelete(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();

		/*
		 * Department2 에 empList 멤버에 영속성 전이 옵션이 설정됨.
		 * CascadeType.REMOVE
		 * 
		 * 부서가 삭제가 되었을 때,
		 * 해당 부서에 소속된 사원정보도 함께 삭제.
		 */
		
		// 삭제할 부서의 entity 검색
		Department2 dept = em.find(Department2.class, 7L);
//		
//		// 해당 부서에 속한 사원 정보 삭제
//		List<Employee2> empList = dept.getEmpList();
//		for(Employee2 emp : empList) {
//			em.remove(emp);
//		}
		
		em.remove(dept);
		
		tx.commit();
	}
	
	
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
		emp1.setName("박문수6");
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
			//dataInsert(emf);
			//dataDelete(emf);
			//dataDeleteOrphanRemoval(emf);
			dataDeleteForNull(emf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			emf.close();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	


}
