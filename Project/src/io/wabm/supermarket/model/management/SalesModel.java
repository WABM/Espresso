package io.wabm.supermarket.model.management;

import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.XYChartModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.List;

/**
 * Created by liu on 2016-12-05 .
 */
public class SalesModel extends XYChartModel<String, Double> {
    private  String kSelectsql =
            "SELECT  ifnull(sum(all_price_db),0.0) allmoney, date_format(`timestamp`, '%e') day, date_format(`timestamp`, '%Y-%m')date\n" +
            "from sales_record\n" +
            "WHERE date_format(`timestamp`, '%Y') = ? and date_format(`timestamp`, '%m') = ?\n" +
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
        public static int getDaysByYearMonth(String year,String month)
        {
            Calendar a = Calendar.getInstance();
            a.set(Calendar.YEAR,Integer.parseInt(year));
            a.set(Calendar.MONTH,Integer.parseInt(month)-1);
            a.set(Calendar.DATE, 1);
            a.roll(Calendar.DATE,-1);
            int maxDate = a.get(Calendar.DATE);
            return maxDate;
        }

    public void fetchData(String a, String b, Callback<Boolean, Void> callback, Label allLabel){
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

                       // Double[] moneyArray = new Double[31];
                        int number = getDaysByYearMonth(a,b);
                        Double[] moneyArray =  new Double[number];

                        try {
                            List<Double> templist;

                            templist = jdbcOperations.query(kSelectsql,
                                    (resultSet, i)-> {
                                        Double money = resultSet.getDouble("allmoney");  //取钱
                                        int index = resultSet.getInt("day");  //day是 日
                                        moneyArray[index-1] = money;

                                        return money;
                                    },a,b);


                            Platform.runLater(() -> {
                                list.clear();
                                double all =0.0;
                                for (int i = 0; i < moneyArray.length; i++) {
                                    all+=moneyArray[i];
                                    list.add(new XYChart.Data<>((i+1)+"", (moneyArray[i] != null) ? moneyArray[i] : new Double(0.0)));
                                    ConsoleLog.print("array[" + i + "] = "+ moneyArray[i]);
                                }
                                allLabel.setText(String.format("%.2f",all));
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

    };  // end of fetchData


}
