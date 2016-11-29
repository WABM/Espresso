package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.warehouse.CommodityInformationModel;
import io.wabm.supermarket.protocol.CallbackAcceptableProtocol;
import io.wabm.supermarket.protocol.StageSetableController;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by MainasuK on 2016-11-16.
 */
public class CommodityInformationController extends SceneController {

    private CommodityInformationModel<Commodity> model;

    @FXML TableView<Commodity> tableView;
    @FXML TableColumn<Commodity, String> idColumn;
    @FXML TableColumn<Commodity, String> barCodeColumn;
    @FXML TableColumn<Commodity, String> nameColumn;
    @FXML TableColumn<Commodity, String> classificationColumn;     // FIXME: classification should be String type
    @FXML TableColumn<Commodity, String> specificationColumn;
    @FXML TableColumn<Commodity, String> unitColumn;
    @FXML TableColumn<Commodity, String> priceColumn;
    @FXML TableColumn<Commodity, Integer> deliverySpecificationColumn;

    @FXML Button backButton;
    @FXML Button addButton;
    @FXML Button deleteButton;
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

    @FXML private void deleteButtonPressed() {
        ConsoleLog.print("button pressed");
    }

    @FXML public void initialize() {
        ConsoleLog.print("CommodityInformationController init");

        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();
    }

    // MARK: Config method
    public void fetchWith(int classificationID) {

        this.model.fetchData(classificationID,
                (isSuccess) -> {
                    ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
                    return null;
                }
        );

    }


    // MARK: Setup method

    private void setupControl() {

    }
    private void setupModel() {
        model = new CommodityInformationModel<>(tableView);
    }
    private void setupTableView() {

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
