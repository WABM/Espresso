package io.wabm.supermarket.misc.test;

import static org.junit.Assert.*;

import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.util.ConsoleLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by MainasuK on 2016-11-21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DBConfig.class})
public class DBTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate template;


    @Test
    public void testDataSource() {
        assertNotNull(dataSource);
    }

    @Test
    public void testJdbcTemplate() {
        assertNotNull(template);
    }


    @Test
    public void tsetSelect() {
        List<Classification> list;

        list = template.query("SELECT f.* FROM wabm.classification f",
                (resultSet, i) -> new Classification(
                        resultSet.getInt("classification_id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("profit_db"),
                        resultSet.getDouble("tax_rate_db")
                )
        );

        for (Classification item : list) {
            System.out.println(item.getName());
        }
    }

}