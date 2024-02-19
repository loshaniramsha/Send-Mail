package lk.mail;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/Mail_Send_Form.fxml"));
        Scene scene=new Scene(fxmlLoader.load());
        stage.setTitle("Send Mail");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
