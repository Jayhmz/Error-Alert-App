package com.plantaccion.alartapp.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.plantaccion.alartapp.common.repository",
    entityManagerFactoryRef = "appEntityManagerFactory", transactionManagerRef = "appTransactionManager"
)
@EnableTransactionManagement
public class AppDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.app")
    public DataSourceProperties appDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean(name = "appDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.app.hikari")
    public DataSource appDataSource(){
        return appDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "appEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean appEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("appDatasource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.plantaccion.alartapp.common.model.app")
                .persistenceUnit("appPU")
                .build();
    }

    @Bean(name = "appTransactionManager")
    public JpaTransactionManager appTransactionManager(
            @Qualifier("appEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
