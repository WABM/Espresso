package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.javafx.alert.SimpleSuccessAlert;
import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.warehouse.CommodityInformationModel;
import io.wabm.supermarket.protocol.CallbackAcceptableProtocol;
import io.wabm.supermarket.protocol.StageSetableController;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by MainasuK on 2016-11-16.
 */
public class CommodityInformationController extends SceneController {

    private CommodityInformationModel<Commodity> model;
    private boolean isSearching = false;

    @FXML TableView<Commodity> tableView;
    @FXML TableColumn<Commodity, String> idColumn;
    @FXML TableColumn<Commodity, String> barCodeColumn;
    @FXML TableColumn<Commodity, String> nameColumn;
    @FXML TableColumn<Commodity, String> classificationColumn;     // FIXME: classification should be String type
    @FXML TableColumn<Commodity, String> specificationColumn;
    @FXML TableColumn<Commodity, String> unitColumn;
    @FXML TableColumn<Commodity, BigDecimal> priceColumn;
    @FXML TableColumn<Commodity, Integer> deliverySpecificationColumn;

    @FXML Button backButton;
    @FXML Button addButton;
    @FXML Button removeButton;
    @FXML Button modifyButton;
    @FXML Button searchButton;

    @FXML private void backButtonPressed() {
        ConsoleLog.print("button pressed");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewPathHelper.class.getResource("warehouse/CommodityClassificationInformationManagementView.fxml"));

