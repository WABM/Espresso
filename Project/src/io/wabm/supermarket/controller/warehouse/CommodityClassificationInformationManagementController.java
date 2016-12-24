package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.javafx.tablecell.HyperlinkTableCell;
import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.warehouse.CommodityClassificationInformationModel;
import io.wabm.supermarket.protocol.CellFactorySetupCallbackProtocol;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by MainasuK on 2016-10-25.
 */
public class CommodityClassificationInformationManagementController extends SceneController {

    private CommodityClassificationInformationModel<Classification> model;

    @FXML private TableView<Classification> tableView;
    @FXML private TableColumn<Classification, String> nameColumn;
    @FXML private TableColumn<Classification, Integer> hasNumColumn;
    @FXML private TableColumn<Classification, Hyperlink> actionColumn;

    @FXML public void initialize() {
        ConsoleLog.print("CommodityClassificationInformationManagementController init");

        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();
    }

    // MARK: Setup method

    private void setupControl() {
    }

    private void setupModel() {
        model = new CommodityClassificationInformationModel<>(tableView);

        // TODO: loading info need add to view (Spinner or just Loading… (e.g. 加载中……)
        model.fetchData(isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });
    }

    private void setupTableView() {
        // Set editable on
        tableView.setEditable(true);
    }

    private void setupTableViewColumn() {

        // Setup cell factory
        actionColumn.setCellFactory(actionColumnSetupCallback);

        // Setup cell value factory
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        hasNumColumn.setCellValueFactory(cellData -> cellData.getValue().hasNumProperty().asObject());
        actionColumn.setCellValueFactory(cellData -> {

            return new SimpleObjectProperty<>(new Hyperlink("查看"));
        });

    }

    private CommodityInformationController commodityInformationController;

    private CellFactorySetupCallbackProtocol<Classification, Hyperlink> actionColumnSetupCallback = (column) -> new HyperlinkTableCell() {
        @Override
        protected void updateItem(Hyperlink item, boolean empty) {
            super.updateItem(item, empty);

            setAlignment(Pos.CENTER);

            // Check empty first
            if (!empty) {
                item.setOnAction(event -> {
                    Classification classification = (Classification) getTableRow().getItem();
                    ConsoleLog.print("" + classification.getName());

                    FXMLLoader loder = new FXMLLoader();
                    loder.setLocation(ViewPathHelper.class.getResource("warehouse/CommodityInformationManagementView.fxml"));

                    // Bind controller when you first call it.
                        navigationTo(loder, controller -> {

                        if (commodityInformationController == null) {
                            ConsoleLog.print("Bind controller success");
                            commodityInformationController = ((CommodityInformationController) controller);
                        }

                        return null;
                    });

                    // Then dispatch the task to controller you hold.
                    commodityInformationController.fetchWith(classification.getClassificationID());
                });
            }
        }
    };
}
