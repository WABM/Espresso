package io.wabm.supermarket.misc.test;

/**
 * Created by MainasuK on 2016-11-27.
 */

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;
import de.saxsys.javafx.test.service.ServiceWrapper;
import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.util.WABMService;
import io.wabm.supermarket.model.warehouse.CommodityInformationModel;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TableView;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;
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
    public void testWithoutFXThread() throws Exception {
        TableView<Commodity> tableView = new TableView<>();
        CommodityInformationModel<Commodity> model = new CommodityInformationModel<>(tableView);

        CompletableFuture isInApplicationThread = new CompletableFuture<>();

        model.fetchData(0, (isSuccess) -> {
            System.out.println("Fetch is: " + isSuccess);

            isInApplicationThread.complete(isSuccess);
            return null;
        });

        Assert.assertEquals(true, (Boolean)isInApplicationThread.get());

        List<Commodity> list = tableView.getItems();
        System.out.println(list.size());

        for (Commodity item : list) {
            System.out.println(item.getName());
        }
    }

}