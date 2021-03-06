package io.wabm.supermarket.model.procurement;

import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.misc.pojo.OrderDetail;
import io.wabm.supermarket.misc.pojo.Supplier;
import io.wabm.supermarket.misc.pojo.SupplyGoods;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.TableViewModel;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by 14580 on 2016/12/7 0007.
 */
@Repository
@ContextConfiguration(classes = DBConfig.class)
public class OrderDetailModel<T> extends TableViewModel<T> {

    private final String kSelectAll ="select order_detail.order_id, commodity.name,order_detail.quantity,order_detail.price_db, order_detail.production_date FROM order_detail ,commodity WHERE order_detail.commodity_id=commodity.commodity_id and order_id = ?";
    private final String  kDeleteSQLWithID = "DELETE  from order_detail where order_id = ? ";
    private final String kOrderDetailInsert = "";
    private int orderID;

    public OrderDetailModel(TableView tableView){

        super(tableView);
    }

    public void CreateOrderDetail(String SupplierID, String CommodityID,Callback<Boolean, Void> callback) {
        ConsoleLog.print("CreateOrderDetailSUPPLIERID" + SupplierID );
        ConsoleLog.print("CreateOrderDetailSUPPLIERID" + CommodityID );
        Assert.notNull(jdbcOperations);
        try {
            jdbcOperations.update(kOrderDetailInsert,
                    SupplierID,CommodityID
            );
        } catch (QueryTimeoutException timeoutException) {
            timeoutException.printStackTrace();
        } catch (DataAccessException dataAccessException) {
            dataAccessException.printStackTrace();
        }
        callback.call(true);
    }
    public  void fetchData(int orderID, Callback<Boolean, Void> callback) {
        ConsoleLog.print("fetching data with id: " + orderID+"…");
        this.orderID = orderID;
        Assert.notNull(jdbcOperations);

        new WABMThread().run((_void) -> {
            ConsoleLog.print("Start background task…");

            try {
                List<OrderDetail> templist = jdbcOperations.query(kSelectAll, (ResultSet resultSet, int i) -> {
                    OrderDetail orderDetail;
                    orderDetail = new OrderDetail(
                            resultSet.getInt("order_detail.order_id"),
                            resultSet.getString("commodity.name"),
                            resultSet.getInt("order_detail.quantity"),
                            resultSet.getDouble("order_detail.price_db"),
                            resultSet.getString("order_detail.production_date")
                    );
                    return orderDetail;
                }, orderID);

                list.clear();
                list.addAll((T[]) templist.toArray());

                callback.call(true);

            } catch (DataAccessException exception) {
                callback.call(false);
                exception.printStackTrace();
            }

            return null;
        });

    }

    public void delete(OrderDetail orderDetail, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("remove supplier: " + orderDetail.getOrderID());
        Assert.notNull(jdbcOperations);
        try {
            jdbcOperations.update(kDeleteSQLWithID, orderDetail.getOrderID());
            delete((T) orderDetail);
            callback.call(null);
        } catch (QueryTimeoutException timeoutException) {
            callback.call(timeoutException);
        } catch (DataAccessException dataAccessException) {
            callback.call(dataAccessException);
        }
    }

    public void update(OrderDetail orderDetail, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("modify orderDetail: " + orderDetail.getOrderID());
        Assert.notNull(jdbcOperations);

        try {
            jdbcOperations.update("UPDATE order_detail SET quantity=? WHERE order_id=?",
                    orderDetail.getQuantity(),
                    orderDetail.getOrderID()
            );
            callback.call(null);
        } catch (DataAccessException exception) {
            callback.call(exception);
        }

    }
}
