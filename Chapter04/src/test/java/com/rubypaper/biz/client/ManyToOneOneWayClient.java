package com.rubypaper.biz.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Department;
import com.rubypaper.biz.domain.Employee;

public class ManyToOneOneWayClient {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EntityManagerFactory emf = 
				Persistence.createEntityManagerFactory("Chapter04");

		try {
			// 부서 등록, 사원 등록
			//dataInsert(emf);
			dataSelect(emf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			emf.close();
		}
		
	}
	
	private static void dataSelect(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		Employee emp = em.find(Employee.class, 1L);
		System.out.println(emp.toString());
		
		/*
		 * 실행 결과는 left outer join 임.
		 * 
		 * 현재의 도메인은 사원관리임.
		 * - 신규 사원인 경우에 교육인 끝나기 전까지는 부서배치를 보유
		 *   => 해당 사원의 경우 부서정보가 없을 수 있음.
		 *   
		 * - 근무중에 심각한 문제를 일으킨 경우
		 *   => 부서에서 제외하고, 향후에 부서를 재배치.
		 *      그래서, 부서정보가 없음.
		 * 
		 * 따라서, 기본적으로 전체 사원정보가 조회되도록 하도록 하면,
		 * outer join 이 필요하게 됨.
		 * 
		 */
	}
	
	private static void dataInsert(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction ex = em.getTransaction();
		
		ex.begin();
		
		// 부서 객체 생성
		Department dept = new Department();
		dept.setName("개발부");
		em.persist(dept);//영속성 컨테이너에 엔터티 등록
		
		// 사원 객체 생성
		Employee emp1 = new Employee();
		emp1.setName("홍길동");
		emp1.setDept(dept);
		em.persist(emp1);
		
		Employee emp2 = new Employee();
		emp2.setName("김길동");
		emp2.setDept(dept);
		em.persist(emp2);
		
		ex.commit();
		em.close();
	}

}
