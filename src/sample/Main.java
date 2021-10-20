package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(Thread.currentThread().getId());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        Scene scene = new Scene(root);

        scene.setOnMouseMoved(controller.getGunController());
        scene.addEventHandler(KeyEvent.KEY_PRESSED, controller.getFireHandler());

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("In memory of Derek Chauvin");
        stage.show();
    }
}
