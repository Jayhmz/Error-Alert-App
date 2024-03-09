package com.plantaccion.alartapp.config;

import com.plantaccion.alartapp.common.repository.auth.AuthenticationRepository;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
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

    @Autowired
    private final Environment env;

    private String tableName;
    public AuthDataSourceConfig(Environment env) {
        this.env = env;
        this.tableName = env.getProperty("auth.entity.table");
    }

    @Override
    public Identifier toPhysicalTableName(final Identifier name, final JdbcEnvironment context) {
        System.out.println("-----------> " +tableName);
        if (name == null){
            System.out.println("inside the name is null thing");
            return null;
        }
        return new Identifier(tableName, name.isQuoted());
    }

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
//        var authenticationConfig = new AuthenticationConfig();
//        authenticationConfig.setTableName(env.getProperty("auth.entity.table"));
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = builder
                .dataSource(dataSource)
                .packages("com.plantaccion.alartapp.common.model.auth")
                .persistenceUnit("authenticationPU")
                .build();
//        entityManagerFactoryBean.setJpaProperties(hibernateProperties());
        return entityManagerFactoryBean;
    }

//    private Properties hibernateProperties() {
//        Properties properties = new Properties();
//        properties.put("hibernate.physical-strategy", "com.plantaccion.alartapp.config.AuthenticationConfig");
////        properties.put("auth.entity.table", env.getProperty("auth.entity.table"));
//        return properties;
//    }

    @Bean(name = "authenticationTransactionManager")
    @Primary
    public JpaTransactionManager authenticationTransactionManager(
            @Qualifier("authenticationEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