        navigationTo(loader);
    }

    @FXML private void addButtonPressed() {
        ConsoleLog.print("button pressed");

        try {
            // Load view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("warehouse/AddCommodityView.fxml"));
            AnchorPane pane = loader.load();

            // Create the popup Stage.
            Stage stage = new Stage();
            stage.setTitle("添加商品");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(pane);
            stage.setScene(scene);

            // Pass the info into the controller.
            StageSetableController controller = loader.getController();
            controller.setStage(stage);
            ((AddCommodityController) controller).fetchClassificationWithDefault(model.getClassificationID());
            ((CallbackAcceptableProtocol<Commodity, DataAccessException>) controller).set((commodity) -> {
                ConsoleLog.print("Add commodity callback called");
                final DataAccessException[] e = {null};

                model.add(commodity, (exception) -> {
                    e[0] = exception;
                    if (null != exception) {
                        exception.printStackTrace();
                    } else {
                        ConsoleLog.print("Insert commodity success");
                    }

                    return null;
                });

                return e[0];
            });

            // Show the dialog and wait until the user closes it.
            // (This event thread is blocked until close)
            stage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void removeButtonPressed() {
        ConsoleLog.print("button pressed");

        Commodity commodity = tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("移除商品");
        alert.setHeaderText("移除商品会导致商品被标记为失效状态，数据仍会被保留，但仅供查看而无法修改和使用");
        alert.setContentText("是否移除 " + commodity.getName());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ConsoleLog.print("Remove " + commodity.getName() + " …");

            model.remove(commodity, exception -> {
                if (null == exception) {
                    tableView.refresh();
                    new SimpleSuccessAlert("移除成功", "", commodity.getName() + " 移除成功").show();
                } else {
                    new SimpleErrorAlert("移除失败", "更新数据遇到了错误", "请重试").show();
                }
                return null;
            });
        } else {
            ConsoleLog.print("Remove canceled");
        }
    }

    @FXML private void modifyButtonPressed() {
        ConsoleLog.print("button pressed");

        try {
            // Load view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("warehouse/ModifyCommodityView.fxml"));
            AnchorPane pane = loader.load();

            // Create the popup Stage.
            Stage stage = new Stage();
            stage.setTitle("修改商品");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(pane);
            stage.setScene(scene);

            // Pass the info into the controller.
            StageSetableController controller = loader.getController();
            controller.setStage(stage);
            ((ModifyCommodityController) controller).fetchClassificationWithDefault(model.getClassificationID());
            ((ModifyCommodityController) controller).setCommodity(tableView.getSelectionModel().getSelectedItem());
            ((CallbackAcceptableProtocol<Commodity, DataAccessException>) controller).set((commodity) -> {
                ConsoleLog.print("modify commodity callback called");
                final DataAccessException[] e = {null};

                model.update(commodity, (exception) -> {
                    e[0] = exception;
                    if (null != exception) {
                        exception.printStackTrace();
                    } else {
                        ConsoleLog.print("update commodity success");
                    }

                    return null;
                });

                return e[0];
            });

            // Show the dialog and wait until the user closes it.
            // (This event thread is blocked until close)
            stage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void searchButtonPressed() {
        ConsoleLog.print("button pressed");

        if (isSearching) {
            isSearching = !isSearching;
            searchButton.setText("查询");
            model.setPredicate(commodity -> true);

            return ;
        }

        try {
            // Load view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("warehouse/FilterCommodityView.fxml"));
            AnchorPane pane = loader.load();

            // Create the popup Stage.
            Stage stage = new Stage();
            stage.setTitle("查询商品");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(pane);
            stage.setScene(scene);

            // Pass the info into the controller.
            StageSetableController controller = loader.getController();
            controller.setStage(stage);

            ((CallbackAcceptableProtocol<String[], Void>) controller).set((strings) -> {
                ConsoleLog.print("filter commodity callback called");

                // Check input first
                if (    (strings[0] == null || "".equals(strings[0])) &&
                        (strings[1] == null || "".equals(strings[1])) &&
                        (strings[2] == null || "".equals(strings[2]))) {
                    model.setPredicate(commodity -> true);
                    return null;
                }

                searchButton.setText("重置");
                isSearching = true;
                model.setPredicate(commodity -> {
                    boolean hasID, hasBarCode, hasName;

                    hasID = commodity.getCommodityID().contains(strings[0]);
                    hasBarCode = commodity.getBarcode().contains(strings[1]);
                    hasName = commodity.getName().contains(strings[2]);

                    // Debug
                    // ConsoleLog.print(hasID+ " " + hasBarCode + " " + hasName);
                    return  hasID && hasBarCode && hasName;
                });

                return null;
            });

            // Show the dialog and wait until the user closes it.
            // (This event thread is blocked until close)
            stage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML public void initialize() {
        ConsoleLog.print("CommodityInformationController init");

        setupModel();
        setupTableView();
        setupTableViewColumn();
        setupControl();
    }

    // MARK: Config method
    public void fetchWith(int classificationID) {

        this.model.fetchData(classificationID,
                (exception) -> {

            // TODO: handle exception
            return null;
        }
        );

    }

    // MARK: Setup method

    private void setupControl() {
        removeButton.setDisable(true);
        modifyButton.setDisable(true);
    }
    private void setupModel() {
        model = new CommodityInformationModel<>(tableView);
    }

    private void setupTableView() {
        tableView.setRowFactory(tableView -> new TableRow<Commodity>() {
            @Override
            protected void updateItem(Commodity item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty) {
                    setDisable(!item.isValid());
                    setEditable(item.isValid());
                }
            }
        });

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    removeButton.setDisable(newValue == null || !newValue.isValid());
                    modifyButton.setDisable(newValue == null || !newValue.isValid());
                }
        );
    }

    private void setupTableViewColumn() {
        // Setup cell value factory
        idColumn.setCellValueFactory(cellData -> cellData.getValue().commodityIDProperty());
        classificationColumn.setCellValueFactory(cellData -> cellData.getValue().classificationNameProperty());
        barCodeColumn.setCellValueFactory(cellData -> cellData.getValue().barcodeProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        specificationColumn.setCellValueFactory(cellData -> cellData.getValue().specificationProperty());
        unitColumn.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        deliverySpecificationColumn.setCellValueFactory(cellData -> cellData.getValue().deliverySpecificationProperty().asObject());
    }

}
