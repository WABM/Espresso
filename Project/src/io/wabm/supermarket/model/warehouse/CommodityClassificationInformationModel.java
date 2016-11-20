package io.wabm.supermarket.model.warehouse;

import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.Model;
import io.wabm.supermarket.model.TableViewModel;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;

/**
 * Created by MainasuK on 2016-11-14.
 */
public class CommodityClassificationInformationModel<T> extends TableViewModel<T> {

    public CommodityClassificationInformationModel(TableView<T> tableView) {
        super(tableView);

        ConsoleLog.print("CommodityClassificationInformationModel init");
    }

}
