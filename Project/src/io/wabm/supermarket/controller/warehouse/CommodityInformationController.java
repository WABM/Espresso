package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.warehouse.CommodityInformationModel;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    @FXML TableColumn<Commodity, Integer> classificationColumn;     // FIXME: classification should be String type
    @FXML TableColumn<Commodity, String> specificationColumn;
    @FXML TableColumn<Commodity, String> unitColumn;
    @FXML TableColumn<Commodity, String> priceColumn;
    @FXML TableColumn<Commodity, String> deliverySpecificationColumn;
    @FXML TableColumn<Commodity, Timestamp> createTimestampColumn;



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

    @FXML public void initialize() {
        ConsoleLog.print("CommodityInformationController init");

        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();



        // Stub for develop
        for (int i = 0; i < 100; i += 4) {
            model.add(new Commodity(i + 0 + "", 0, "6902538006261", "脉动 青柠味", "1L", "瓶", 8.00, 12, 10 * 30, 10, new Timestamp(System.currentTimeMillis())));
            model.add(new Commodity(i + 1 + "", 0, "6902538006262", "脉动 苹果味", "1L", "瓶", 8.99, 12, 10 * 30, 10, new Timestamp(System.currentTimeMillis())));
            model.add(new Commodity(i + 2 + "", 0, "6902538006263", "脉动 香蕉味", "1L", "瓶", 8.999, 12, 10 * 30, 10, new Timestamp(System.currentTimeMillis())));
            model.add(new Commodity(i + 3 + "", 0, "6902538006264", "脉动 草莓味", "1L", "瓶", 8.975, 12, 10 * 30, 10, new Timestamp(System.currentTimeMillis())));
        }
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
        classificationColumn.setCellValueFactory(cellData -> cellData.getValue().classificationIDProperty().asObject());
        barCodeColumn.setCellValueFactory(cellData -> cellData.getValue().barcodeProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        specificationColumn.setCellValueFactory(cellData -> cellData.getValue().specificationProperty());
        unitColumn.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        createTimestampColumn.setCellValueFactory(cellData -> cellData.getValue().createTimestampProperty());
    }

}
