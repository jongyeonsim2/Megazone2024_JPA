package com.rubypaper.biz.client;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*
 * 양방향 통신하는 테스트 프로그램
 */

public class ManyToOneBothWayClient {
	
	private static void dataSelect(EntityManagerFactory emf) {
		
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EntityManagerFactory emf = 
				Persistence.createEntityManagerFactory("Chapter04");

		try {

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			emf.close();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	


}
