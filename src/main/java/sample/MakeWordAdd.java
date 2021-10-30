package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MakeWordAdd implements Initializable {
    @FXML
    private TextArea wordAddIn;

    @FXML
    private TextArea meanAddIn;

    @FXML
    private JFXButton add;

    @FXML
    private JFXButton prev;

    @FXML
    private WebView webAdd;
    public WebEngine engine = null;

    /**sua cac tu add.*/
    @FXML
    void makeWordAdd(MouseEvent event) throws SQLException, IOException {
        if (meanAddIn.getText() != "") {
            database_manage.make_change(ListAddController.wordAdd, meanAddIn.getText());
            Stage stage = (Stage) prev.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("listAdd.fxml"));
            stage.setScene(new Scene(root));
        } else {
            Stage stage = (Stage) prev.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("listAdd.fxml"));
            stage.setScene(new Scene(root));
        }
    }

    /** xoa tu add.*/
    @FXML
    void remove(ActionEvent event) throws SQLException, IOException {
        database_manage.user_delete(ListAddController.wordAdd, "user");
        Stage stage = (Stage) prev.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("listAdd.fxml"));
        stage.setScene(new Scene(root));
    }

    /**quay lai man hinh chinh .*/
    @FXML
    void prevController(MouseEvent event) throws IOException {
        Stage stage = (Stage) prev.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("listAdd.fxml"));
        stage.setScene(new Scene(root));
    }

    /**ham main.*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        engine = webAdd.getEngine();
        try {
            engine.loadContent(database_manage.user_search(ListAddController.wordAdd));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        wordAddIn.setText(ListAddController.wordAdd);
    }
}
