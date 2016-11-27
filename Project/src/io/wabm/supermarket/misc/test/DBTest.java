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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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

    @Test
    public void testCommodityModel() {
        List<Commodity> list;

        list = template.query("SELECT co.*, cl.name classification_name FROM commodity co JOIN classification cl ON co.classification_id=cl.classification_id WHERE co.classification_id = ?",
                (resultSet, i) -> new Commodity(
                    resultSet.getString("commodity_id"),
                    resultSet.getInt("classification_id"),
                    resultSet.getString("bar_code"),
                    resultSet.getString("name"),
                    resultSet.getString("specification"),
                    resultSet.getString("unit"),
                    resultSet.getDouble("price_db"),
                    resultSet.getInt("delivery_specification"),
                    resultSet.getInt("shelf_life"),
                    resultSet.getInt("start_storage")
                ),
                0
        );


        for (Commodity item : list) {
            System.out.println(item.getName());
        }

//        TableView<Commodity> tableView = new TableView<>();
//        CommodityInformationModel<Commodity> model = new CommodityInformationModel<>(tableView).k;
//
//        Assert.notNull(model);
//
//        model.fetchData(0, (isSuccess) -> {
//            ConsoleLog.print("Fetch is: " + isSuccess);
//            return null;
//        });
    }

}