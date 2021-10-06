package group1.learn_fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStream;
import java.sql.SQLException;
import java.io.IOException;
import dictionary.database_manage;

import com.gtranslate.Audio;
import com.gtranslate.Language;

//import javax.speech.synthesis;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) throws SQLException {
        database_manage.set_database();
        launch();
        database_manage.close_database();
    }
}

