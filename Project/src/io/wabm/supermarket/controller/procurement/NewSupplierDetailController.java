package io.wabm.supermarket.controller.procurement;


import io.wabm.supermarket.misc.pojo.CommodityPriceInformation;
import io.wabm.supermarket.misc.pojo.Supplier;
import io.wabm.supermarket.misc.pojo.SupplyGoods;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.procurement.SupplyGoodsModel;
import io.wabm.supermarket.model.sales.CommodityPriceInformationModel;
import io.wabm.supermarket.protocol.StageSetableController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


/**
 * Created by 14580 on 2016/12/11 0011.
 */
public class NewSupplierDetailController implements StageSetableController {

    private CommodityPriceInformationModel<CommodityPriceInformation> model;
    private SupplyGoodsModel<SupplyGoods> supplyGoodsModel;
    private Supplier supplier;
    private TableView supplyTableView;

    @FXML Stage stage;
    @FXML TableView<CommodityPriceInformation> tableView;
    @FXML TableColumn<CommodityPriceInformation,String> commodityID;
    @FXML TableColumn<CommodityPriceInformation,String> barcode;
    @FXML TableColumn<CommodityPriceInformation,String> commodityName;
    @FXML TableColumn<CommodityPriceInformation,String> className;
    @FXML TableColumn<CommodityPriceInformation,String> specification;
    @FXML TableColumn<CommodityPriceInformation,String> unit;

    @FXML Button comfirmButton;
    @FXML Button cancelButton;

    @Override public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML public void initialize(){
        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();
    }

    private void setupControl(){
        comfirmButton.setDisable(true);
    }

    private void setupTableView() {
        tableView.setEditable(true);
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> comfirmButton.setDisable(newValue == null)
        );
    }

    private void setupModel(){
        model = new CommodityPriceInformationModel<>(tableView);
        model.fetchData(isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });
    }

    private void setupTableViewColumn(){
        commodityID.setCellValueFactory(new PropertyValueFactory<CommodityPriceInformation, String>("commodityID"));
        barcode.setCellValueFactory(new PropertyValueFactory<CommodityPriceInformation, String>("barcode"));
        commodityName.setCellValueFactory(new PropertyValueFactory<CommodityPriceInformation, String>("commodityName"));
        className.setCellValueFactory(new PropertyValueFactory<CommodityPriceInformation, String>("className"));
        specification.setCellValueFactory(new PropertyValueFactory<CommodityPriceInformation, String>("specification"));
        unit.setCellValueFactory(new PropertyValueFactory<CommodityPriceInformation, String>("unit"));
    }

    public void setSupplier(Supplier supplier){
        this.supplier = supplier;
    }

    public void setTableView(TableView tableView){

        this.supplyTableView = tableView;
    }

    @FXML private void setComfirmButtonPressed() {
        CommodityPriceInformation commodityPriceInformation = tableView.getSelectionModel().getSelectedItem();
        System.out.println(supplier.getSupplierID());

        supplyGoodsModel = new SupplyGoodsModel<SupplyGoods>(supplyTableView);

        supplyGoodsModel.add(String.valueOf(supplier.getSupplierID()),commodityPriceInformation.getCommodityID(),
                isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });
        supplyGoodsModel.fetchData(supplier.getSupplierID(),isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });
        stage.close();
    }

    @FXML private void setCancelButtonPressed () {
        ConsoleLog.print("Button pressed");
        stage.close();
    }

}
