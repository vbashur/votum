package com.vbashur.votum.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.vbashur.votum.domain"})
@EnableJpaRepositories(basePackages = {"com.vbashur.votum.repository"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}
