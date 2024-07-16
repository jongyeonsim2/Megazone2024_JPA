package com.rubypaper.biz.client;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.rubypaper.biz.domain.Employee_Test;

/*
 * Chapter03 의 JPA 개발 및 테스트 환경 테스트 엔터티 클래스
 * Chapter02 에서 복사함.
 * 
 */

public class Employee_TestServiceClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// <persistence-unit name="Chapter03"> 의 설정 정보를 참조
		EntityManagerFactory emf = 
				Persistence.createEntityManagerFactory("Chapter03");
		
		EntityManager em = emf.createEntityManager();
		
		try {
			// 영속성 관리 엔터티 생성
			Employee_Test employee = new Employee_Test();
			
			employee.setId(1L);
			employee.setName("홍길동");
			employee.setMailId("hong");
			employee.setStartDate(new Date());
			employee.setTitle("대리");
			employee.setDeptName("개발부");
			employee.setSalary(2500.00);
			employee.setCommisionPct(12.50);
			
			// 영속성 관리를 위한 엔터티 등록
			em.persist(employee);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
			emf.close();
		}
	}

}