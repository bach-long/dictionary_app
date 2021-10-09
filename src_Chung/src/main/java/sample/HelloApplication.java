package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.io.IOException;

//import javax.speech.synthesis;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
       //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("DICTIONARY!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) throws SQLException {
        database_manage.set_database();
        launch();
        database_manage.close_database();
    }
}