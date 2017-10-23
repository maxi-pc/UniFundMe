import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class OutputController  implements Initializable {

    // method to initialize required arrays like the provinces drop-down menu.
    public void initialize(URL url, ResourceBundle resources) {
        // check search type
        System.out.println("Debug: Result window/scene done");
        dc = new DbConnection();
        loadDataFromDB();
    }

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    @FXML
    private VBox VBoxOutput;

    @FXML
    private Text txtLabelTotal;

    @FXML
    private TableView<AwardList> outputTable;

    @FXML
    private TableColumn<AwardList, String> colSource;

    @FXML
    private TableColumn<AwardList, String> colType;

    @FXML
    private TableColumn<AwardList, String> colName;

    @FXML
    private TableColumn<AwardList, String> colAmount;

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
    private void loadDataFromDB() {
        Connection conn = dc.Connect();
        data = FXCollections.observableArrayList();

        // execute query and store results in a resultset
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT award_source, award_type, award_name, award_amount FROM awards");

            while(rs.next()) {
              data.add(new AwardList(rs.getString("award_source"),rs.getString("award_type"), rs.getString("award_name"), rs.getString("award_amount")));
            }


        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }

        int total = data.size();
        txtLabelTotal.setText(Integer.toString(total));

        //set cell value factory to tableview
        //NB.PRopertyValue Factory must be the same with the one set in model class.

        colSource.setCellValueFactory(new PropertyValueFactory<>("source"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        outputTable.setItems(null);
        outputTable.setItems(data);

        System.out.println("Debug: Loading from database successful");

    }
}
