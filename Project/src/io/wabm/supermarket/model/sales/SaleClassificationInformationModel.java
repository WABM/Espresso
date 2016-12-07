package io.wabm.supermarket.model.sales;

import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.warehouse.CommodityClassificationInformationModel;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Administrator on 2016/12/7 0007.
 */
public class SaleClassificationInformationModel<T> extends CommodityClassificationInformationModel<T>{
    String KSelectone = "select * from classification where name = ?";
    public SaleClassificationInformationModel(TableView<T> tableView){
        super(tableView);
    }
    public void Choose(String name,Callback<Boolean, Void> callback) {
        ConsoleLog.print("fetching data…");
        Assert.notNull(jdbcOperations);

        new WABMThread().run((_void) -> {
            try {
                List<Classification> templist = jdbcOperations.query(KSelectone,
                        (ResultSet resultSet, int i) -> {
                            Classification classification = new Classification(
                                    resultSet.getInt("classification_id"),
                                    resultSet.getString("name"),
                                    resultSet.getDouble("profit_db"),
                                    resultSet.getDouble("tax_rate_db")
                            );
                            return classification;
                        }
                        , name);

                list.clear();
                list.addAll((T[]) templist.toArray());
            }catch (DataAccessException e){
                e.printStackTrace();
            }

            // Return callback with isfetchSuccess flag;
            // TODO:
            callback.call(true);
            return null;
        });

    };  // end of fetchData
}
