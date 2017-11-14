import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.script.Bindings;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;


public class OutputController  implements Initializable {

    // method to initialize required arrays like the provinces drop-down menu.
    public void initialize(URL url, ResourceBundle resources) {
        // check search type
        System.out.println("Debug: Result window/scene done");
        dc = new DbConnection();

        // set connection between main controller objects and the results / output controller
        MainController cont=Context.getInstance().getTabRough();
        // pass values from main controller to this controller (output controller)
        String searchType = cont.searchType;
        String school = cont.tf_school.getValue();
        String gpa = cont.tf_txtFieldGPA.getText();
        String study = cont.study;
        String locality = cont.locality;
        String aboriginality = cont.aboriginality;
        String sourceType = "";

        // debug printout, checking to see if values are being passed correctly.
        System.out.println(school);
        System.out.println(gpa);
        System.out.println(study);
        System.out.println(locality);
        System.out.println(aboriginality);

        // launch default search query with scholarships, brusaries, and grants
       if (searchType == "default") {
           loadDataDefault(school, study, locality, aboriginality, gpa);
        }
        // launch search query only with scholarships.
       else if(searchType == "scholar") {
           sourceType = "Scholarship";
           loadDataMenu(school, study, locality, aboriginality, gpa, sourceType);
        }
       // launch search query only with brusaries.
       else if(searchType == "brusary") {
           sourceType = "Bursary";
           loadDataMenu(school, study, locality, aboriginality, gpa, sourceType);
       }
       // launch search query only with grants.
       else if(searchType == "grant") {
           sourceType = "Grant";
           loadDataMenu(school, study, locality, aboriginality, gpa, sourceType);
       }
       else {
           // maybe needed maybe not
       }
        System.out.println(sourceType);


    }

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    @FXML
    private VBox VBoxOutput;

    @FXML
    private Text txtLabelTotal;

    @FXML
    private Text txtLabelSchool;

    @FXML
    private TableView<AwardList> outputTable;

    @FXML
    private TableColumn<AwardList, String> colSource;

    @FXML
    private TableColumn<AwardList, String> colType;

    @FXML
    private TableColumn<AwardList, String> colName;

    @FXML
    private TableColumn<AwardList, Number> colAmount = new TableColumn<>("Amount");

    private ObservableList<AwardList>data;

    private DbConnection dc;

    @FXML
    private Button BtnBack;

