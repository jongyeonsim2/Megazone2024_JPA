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
			dataInsert(emf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			emf.close();
		}
		
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
