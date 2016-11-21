package io.wabm.supermarket.misc.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by MainasuK on 2016-11-21.
 */
@Configuration
public class DBConfig {

    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/Espresso?serverTimezone=CST");    // Set timezone to China Standard Time
//        dataSource.setUrl("jdbc:mysql://192.168.1.110:3306/Espresso?serverTimezone=CST");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        dataSource.setInitialSize(5);
        dataSource.setMaxTotal(10);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

}
