package com.rubypaper.biz.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Data;

/*
 * 식별자
 * 
 * JPA 가 관리하는 엔터티는 @Id로 지정한 식별자 변수를 통해서 식별되었음.
 * 테이블의 기본키와 엔터티의 식별자 변수를 매핑해서 유일한 엔터티 객체를 식별하고
 * 관리.
 * 
 * 식별자 생성 전략( 중요 )
 * p.118 참고
 * 
 * Sequence 사용한 실습
 * 
 */

@Data
@Entity
@Table(name = "Employee10")
@TableGenerator(name = "SEQ_GENERATOR", //generatro 이름
				table = "SHOPPING_SEQUENCE", //table 명
				pkColumnName = "SEQ_NAME", //column 명
				pkColumnValue = "EMP_SEQ", // SEQ_NAME 칼럼의 값
				valueColumnName = "NEXT_VALUE",  //column 명
				initialValue = 0,
				allocationSize = 1)
public class Employee10 {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE,
				generator = "SEQ_GENERATOR")
	private Long id;
	
	private String name;
}
