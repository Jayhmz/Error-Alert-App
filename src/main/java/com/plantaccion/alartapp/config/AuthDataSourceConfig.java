package com.plantaccion.alartapp.config;

import com.plantaccion.alartapp.common.repository.auth.AuthenticationRepository;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = AuthenticationRepository.class,
        entityManagerFactoryRef = "authenticationEntityManagerFactory",
        transactionManagerRef = "authenticationTransactionManager"
)
public class AuthDataSourceConfig extends PhysicalNamingStrategyStandardImpl {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.auth")
    public DataSourceProperties authenticationDatasourceProperties(){
        return new DataSourceProperties();
    }

    @Bean(name = "authenticationDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    @Primary
    public DataSource authenticationDatasource() {
        return authenticationDatasourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean(name = "authenticationEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean authenticationEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("authenticationDatasource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = builder
                .dataSource(dataSource)
                .packages("com.plantaccion.alartapp.common.model.auth")
                .persistenceUnit("authenticationPU")
                .build();
        return entityManagerFactoryBean;
    }

    @Bean(name = "authenticationTransactionManager")
    @Primary
    public JpaTransactionManager authenticationTransactionManager(
            @Qualifier("authenticationEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
