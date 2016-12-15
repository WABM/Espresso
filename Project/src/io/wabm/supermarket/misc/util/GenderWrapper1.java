package io.wabm.supermarket.misc.util;

import io.wabm.supermarket.misc.pojo.Employee;

/**
 * Created by liu on 2016-12-11 .
 */
public class GenderWrapper1 {
        private int department;

        public GenderWrapper1(int department) {
            this.department = department;
        }

        public int getDepartment() {
            return department;

        }

        public void setDepartment(int department) {
            this.department = department;
        }

        @Override
        public String toString() {
            return Employee.positonArray.get(department);
        }

}
