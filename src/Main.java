import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Main extends Application {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Context2.getInstance().setTabRough(this);
        VBox mainVBox = (VBox) FXMLLoader.load(Main.class.getResource("UniFundMe.fxml"));
        primaryStage.setScene(new Scene(mainVBox));
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setTitle("UniFundMe");
      // public Window lol = primaryStage.getOwner();
     //  String mainTest = "lol";
    }





}
