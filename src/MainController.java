import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    // method to initialize required arrays like the provinces drop-down menu.
    public void initialize(URL location, ResourceBundle resources) {
        tf_province.setItems(list);
        Context.getInstance().setTabRough(this);
    }


    // main controller objects
    @FXML
    private VBox VBoxMain;

    @FXML
    private SplitMenuButton tf_btnSearch;

    @FXML
    public  ComboBox<String> tf_province;

    ObservableList<String> list = FXCollections.observableArrayList(
            "Alberta",
            "British Columbia",
            "Manitoba",
            "New Brunswick",
            "Newfoundland and Labrador",
            "Nova Scotia",
            "Ontario",
            "Prince Edward Island",
            "Quebec",
            "Saskatchewan",
            "Select All Provinces"
    );

    public void setSchool(String school){
     //   this.tf_school.getValue() = school;
    }


    @FXML
    public ComboBox<String> tf_school;

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
    private TextField tf_txtFieldGPA;

    // main controller event methods
    @FXML
    void onDefault(ActionEvent event) throws IOException {

    	System.out.println("Debug: method to initialize result window/scene");
    	VBox vBox = FXMLLoader.load(getClass().getResource("UniFundMeResults.fxml"));
    	VBoxMain.getChildren().setAll(vBox);
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
        //int last = tf_province.getSelectionModel().getSelectedIndex();
       // System.out.println(last);
    }

    @FXML
    void onDocumentation(ActionEvent event) {

    }

    @FXML
    void onGithub(ActionEvent event) {

    }

    @FXML
    void onPrint(ActionEvent event) {

    }

    @FXML
    void onQuit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onSettings(ActionEvent event) {
    }

    @FXML
    void onWiki(ActionEvent event) {

    }

    @FXML
    public void loadProvince() {
        // create array for all provinces in canada.
        ObservableList<String> list = FXCollections.observableArrayList(
                "Alberta",
                "British Columbia",
                "Manitoba",
                "New Brunswick",
                "Newfoundland and Labrador",
                "Nova Scotia",
                "Ontario",
                "Prince Edward Island",
                "Quebec",
                "Saskatchewan",
                "Select All Provinces"
        );
     //   tf_province.setItems(list);
    }

    @FXML
    public void checkProvince(ActionEvent event) {
        // get the current index value of the combobox for the province drop-down list.
        int i = tf_province.getSelectionModel().getSelectedIndex();
        // if index selected is 1 (BC) then load BC schools
        if(i == 1){
        loadBC();
        }
        // if index selected is 10 (all schools) then load all (BC)
        else if(i == 10) {
        loadAll();
        }
        else{
        tf_school.getItems().clear();
        }
    }

    @FXML
    public void loadBC() {
        // create array for all schools in BC.
        ObservableList<String> list = FXCollections.observableArrayList(
                "Camosun College",
                "College of New Caledonia",
                "College of the Rockies",
                "Douglas College",
                "Langara College",
                "North Island College",
                "Northern Lights College",
                "Northwest Community College ",
                "Okanagan College",
                "Selkirk College",
                "Vancouver Community College",
                "Capilano University",
                "Emily Carr University of Art and Design",
                "Kwantlen Polytechnic University",
                "Royal Roads University",
                "Simon Fraser University",
                "Thompson Rivers University",
                "University of British Columbia",
                "University of the Fraser Valley",
                "University of Northern British Columbia",
                "University of Victoria",
                "Vancouver Island University",
                "British Columbia Institute of Technology",
                "Justice Institute of British Columbia",
                "Nicola Valley Institute of Technology",
                "Select All Schools"
        );
        tf_school.setItems(list);
    }

    @FXML
    public void loadAll() {
        loadBC();
    }

}
