package io.wabm.supermarket.model.sales;

import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.XYChartModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

/**
 * Created by blue on 2018/4/23.
 */
public class SalesGetModel extends XYChartModel<String,Double> {
    private String GSelectsql ="SELECT  ifnull(sum(all_price_db),0.0) allmoney, date_format(`timestamp`, '%m') month, date_format(`timestamp`, '%Y') date\n" +
            " from sales_record s\n" +
            " WHERE date_format(`timestamp`, '%Y') = ?\n" +
            " GROUP BY month,date";
    private String ESelectsql ="SELECT ifnull(SUM(od.quantity*od.price_db),0.0) allmoney ,date_format(production_date, '%m') month,date_format(production_date, '%Y') date\n" +
            " FROM order_detail od\n" +
            " WHERE date_format(production_date, '%Y') = ?\n" +
            " GROUP BY month";
    private Service<Void> backgroundThread;
    private XYChart chart;
    ObservableList<XYChart.Data<String, Double>> list = FXCollections.observableArrayList();

    public SalesGetModel(XYChart chart){
        super();

        this.chart = chart;
        series.setData(list);
        chart.getData().add(series);
        ConsoleLog.print("SalesGetModel init");
    }

    public void fetchData(String year,Callback<Boolean, Void> callback){
        ConsoleLog.print("fetching data…");
        Assert.notNull(jdbcOperations);

        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        ConsoleLog.print("call…");

                        double[] profitArray = new double[12];
                        double[] expendArray = new double[12];
                        try {
                            jdbcOperations.query(GSelectsql,
                                    (resultSet, i)-> {
                                        Double money = resultSet.getDouble("allmoney");
                                        int index = resultSet.getInt("month");
                                        profitArray[index-1] = money;

                                        return money;
                                    },year);

                            jdbcOperations.query(ESelectsql,
                                    (resultSet, i)-> {
                                        Double money = resultSet.getDouble("allmoney");
                                        int index = resultSet.getInt("month");
                                        expendArray[index-1] = money;

                                        return money;
                                    },year);

                            Platform.runLater(() -> {
                                list.clear();
                                for (int i = 0; i < profitArray.length; i++) {
                                    list.add(new XYChart.Data<>((i+1)+"",profitArray[i]-expendArray[i]));
                                    ConsoleLog.print("array[" + i + "] = "+ (profitArray[i]-expendArray[i]));
                                }
                            });


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

    }
}
