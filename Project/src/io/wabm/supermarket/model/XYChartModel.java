package io.wabm.supermarket.model;

import io.wabm.supermarket.application.Main;
import javafx.scene.chart.XYChart;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * Created by liu on 2016-12-07 .
 */
public class XYChartModel<X, Y> {

    protected XYChart.Series<X, Y> series = new XYChart.Series<>();
    protected JdbcOperations jdbcOperations;

    public XYChartModel() {
        jdbcOperations = Main.getJdbcOperations();
    }

}
