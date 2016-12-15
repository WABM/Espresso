package io.wabm.supermarket.misc.util;

import io.wabm.supermarket.misc.pojo.Employee;

/**
 * Created by liu on 2016-11-30 .
 */
public class GenderWrapper {
    private int sex;

    public GenderWrapper(int sex) {
        this.sex = sex;
    }

    public int getSex() {
        return sex;

    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return Employee.sexArray.get(sex);
    }
}
