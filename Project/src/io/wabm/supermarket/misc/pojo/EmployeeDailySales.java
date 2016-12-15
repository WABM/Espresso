package io.wabm.supermarket.misc.pojo;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/12/8 0008.
 */
public class EmployeeDailySales {
    private String employee_id;
    private String name;
    private BigDecimal dailySales;

    public EmployeeDailySales(String employee_id, String name, BigDecimal dailysales) {
        this.employee_id = employee_id;
        this.name = name;
        this.dailySales = dailysales;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDailySales() {
        return dailySales;
    }

    public void setDailySales(BigDecimal dailySales) {
        this.dailySales = dailySales;
    }
}
