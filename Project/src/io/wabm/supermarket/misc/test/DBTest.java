package io.wabm.supermarket.misc.test;

import static org.junit.Assert.*;

import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.warehouse.CommodityInformationModel;
import javafx.scene.control.TableView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;

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

    @Autowired
    DataSourceTransactionManager transactionManager;


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

    @Test public void testRollback() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(definition);

        try {
            template.update("INSERT INTO wabm.classification VALUES (100, '测试', 0.1, 0.16)");

            transactionManager.rollback(status);    // Rollback for test

//            transactionManager.commit(status);      // Use commit in normal case

        } catch (DataAccessException e) {
            e.printStackTrace();
            transactionManager.rollback(status);
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
        }

        Classification classification = null;
        try {

            classification = template.queryForObject("SELECT f.* FROM wabm.classification f WHERE f.classification_id = 100",
                    (resultSet, i) -> new Classification(
                            resultSet.getInt("classification_id"),
                            resultSet.getString("name"),
                            resultSet.getDouble("profit_db"),
                            resultSet.getDouble("tax_rate_db")
                    )
            );
        } catch (EmptyResultDataAccessException empty) {
            ConsoleLog.print("Empty result");
        }

        Assert.isNull(classification);
    }

}