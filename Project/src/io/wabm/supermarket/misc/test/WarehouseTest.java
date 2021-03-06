package io.wabm.supermarket.misc.test;

/**
 * Created by MainasuK on 2016-11-27.
 */

import de.saxsys.javafx.test.JfxRunner;
import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.model.warehouse.CommodityInformationModel;
import javafx.scene.control.TableView;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertNotNull;

/**
 * Created by MainasuK on 2016-11-21.
 */
@RunWith(JfxRunner.class)
@ContextConfiguration(classes = {DBConfig.class})
public class WarehouseTest {

    static BasicDataSource dataSource;
    static JdbcTemplate template;

    @BeforeClass
    public static void setupDataSource() {
        dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://localhost/WABM?serverTimezone=CST");    // Set timezone to China Standard Time
        dataSource.setUsername("root");
        dataSource.setPassword("");
        dataSource.setInitialSize(5);
        dataSource.setMaxTotal(10);

        template = new JdbcTemplate(dataSource);
    }

    @Test
    public void testDataSource() {
        assertNotNull(dataSource);
    }

    @Test
    public void testJdbcTemplate() {
        assertNotNull(template);
    }

    @Test
    public void testAddCommodity() throws Exception {
        TableView<Commodity> tableView = new TableView<>();
        CommodityInformationModel<Commodity> model = new CommodityInformationModel<>(tableView);

        CompletableFuture future = new CompletableFuture<>();

        model.fetchData(0, (isSuccess) -> {
            System.out.println("Fetch is: " + isSuccess);

            future.complete(isSuccess);
            return null;
        });

        Assert.assertEquals(true, (Boolean) future.get());

        List<Commodity> list = tableView.getItems();
        System.out.println(list.size());

        for (Commodity item : list) {
            System.out.println(item.getName());
        }
    }

    @Test
    public void testDeleteCommodity() throws DataAccessException, Exception {
        TableView<Commodity> tableView = new TableView<>();
        CommodityInformationModel<Commodity> model = new CommodityInformationModel<>(tableView);

        CompletableFuture future = new CompletableFuture<>();

        BigDecimal price = new BigDecimal(14.50);
        price.setScale(2);

        Commodity commodity = new Commodity("WABM00000001", 0, "6934665087752", "蒙牛冠益乳原味", "450g", "瓶", price, 20, 21, 10, true);
        model.delete(commodity, (exception) -> {

            future.complete(exception);
            return null;
        });

        Assert.assertEquals(null, (DataAccessException) future.get());

        List<Commodity> list = tableView.getItems();
        System.out.println(list.size());

        for (Commodity item : list) {
            System.out.println(item.getName());
        }

    }

}