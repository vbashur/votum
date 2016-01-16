package com.vbashur.votum.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Configuration
//@EnableTransactionManagement
//@PropertySource({ "classpath:persistence-h2db.properties" })
//@ComponentScan({ "com.vbashur.votum" })
public class DBConfiguration {

//	@Autowired
//	private Environment env;
//
//	@Bean
//	public LocalSessionFactoryBean sessionFactory() {
//		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//		sessionFactory.setDataSource(getDataSource());
//		sessionFactory.setPackagesToScan(new String[] { "com.vbashur.votum.domain" });
//		
//		sessionFactory.setHibernateProperties(hibernateProperties());
//		return sessionFactory;
//	}
//
//	 @Bean(name = "dataSource", destroyMethod = "close")	 
//	 public DataSource getDataSource(){
//		BasicDataSource dataSource = createDataSource();
//		DatabasePopulatorUtils.execute(createDatabasePopulator(), dataSource);
//		return dataSource;
//	}
//
//	@Bean
//	@Autowired
//	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
//		HibernateTransactionManager txManager = new HibernateTransactionManager();
//		txManager.setSessionFactory(sessionFactory);
//		return txManager;
//	}
//
//	@Bean
//	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
//		return new PersistenceExceptionTranslationPostProcessor();
//	}
//
//	@SuppressWarnings("serial")
//	Properties hibernateProperties() {
//		return new Properties() {
//			{
//				setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//				setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
//				setProperty("hibernate.globally_quoted_identifiers", "true");
//			}
//		};
//	}
//	
//	private BasicDataSource createDataSource() {
//		BasicDataSource dataSource = new BasicDataSource();
//		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
//		dataSource.setUrl(env.getProperty("jdbc.url"));
//		dataSource.setUsername(env.getProperty("jdbc.user"));
//		dataSource.setPassword(env.getProperty("jdbc.pass"));
//		return dataSource;
//	}
//	
//	private DatabasePopulator createDatabasePopulator() {
//        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
//        databasePopulator.setContinueOnError(true);
////        databasePopulator.addScript(new ClassPathResource("sql/TableCreate.sql"));
//        databasePopulator.addScript(new ClassPathResource("sql/DataInsert.sql"));        
//        return databasePopulator;
//    }
}
