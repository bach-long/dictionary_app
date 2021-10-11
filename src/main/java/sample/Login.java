package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {
    public static String name;

    @FXML
    private TextField nameLogin;

    @FXML
    private Button nextLogin;

    @FXML
    private ImageView logo;

    @FXML
    private JFXButton loginButton;



    @FXML
    void nameLogin(MouseEvent event) throws IOException {
        name = nameLogin.getText();
        Stage stage = (Stage)loginButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("scene1.fxml"));
        stage.setScene(new Scene(root));
    }

    @FXML
    void skip(MouseEvent event) throws IOException {
        Stage stage = (Stage)nextLogin.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage.setScene(new Scene(root));
    }

    @FXML
    void inputName(KeyEvent event) throws IOException {
        switch (event.getCode()){
            case ENTER:
                name = nameLogin.getText();
                Stage stage = (Stage)nextLogin.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("scene1.fxml"));
                stage.setScene(new Scene(root));
                break;
            default:
                break;
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