    @FXML
    void onLoadMain(ActionEvent event) throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("UniFundMe.fxml"));
        VBoxOutput.getChildren().setAll(vBox);
        System.out.println("Debug: Successfully back at Main window/scene");
    }

    @FXML
    void onDocumentation(ActionEvent event) {

    }

    @FXML
    void onGithub(ActionEvent event) {

    }

    @FXML
    MainController cont=Context.getInstance().getTabRough();
    public boolean printable = cont.printable;


    @FXML
    void onPrint(ActionEvent event) {
        //MainController cont=Context.getInstance().getTabRough();
        //boolean printable = cont.printable;


      //  if (printable == true) {
        //    PrinterJob printerJob = PrinterJob.createPrinterJob();
         //   if(printerJob.showPrintDialog(VBoxOutput.getScene().getWindow()) && printerJob.printPage(outputTable))
        //        printerJob.endJob();
         //   System.out.println(printable);

          //  printerJob.showPageSetupDialog(con.getClass())
                   // Window =
    //    }
    //    else {
            // do nothing
         //   System.out.println(printable);
    //    }

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(VBoxOutput.getScene().getWindow())){
            boolean success = job.printPage(outputTable);
            if (success) {
                job.endJob();
            }
        }
    }

    @FXML
    void onQuit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onSettings(ActionEvent event) {

    }

  // @FXML
  //  void onSave(ActionEvent event) throws Exception {
 //       try {
  //          writeExcel();
  //      } catch (Exception e) {
  //          e.printStackTrace();
  //      }us
  //  }

    @FXML
    void onSave(ActionEvent event) throws Exception {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilterCVS =
                //     new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                new FileChooser.ExtensionFilter("CVS files (*.cvs)", "*.csv");
        FileChooser.ExtensionFilter extFilterTXT =
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().addAll(extFilterCVS, extFilterTXT);

            //Show save file dialog
            File file = fileChooser.showSaveDialog(VBoxOutput.getScene().getWindow());

            if (file != null) {
                SaveFile(file);
            }
    }

    @FXML
    void onWiki(ActionEvent event) {
    //    CustomControl c = new CustomControl();
     //   c.getCustomId();
    //    System.out.println(c);
    }

    @FXML
    public void loadDataDefault(String school, String study, String locality, String aboriginality, String gpa) {
        String schoolVal = school;
        Connection conn = dc.Connect();
        data = FXCollections.observableArrayList();

        if (schoolVal == "Select All Schools") {
            schoolVal = "All Schools";

            try {
                PreparedStatement sql = conn.prepareStatement("SELECT award_source, award_type, school_name, award_amount FROM awards AS A INNER JOIN schools AS S ON A.award_school_id = S.school_id WHERE award_studies = ? AND award_student_type = ? AND award_aboriginality = ? AND award_req_gpa <= ?");
                sql.setString(1, study);
                sql.setString(2, locality);
                sql.setString(3, aboriginality);
                sql.setString(4, gpa);
                ResultSet rs = sql.executeQuery();
                while (rs.next()) {
                    data.add(new AwardList(rs.getString("award_source"), rs.getString("award_type"), rs.getString("school_name"), rs.getDouble("award_amount")));
                }


            } catch (SQLException ex) {
                System.err.println("Error" + ex);
            }

            // just tiny bit of code to count the size of the created tableview (results) and set it to a label.
            int total = data.size();
            txtLabelTotal.setText(Integer.toString(total));
            txtLabelSchool.setText(schoolVal);

            //set cell value factory to tableview
            //NB.PRopertyValue Factory must be the same with the one set in AwardList class.

            colSource.setCellValueFactory(new PropertyValueFactory<>("source"));
            colType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

            // format the database double number to dollar and two decimal places.
            colAmount.setCellFactory(tc -> new TableCell<AwardList, Number>() {
                @Override
                protected void updateItem(Number amount, boolean empty) {
                    if (empty) {
                        setText("");
                    } else {
                        setText(String.format("$%.2f", amount.doubleValue()));
                    }
                }
            });

          //  colAmount.setCellValueFactory(cellData ->
          //          Bindings.format("%.2f", cellData.getValue().getPropertyMyDouble()));

            //.setCellFactory(new ColumnFormatter<Levels, Double>(new DecimalFormat("0.0dB")));


            outputTable.setItems(null);
            outputTable.setItems(data);

            System.out.println("Debug: Loading default from database successful");
            printable = true;

        } else {

            // execute query and store results in a resultset
            try {
                // ResultSet rs = conn.createStatement().executeQuery("SELECT award_source, award_type, award_name, award_amount FROM awards");
                //  ResultSet rs = conn.createStatement().executeQuery("SELECT award_source, award_type, award_name, award_amount FROM awards INNER JOIN schools AS S ON awards.award_school_id = S.school_id WHERE S.school_name LIKE \"%schoolVal%\"");
                PreparedStatement sql = conn.prepareStatement("SELECT award_source, award_type, school_name, award_amount FROM awards AS A INNER JOIN schools AS S ON A.award_school_id = S.school_id WHERE school_name = ? AND award_studies = ? AND award_student_type = ? AND award_aboriginality = ? AND award_req_gpa <= ?;");
                sql.setString(1,schoolVal);
                sql.setString(2, study);
                sql.setString(3, locality);
                sql.setString(4, aboriginality);
                sql.setString(5, gpa);

                ResultSet rs = sql.executeQuery();
                while (rs.next()) {
                    data.add(new AwardList(rs.getString("award_source"), rs.getString("award_type"), rs.getString("school_name"), rs.getDouble("award_amount")));
                }


            } catch (SQLException ex) {
                System.err.println("Error" + ex);
            }

            // just tiny bit of code to count the size of the created tableview (results) and set it to a label.
            int total = data.size();
            txtLabelTotal.setText(Integer.toString(total));
            txtLabelSchool.setText(schoolVal);

            //set cell value factory to tableview
            //NB.PRopertyValue Factory must be the same with the one set in AwardList class.

            colSource.setCellValueFactory(new PropertyValueFactory<>("source"));
            colType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

            // format the database double number to dollar and two decimal places.
            colAmount.setCellFactory(tc -> new TableCell<AwardList, Number>() {
                        @Override
                        protected void updateItem(Number amount, boolean empty) {
                            if (empty) {
                                setText("");
                            } else {
                                setText(String.format("$%.2f", amount.doubleValue()));
                            }
                        }
                    });

            //colAmount.setCellFactory(new ColumnFormatter<Levels, Double>(new DecimalFormat("0.0dB")));


            outputTable.setItems(null);
            outputTable.setItems(data);

            System.out.println("Debug: Loading default from database successful");
            printable = true;

        }
    }

    @FXML
    public void loadDataMenu(String school, String study, String locality, String aboriginality, String gpa, String sourceType) {
        String schoolVal = school;
        Connection conn = dc.Connect();
        data = FXCollections.observableArrayList();

        if (schoolVal == "Select All Schools") {
            schoolVal = "All Schools";

            try {
                PreparedStatement sql = conn.prepareStatement("SELECT award_source, award_type, school_name, award_amount FROM awards AS A INNER JOIN schools AS S ON A.award_school_id = S.school_id WHERE award_studies = ? AND award_student_type = ? AND award_aboriginality = ? AND award_req_gpa <= ? and award_type = ?");
                sql.setString(1, study);
                sql.setString(2, locality);
                sql.setString(3, aboriginality);
                sql.setString(4, gpa);
                sql.setString(5, sourceType);

                ResultSet rs = sql.executeQuery();
                while (rs.next()) {
                    data.add(new AwardList(rs.getString("award_source"), rs.getString("award_type"), rs.getString("school_name"), rs.getDouble("award_amount")));
                }


            } catch (SQLException ex) {
                System.err.println("Error" + ex);
            }

            // just tiny bit of code to count the size of the created tableview (results) and set it to a label.
            int total = data.size();
            txtLabelTotal.setText(Integer.toString(total));
            txtLabelSchool.setText(schoolVal);


            //
            colSource.setCellValueFactory(new PropertyValueFactory<>("source"));
            colType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

            colAmount.setCellFactory(tc -> new TableCell<AwardList, Number>() {
                @Override
                protected void updateItem(Number amount, boolean empty) {
                    if (empty) {
                        setText("");
                    } else {
                        setText(String.format("$%.2f", amount.doubleValue()));
                    }
                }
            });


            outputTable.setItems(null);
            outputTable.setItems(data);

            System.out.println("Debug: Loading specific from database successful");
            printable = true;

        } else {

            // execute query and store results in a resultset
            try {
                // ResultSet rs = conn.createStatement().executeQuery("SELECT award_source, award_type, award_name, award_amount FROM awards");
                //  ResultSet rs = conn.createStatement().executeQuery("SELECT award_source, award_type, award_name, award_amount FROM awards INNER JOIN schools AS S ON awards.award_school_id = S.school_id WHERE S.school_name LIKE \"%schoolVal%\"");
                PreparedStatement sql = conn.prepareStatement("SELECT award_source, award_type, award_name, award_amount FROM awards AS A INNER JOIN schools AS S ON A.award_school_id = S.school_id WHERE school_name = ? AND award_studies = ? AND award_student_type = ? AND award_aboriginality = ? AND award_req_gpa <= ? AND award_type = ?;");
                sql.setString(1,schoolVal);
                sql.setString(2, study);
                sql.setString(3, locality);
                sql.setString(4, aboriginality);
                sql.setString(5, gpa);
                sql.setString(6, sourceType);
                ResultSet rs = sql.executeQuery();

                while (rs.next()) {
                    data.add(new AwardList(rs.getString("award_source"), rs.getString("award_type"), rs.getString("award_name"), rs.getDouble("award_amount")));
                }


            } catch (SQLException ex) {
                System.err.println("Error" + ex);
            }

            // just tiny bit of code to count the size of the created tableview (results) and set it to a label.
            int total = data.size();
            txtLabelTotal.setText(Integer.toString(total));
            txtLabelSchool.setText(schoolVal);

            //set cell value factory to tableview
            //NB.PRopertyValue Factory must be the same with the one set in AwardList class.

            colSource.setCellValueFactory(new PropertyValueFactory<>("source"));
            colType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

            // change format of amount from awards to two decimal and dollar sign.
            colAmount.setCellFactory(tc -> new TableCell<AwardList, Number>() {
                @Override
                protected void updateItem(Number amount, boolean empty) {
                    if (empty) {
                        setText("");
                    } else {
                        setText(String.format("$%.2f", amount.doubleValue()));
                    }
                }
            });

            //.setCellFactory(new ColumnFormatter<Levels, Double>(new DecimalFormat("0.0dB")));


            outputTable.setItems(null);
            outputTable.setItems(data);

            System.out.println("Debug: Loading specific from database successful");
            printable = true;

        }
    }

    public void writeExcel() throws Exception {

        Writer writer = null;
        try {
            File file = new File("C:\\Awards.csv.");
            writer = new BufferedWriter(new FileWriter(file));
            for (AwardList award : data) {

                String text = award.getSource() + "," + award.getType() + "," + award.getName() + "," + award.getAmount() + "\n";

                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {

            writer.flush();
            writer.close();
        }
    }

    private void SaveFile(File file) throws Exception{
     //   Writer writer = null;
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            // working writer = new BufferedWriter(new FileWriter(file));
            //  BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            String tempPath = file.getCanonicalPath().toLowerCase();
            // check if saving file to a text format since it needs a newLine for it to display properly
            if (tempPath.endsWith(".txt")) {
                for (AwardList award : data) {
                    String text = award.getSource() + " ----- " + award.getType() + " ----- " + award.getName() + " ----- " + award.getAmount() + "\n";
                    writer.write(text);
                    writer.newLine();
                    writer.newLine();
                }
                // do normal saving for cvs file
            } else {
                for (AwardList award : data) {
                    String text = award.getSource() + "," + award.getType() + "," + award.getName() + "," + award.getAmount() + "\n";
                    writer.write(text);
              //      writer.newLine();
                }
            }
            writer.flush();
            writer.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    //    finally {
    //        writer.flush();
    //        writer.close();
    //    }
    }

}
