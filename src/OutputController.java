import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

import java.io.IOException;

public class OutputController {

    @FXML
    private VBox VBoxOutput;

    @FXML
    private Text txtLabelTotal;

    @FXML
    private TableView<?> outputTable;

    @FXML
    private TableColumn<?, ?> colSource;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private Button BtnBack;

    @FXML
    void onLoadMain(ActionEvent event) throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("UniFundMe.fxml"));
        VBoxOutput.getChildren().setAll(vBox);
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

}
