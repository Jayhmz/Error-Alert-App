package com.plantaccion.alartapp.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public class AuthenticationConfig  {

    private String tableName = "app_users";

    public AuthenticationConfig() {
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


}


