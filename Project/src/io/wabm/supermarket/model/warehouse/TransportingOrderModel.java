package io.wabm.supermarket.model.warehouse;

import io.wabm.supermarket.model.procurement.CommodityOrderModel;
import javafx.scene.control.TableView;

/**
 * Created by MainasuK on 2016-12-12.
 */
public class TransportingOrderModel<T> extends CommodityOrderModel<T> {

    protected String kSelectAll = "SELECT order.order_id, supplier.name, order.create_timestamp, order.status from wabm.order , wabm.supplier where order.supplier_id = supplier.supplier_id and order.status=2";

    public TransportingOrderModel(TableView<T> tableView) {
        super(tableView);
    }

}
