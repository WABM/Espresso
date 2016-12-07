package io.wabm.supermarket.model.management;

import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.TableViewModel;
import io.wabm.supermarket.model.XYChartModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by liu on 2016-12-05 .
 */
public class SalesModel extends XYChartModel<String, Double> {
    private  String kSelectsql =
            "SELECT  ifnull(sum(all_price_db),0.0) allmoney, date_format(`timestamp`, '%e') day, date_format(`timestamp`, '%Y-%m')date\n" +
            "from sales_record\n" +
            "WHERE date_format(`timestamp`, '%Y-%m') = ?\n" +
            "GROUP BY day,date";

    private Service<Void> backgroundThread;
    private XYChart chart;
    ObservableList<XYChart.Data<String, Double>> list = FXCollections.observableArrayList();

    public SalesModel(XYChart chart){
        super();

        this.chart = chart;
        series.setData(list);
        chart.getData().add(series);
        ConsoleLog.print("SalesModel init");
    }
    public void fetchData(String c,Callback<Boolean, Void> callback){
        ConsoleLog.print("fetching data…");
        Assert.notNull(jdbcOperations);

        //String[] days = {"1", "2","3","4","5"};

        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        ConsoleLog.print("call…");

                        Double[] moneyArray = new Double[31];
                        try {
                            List<Double> templist;

                            templist = jdbcOperations.query(kSelectsql,
                                    (resultSet, i)-> {
                                        Double money = resultSet.getDouble("allmoney");
                                        int index = resultSet.getInt("day");
                                        moneyArray[index] = money;

                                        return money;
                                    },c);


                            Platform.runLater(() -> {
                                list.clear();
                                for (int i = 0; i < moneyArray.length; i++) {
                                    list.add(new XYChart.Data<>((i + 1)+"", (moneyArray[i] != null) ? moneyArray[i] : new Double(0.0)));
                                    ConsoleLog.print("array[" + i + "] = "+ moneyArray[i]);
                                }
                            });


                            //days[i]应该是你选择的月的天数

                            callback.call(true);

                        } catch (DataAccessException exception){
                            callback.call(false);
                            exception.printStackTrace();
                        } catch (Exception e) {
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
