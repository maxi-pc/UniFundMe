
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

import java.awt.Desktop;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainController implements Initializable {

    // method to initialize required arrays like the provinces drop-down menu.
    public void initialize(URL location, ResourceBundle resources) {
        tf_province.setItems(list);
        Context.getInstance().setTabRough(this);
      //  tf_txtFieldGPA.max
    }

    @FXML
    public String searchType="";

    // main controller objects
    @FXML
    private VBox VBoxMain;

    @FXML
    public SplitMenuButton tf_btnSearch;

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
    public RadioButton tf_RdFull;

    @FXML
    private ToggleGroup studies;

    @FXML
    public RadioButton tf_RdPart;

    @FXML
    public RadioButton tf_RdDomestic;

    @FXML
    private ToggleGroup student;

    @FXML
    public RadioButton tf_RdInternational;

    @FXML
    public RadioButton tf_RdYesAbo;



    @FXML
    private ToggleGroup aboriginal;

    @FXML
    public RadioButton tf_RdNoAbo;

    @FXML
    public TextField tf_txtFieldGPA;

    @FXML
    public String study, locality, aboriginality;

    @FXML
    void validationCheck()throws IOException {

        String one, two, three, four, five, six, seven ="", eight ="", nine = "", error;
        boolean numeric = true, gpaformat = true, gpagreater = true;
        String gpa = tf_txtFieldGPA.getText();

        Pattern pattern = Pattern.compile("^\\d+\\.\\d{2}$");
        Matcher matcher = pattern.matcher(gpa);

        if (studies.getSelectedToggle() == null) {
            one = "Studies Check\n";
        } else {
            one = "";
        }
        if (student.getSelectedToggle() == null) {
             two = "Domestic Check\n";
        } else {
             two = "";
        }
        if (aboriginal.getSelectedToggle() == null) {
             three = "Aboriginal Check\n";
        } else {
             three = "";
        }
        if (tf_province.getValue() == null) {
             four = "Province Check\n";
        } else {
             four = "";
        }
        if (tf_school.getValue() == null) {
             five = "School Check\n";
        } else {
             five = "";
        }
        if (gpa.trim().isEmpty()) {
             six = "GPA Check\n";
        } else {
             six = "";
        }
        if (!gpa.trim().isEmpty()) {
            seven = "";
            eight = "";

           try {
               Double.valueOf(gpa);
            } catch(Exception e) {
               seven = "Non-Numeric GPA\n";
               numeric = false;
            }

            if (numeric == true) {
                if (matcher.matches()) {
                   Double gpanum = Double.valueOf(gpa);
                    if (gpanum > 4.33) {
                        nine = "GPA cannot exceed 4.33\n";
                        gpagreater = false;
                    }
                    else if(gpanum <= 1.00) {
                        nine = "Please consider dropping out of school\n";
                        gpagreater = false;
                    }
                } else {
                    eight = "Invalid GPA format (0.00)\n";
                    gpaformat = false;
                }


            }

        }

        System.out.println(tf_txtFieldGPA.getText().toString());

         error = four + five + one + two + three + six + seven + eight + nine;
        if (studies.getSelectedToggle() == null || student.getSelectedToggle() == null ||
                aboriginal.getSelectedToggle() == null || tf_province.getValue() == null ||
                tf_school.getValue() == null || gpa.trim().isEmpty() || numeric == false || gpaformat == false || gpagreater == false) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Input Validation");
            alert.setHeaderText("Please make sure you have selected the following inputs");
            alert.setContentText(error);


            alert.showAndWait();

        }
        else {

            setRadioButtonValues();
            System.out.println("GOOD");
            VBox vBox = FXMLLoader.load(getClass().getResource("UniFundMeResults.fxml"));
            VBoxMain.getChildren().setAll(vBox);
        }
    }


    // main controller event methods
    @FXML
    void onDefault(ActionEvent event) throws IOException {
        searchType = "default";
        validationCheck();
       // String test = studies.getSelectedToggle().toString();
        //System.out.println(test);
    	//System.out.println("Debug: method to initialize result window/scene");
    }
    @FXML
    void onScholar(ActionEvent event) throws IOException {
        searchType = "scholar";
        validationCheck();
        System.out.println("Debug: method to initialize result Scholarships");
        VBox vBox = FXMLLoader.load(getClass().getResource("UniFundMeResults.fxml"));
        VBoxMain.getChildren().setAll(vBox);
    }
    @FXML
    void onGrants(ActionEvent event) throws IOException {
        searchType = "grant";
        validationCheck();
        System.out.println("Debug: method to initialize result Scholarships");
        VBox vBox = FXMLLoader.load(getClass().getResource("UniFundMeResults.fxml"));
        VBoxMain.getChildren().setAll(vBox);
    }
    @FXML
    void onBrusaries(ActionEvent event) throws IOException {
        searchType = "brusary";
        validationCheck();
        System.out.println("Debug: method to initialize result Scholarships");
        VBox vBox = FXMLLoader.load(getClass().getResource("UniFundMeResults.fxml"));
        VBoxMain.getChildren().setAll(vBox);
    }

    @FXML
    void onDocumentation(ActionEvent event) {

    }

    //Load the GitHub web address in external web browser
    @FXML
    void onGithub(ActionEvent event) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("https://github.com/maximilious/UniFundMe"));
            }
        } catch (Exception ex){
            System.out.println("Failed to open GitHub link.");
        }
    }

    @FXML
    void onPrint(ActionEvent event) {

    }

    //Quit the application
    @FXML
    void onQuit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onSettings(ActionEvent event) {
    }

    //Handling external resource for wiki information
    @FXML
    void onWiki(ActionEvent event) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("http://dereksmart.com/2016/06/star-citizen-fidelity-of-failure/"));
            }
        } catch (Exception ex){
            System.out.println("Failed to open wiki.");
        }
    }

    // set the values from radio buttons to values
    @FXML
    public void setRadioButtonValues() {

        // full time or part time student radio buttons
        if(tf_RdFull.isSelected()) {
            study = "1";
        }
        else if(tf_RdPart.isSelected()) {
            study = "0";
        }

        // domestic or international radio buttons
        if(tf_RdDomestic.isSelected()) {
            locality = "1";
        }
        else if(tf_RdInternational.isSelected()) {
            locality = "0";
        }

        // aboriginal or not radio buttons
        if(tf_RdYesAbo.isSelected()) {
            aboriginality = "1";
        }
        else if(tf_RdNoAbo.isSelected()) {
            aboriginality = "0";
        }
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
