import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.NumberFormat;

/**
 * Created by Maxi on 10/22/2017.
 * this is the class that manages awards taken from the database using contructors, setters, getters, etc...
 */
public class AwardList {

    private final StringProperty source;
    private final StringProperty type;
    private final StringProperty name;
    //private final StringProperty amount;
    private DoubleProperty amount = new SimpleDoubleProperty();

    public String getSource() {
        return source.get();
    }

    public void setSource(String source) {
        this.source.set(source);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
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

    public Double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public AwardList(String source, String type, String name, Double amount) {
        this.source = new SimpleStringProperty(source);
        this.type = new SimpleStringProperty(type);
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleDoubleProperty(amount);
        //this.amount = new Number();

    }

}
