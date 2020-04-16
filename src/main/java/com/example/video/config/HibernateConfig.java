package com.example.video.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class HibernateConfig {
    @Autowired
      private DataSource dataSource;    // It will automatically read database properties from application.properties and create DataSource object

	@Bean(name = "sessionFactory")
	public LocalSessionFactoryBean getSessionFactory() { // creating session factory
	
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setPackagesToScan("com.example.video");
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
		
	}

	private Properties hibernateProperties() { // configure hibernate properties
		
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.format_sql", "true");
		return properties;
	}
}