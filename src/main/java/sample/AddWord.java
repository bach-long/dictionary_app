package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddWord implements Initializable {

    @FXML
    private JFXButton prev;

    @FXML
    private JFXButton add;

    @FXML
    private TextArea wordAddIn;

    @FXML
    private TextArea meanAddIn;

    @FXML
    private JFXDialog dialog;

    @FXML
    private StackPane root;

    /**mo danh danh add.*/
    @FXML
    void openListAdd(ActionEvent event) throws IOException {
        Stage stage = (Stage) prev.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("listAdd.fxml"));
        stage.setScene(new Scene(root));
    }

    /**quay lai man hinh chinh.*/
    @FXML
    void prevController(MouseEvent event) throws IOException {
        Stage stage = (Stage) prev.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage.setScene(new Scene(root));
    }

    /**them tu vua add.*/
    @FXML
    void add_new_Word(MouseEvent event) throws SQLException, IOException {
        String s = "";
        String m = "";
        s = wordAddIn.getText();
        m = meanAddIn.getText();

        if (database_manage.user_add(s, m)) {
            meanAddIn.setText("");
            wordAddIn.setText("");
            Stage stage = (Stage) prev.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("listAdd.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            dialog.show();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
        dialog.setDialogContainer(root);
    }
}
