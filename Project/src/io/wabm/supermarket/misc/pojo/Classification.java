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

    private BooleanProperty choosed;

    public Classification(int classificationID, String name, Double profitRate, Double taxRate) {
        this.classificationID = classificationID;
        this.name = new SimpleStringProperty(name);
        this.profitRate = new SimpleDoubleProperty(profitRate);
        this.taxRate = new SimpleDoubleProperty(taxRate);

        this.choosed = new SimpleBooleanProperty(false);
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

    public boolean isChoosed() {
        return choosed.get();
    }

    public BooleanProperty choosedProperty() {
        return choosed;
    }

    public void setChoosed(boolean choosed) {
        this.choosed.set(choosed);
    }
}
