package com.rubypaper.biz.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// 현재 클래스가 스프링 설정 클래스임을 컨테이너에게 알려줌.
@Configuration
// 스프링 컨테이너가 생성할 객체의 클래스를 탐색하기 위한 기준 위치 정보
@ComponentScan(basePackages = "com.rubypaper.biz")
/*
 * business-layer.xml 의 트랜잭션 설정 대신에 스프링 설정 클래스에서
 * @EnableTransactionManagement 어노테이션을 통해서
 * @Transactional 을 활성화. => 어노테이션 기반의 트랜잭션 관리가 가능해짐.
 * 
 * 비즈니스 클래스에 @Transactional 어노테이션을 정의하면,
 * 비즈니스 메소드에 대한 트랜잭션 관리를 알아서 해줌.
 */
@EnableTransactionManagement

/*
 * 지정한 패키지 경로에서
 * Spring Data 의 Interface 를 상속받아 구현한 Interface 를 인식해서
 * 객제로 생성해 줌.
 */
@EnableJpaRepositories(basePackages = "com.rubypaper.biz.repository",
						entityManagerFactoryRef = "factoryBean",
						transactionManagerRef = "txManager")
public class SpringConfiguration {
	/*
	 * business-layer.xml 의 <bean> 설정과 동일함.
	 * 스프링 컨테이너는 @Bean 이 설정된 메소드를 호출한 후
	 * 반환된 객체를 스프링 컨테이너에서 관리함.
	 */
	@Bean
	public HibernateJpaVendorAdapter vendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		return adapter;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:tcp://localhost/./insa");
		dataSource.setUsername("insa");
		dataSource.setPassword("insa");
		return dataSource;
	}

	@Bean//(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean factoryBean() {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setJpaVendorAdapter(vendorAdapter());
		factoryBean.setDataSource(dataSource());

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");
		properties.put("hibernate.id.new_generator_mappings", "true");
		properties.put("hibernate.hbm2ddl.auto", "create");

		factoryBean.setJpaPropertyMap(properties);
		return factoryBean;
	}

	@Bean//(name = "transactionManager")
	public JpaTransactionManager txManager(EntityManagerFactory factory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(factory);
		return txManager;
	}
}
