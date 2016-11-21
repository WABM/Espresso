package io.wabm.supermarket.misc.test;

import static org.junit.Assert.*;

import io.wabm.supermarket.misc.config.DBConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

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


//    @Test
//    public void testAddSpitter() {
//        try {
//            repository.addSpitter(this.spitter);
//        } catch (DataAccessException e) {
//            assertNull(e);
//        }
//    }
//
//    @Test
//    public void testFindSpitter() {
//        try {
//            Spitter spitter = repository.findOne(1);
//            System.out.println(spitter);
//        } catch (DataAccessException e) {
//            assertNull(e);
//        }
//    }

}