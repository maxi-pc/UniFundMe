import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.ToggleGroup;
import org.controlsfx.control.textfield.CustomTextField;

public class MyController {

    @FXML
    private SplitMenuButton tf_btnSearch;


    @FXML
    private ComboBox<?> tf_province;

    @FXML
    private ComboBox<?> tf_school;

    @FXML
    private RadioButton tf_RdFull;

    @FXML
    private ToggleGroup studies;

    @FXML
    private RadioButton tf_RdPart;

    @FXML
    private RadioButton tf_RdDomestic;

    @FXML
    private ToggleGroup student;

    @FXML
    private RadioButton tf_RdInternational;

    @FXML
    private RadioButton tf_RdYesAbo;

    @FXML
    private ToggleGroup aboriginal;

    @FXML
    private RadioButton tf_RdNoAbo;

    @FXML
    private CustomTextField tf_txtFieldGPA;

    @FXML
    void onDefault(ActionEvent event) {

    	System.out.println("MySQL Query for displaying ALL awards");
    }
    @FXML
    void onScholar(ActionEvent event) {

        System.out.println("MySQL Query for only Scholarships");
    }
    @FXML
    void onGrants(ActionEvent event) {

        System.out.println("MySQL Query for only Grants");
    }
    @FXML
    void onBrusaries(ActionEvent event) {

        System.out.println("MySQL Query for only Brusaries");
    }

}
