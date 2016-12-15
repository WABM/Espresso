package io.wabm.supermarket.model.warehouse;

import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.TableViewModel;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by MainasuK on 2016-11-14.
 */
public class CommodityClassificationInformationModel<T> extends TableViewModel<T> {

    private final String kSelectAll = "SELECT cl.*, COUNT(co.commodity_id) num FROM classification cl JOIN commodity co ON cl.classification_id = co.classification_id GROUP BY cl.classification_id;";

    public CommodityClassificationInformationModel(TableView<T> tableView) {
        super(tableView);

        ConsoleLog.print("CommodityClassificationInformationModel init");
    }

    public void fetchData(Callback<Boolean, Void> callback) {
        ConsoleLog.print("fetching data…");
        Assert.notNull(jdbcOperations);

        new WABMThread().run((_void) -> {
                List<Classification> templist = jdbcOperations.query(kSelectAll,
                        (ResultSet resultSet, int i) -> {
                            Classification classification = new Classification(
                                    resultSet.getInt("classification_id"),
                                    resultSet.getString("name"),
                                    resultSet.getDouble("profit_db"),
                                    resultSet.getDouble("tax_rate_db")
                            );

                            classification.setHasNum(resultSet.getInt("num"));

                            return classification;
                        }
                );

            list.clear();
            list.addAll((T[]) templist.toArray());

            // Return callback with isfetchSuccess flag;
            // TODO:
            callback.call(true);
            return null;
        });

    };  // end of fetchData

}
