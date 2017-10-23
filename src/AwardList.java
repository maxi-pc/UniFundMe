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
    private final StringProperty amount;

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

    public String getAmount() {
        return amount.get();
    }

    public StringProperty amountProperty() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount.set(amount);
    }

    public AwardList(String source, String type, String name, String amount) {
        this.source = new SimpleStringProperty(source);
        this.type = new SimpleStringProperty(type);
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleStringProperty(amount);

    }

}
