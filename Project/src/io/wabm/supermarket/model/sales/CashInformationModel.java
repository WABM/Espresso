package io.wabm.supermarket.model.sales;

import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.javafx.alert.SimpleSuccessAlert;
import io.wabm.supermarket.misc.pojo.CashInformation;
import io.wabm.supermarket.misc.pojo.EmployeeDailySales;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.Model;
import io.wabm.supermarket.model.TableViewModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.util.Callback;
import javafx.scene.control.TableView;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
/**
 * Created by Administrator on 2016/11/20 0020.
 */
@Repository
@ContextConfiguration(classes = DBConfig.class)
public class CashInformationModel<T> extends TableViewModel<T> {

    private final String kSelectAll = "SELECT c.*,e.name FROM change_record c,employee e where c.employee_id=e.employee_id";
    private final String InsertAll = "insert into change_record(employee_id,money_in_db,money_out_db,money_should_out_db,date) values(?,?,?,?,?)";
    private final String kSelectAllPrice = "select e.employee_id,e.name,sum(all_price_db) from employee e,sales_record s where e.employee_id=s.employee_id and timestamp=? and e.employee_id=? group by e.employee_id";
    private Service<Void> backgroundThread;

    public CashInformationModel(TableView tableView){
        super(tableView);
        ConsoleLog.print("CashInformationModel init");
    }
    /*public void insertDate(CashInformation cashInformation,Callback<Boolean, Void> callback){
        ConsoleLog.print("inserting Date...");

        new WABMThread().run((_void)->{
            try {
                jdbcOperations.update(InsertAll,
                        cashInformation.getEmployeeID(),
                        cashInformation.getMoneyIN(),
                        cashInformation.getMoneyOUT(),
                        cashInformation.getMoneyShould());
            }catch (DataAccessException e){
                e.printStackTrace();
            }
            callback.call(true);
            return null;
        });
    }
    */
    public void checkAllPriceData(String employeeid,BigDecimal moneyIN,BigDecimal moneyOUT,String date, Callback<Boolean, Void> callback){
        ConsoleLog.print("checkAllPriceData ...");

        new WABMThread().run((_void)->{
            CashInformation cashInformation;
            EmployeeDailySales employeeDailySales;
            try {
                employeeDailySales=jdbcOperations.queryForObject(kSelectAllPrice,(resultSet,i)->new EmployeeDailySales(
                        resultSet.getString("employee_id"),
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("sum(all_price_db)")
                ),date,employeeid);

                Double allprice = employeeDailySales.getDailySales().doubleValue();
                BigDecimal moneyShould = new BigDecimal(moneyIN.doubleValue()+allprice);

                if (allprice!=(moneyOUT.doubleValue()-moneyIN.doubleValue())) {
                    Platform.runLater(()->{
                        SimpleErrorAlert simpleErrorAlert = new SimpleErrorAlert("核算错误","应收金额为："+moneyShould.doubleValue(),
                                "错误金额为："+(moneyOUT.doubleValue()-moneyShould.doubleValue()));
                        simpleErrorAlert.show();
                    });
                }
                else {
                    Platform.runLater(() -> {
                        SimpleSuccessAlert simpleSuccessAlert = new SimpleSuccessAlert("核算正确",
                                "应收金额为：" + moneyShould.doubleValue(), "实收金额为：" + moneyOUT.doubleValue());
                        simpleSuccessAlert.show();
                    });
                }
                cashInformation = new CashInformation(employeeid,employeeDailySales.getName(),moneyIN,moneyOUT,moneyShould, LocalDate.now().toString());
                jdbcOperations.update(InsertAll,
                        cashInformation.getEmployeeID(),
                        cashInformation.getMoneyIN(),
                        cashInformation.getMoneyOUT(),
                        cashInformation.getMoneyShould(),
                        cashInformation.getDate());

                List<CashInformation> templist;
                templist = jdbcOperations.query(kSelectAll,
                        (resultSet, i) -> new CashInformation(
                                resultSet.getString("employee_id"),
                                resultSet.getString("name"),
                                resultSet.getBigDecimal("money_in_db"),
                                resultSet.getBigDecimal("money_out_db"),
                                resultSet.getBigDecimal("money_should_out_db"),
                                resultSet.getString("date")
                        )
                );
                list.clear();
                list.addAll((T[]) templist.toArray());

            }catch (DataAccessException e){
                Platform.runLater(()->{
                    SimpleErrorAlert simpleErrorAlert = new SimpleErrorAlert("核算失败","",
                            "该收银员今日无销售记录");
                    simpleErrorAlert.show();
                });
                e.printStackTrace();
            }

            callback.call(true);
            return null;
        });
    }
    public void fetchData(Callback<Boolean, Void> callback){
        ConsoleLog.print("fetching data…");
        Assert.notNull(jdbcOperations);

        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                    try {
                        List<CashInformation> templist;
                        templist = jdbcOperations.query(kSelectAll,
                                (resultSet, i) -> new CashInformation(
                                        resultSet.getString("employee_id"),
                                        resultSet.getString("name"),
                                        resultSet.getBigDecimal("money_in_db"),
                                        resultSet.getBigDecimal("money_out_db"),
                                        resultSet.getBigDecimal("money_should_out_db"),
                                        resultSet.getString("date")
                                )
                        );
                        list.clear();
                        list.addAll((T[]) templist.toArray());

                        // Return callback with isfetchSuccess flag;
                        // TODO:
                        callback.call(true);
                    }catch (DataAccessException e){
                        e.printStackTrace();
                    }

                        return null;
                    }
                };
            }
        };

        backgroundThread.start();

    };  // end of fetchData
}
