package io.wabm.supermarket.model.sales;

import io.wabm.supermarket.misc.pojo.CommodityPriceInformation;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.TableViewModel;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Administrator on 2016/12/11 0011.
 */
public class CommodityPriceInformationModel<T> extends TableViewModel<T>{
    private final String KSelectAll="select c.commodity_id,c.bar_code,c.name,cl.name,c.specification,c.unit,c.price_db from commodity c,classification cl where c.classification_id=cl.classification_id";
    private final String KSelectsome="select c.commodity_id,c.bar_code,c.name,cl.name,c.specification,c.unit,c.price_db from commodity c,classification cl where c.classification_id=cl.classification_id and cl.name = ?";
    private final String Kupdate="update commodity set price_db=? where commodity_id=?";

    public CommodityPriceInformationModel(TableView tableView){
        super(tableView);
    }

    public void fetchData(Callback<Boolean, Void> callback){
        ConsoleLog.print("fetching data…");

        new WABMThread().run((_void) -> {
            try {
                List<CommodityPriceInformation> templist = jdbcOperations.query(KSelectAll,
                        (ResultSet resultSet, int i) -> {
                            CommodityPriceInformation commodityPriceInformation = new CommodityPriceInformation(
                                    resultSet.getString("commodity_id"),
                                    resultSet.getString("bar_code"),
                                    resultSet.getString("c.name"),
                                    resultSet.getString("cl.name"),
                                    resultSet.getString("specification"),
                                    resultSet.getString("unit"),
                                    resultSet.getBigDecimal("price_db")
                            );
                            return commodityPriceInformation;
                        }
                );

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
    public void choose(String name,Callback<Boolean, Void> callback){
        new WABMThread().run((Void) -> {
            try {
                List<CommodityPriceInformation> templist = jdbcOperations.query(KSelectsome,
                        (ResultSet resultSet, int i) -> {
                            CommodityPriceInformation commodityPriceInformation = new CommodityPriceInformation(
                                    resultSet.getString("commodity_id"),
                                    resultSet.getString("bar_code"),
                                    resultSet.getString("c.name"),
                                    resultSet.getString("cl.name"),
                                    resultSet.getString("specification"),
                                    resultSet.getString("unit"),
                                    resultSet.getBigDecimal("price_db")
                            );
                            return commodityPriceInformation;
                        }
                ,name);

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
    }
    public void update(CommodityPriceInformation commodityPriceInformation,Callback<Boolean, Void> callback){
        ConsoleLog.print("update…");

        new WABMThread().run((Void)->{
            try {
                jdbcOperations.update(Kupdate,
                        commodityPriceInformation.getPrice(),
                        commodityPriceInformation.getCommodityID());
            }catch (DataAccessException e){
                e.printStackTrace();
            }
            callback.call(true);
            return null;
        });
    }
}
