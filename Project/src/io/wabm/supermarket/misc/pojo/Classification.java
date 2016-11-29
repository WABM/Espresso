package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.*;

/**
 * Created by MainasuK on 2016-11-14.
 */
public class Classification {
    private int classificationID;
    private StringProperty name;
    private DoubleProperty profitRate;
    private DoubleProperty taxRate;

    private IntegerProperty hasNum = new SimpleIntegerProperty();

    public Classification(int classificationID, String name, Double profitRate, Double taxRate) {
        this.classificationID = classificationID;
        this.name = new SimpleStringProperty(name);
        this.profitRate = new SimpleDoubleProperty(profitRate);
        this.taxRate = new SimpleDoubleProperty(taxRate);
    }

    // MARK: Getter & Setter
    public int getClassificationID() {
        return classificationID;
    }

    public void setClassificationID(int classificationID) {
        this.classificationID = classificationID;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getProfitRate() {
        return profitRate.get();
    }

    public DoubleProperty profitRateProperty() {
        return profitRate;
    }

    public void setProfitRate(double profitRate) {
        this.profitRate.set(profitRate);
    }

    public double getTaxRate() {
        return taxRate.get();
    }

    public DoubleProperty taxRateProperty() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate.set(taxRate);
    }

    public int getHasNum() {
        return hasNum.get();
    }

    public IntegerProperty hasNumProperty() {
        return hasNum;
    }

    public void setHasNum(int hasNum) {
        this.hasNum.set(hasNum);
    }


    @Override
    public String toString() {
        return getName();
    }
}
