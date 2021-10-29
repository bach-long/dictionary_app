package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListGroup implements Initializable {
    public List<String> arrayGroup = new ArrayList<>();

    @FXML
    private JFXButton prev;

    @FXML
    private StackPane root;

    @FXML
    private JFXDialog dialog;

    @FXML
    private JFXButton acpt;

    @FXML
    private JFXButton dec;

    @FXML
    private JFXButton addGroup;

    @FXML
    private TextArea nameGroup;

    @FXML
    private ListView<String> listGroup1;

    @FXML
    private ListView<String> listGroup;

    @FXML
    void addGroup(MouseEvent event) {
        dialog.show();
    }

    @FXML
    private ContextMenu listContexMenu;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
        dialog.setDialogContainer(root);
        dec.setOnMouseClicked(MouseEvent ->{
            dialog.close();
        });

        try {
            arrayGroup = database_manage.get_list("name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 4; i < arrayGroup.size(); i++) {
            listGroup.getItems().add(arrayGroup.get(i));
        }
        //listGroup.getItems().addAll(arrayGroup);
        acpt.setOnMouseClicked(MouseEvent ->{
            String s = nameGroup.getText();
            try {
                database_manage.add_group(s);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            listGroup.getItems().add(s);
            dialog.close();
        });


        listGroup.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> obj, String oldVal, String newVal) {
                try {
                    database_manage.add_memo(HelloController.wordMemoAdd, newVal);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Stage stage = (Stage)prev.getScene().getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setScene(new Scene(root));
            }
        });

        prev.setOnMouseClicked(MouseEvent->{
            Stage stage = (Stage)prev.getScene().getWindow();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setScene(new Scene(root));
        });
    }
    public void deleteItem() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete todo item");
        alert.setHeaderText("Delete item: ");
        alert.setContentText("Are you sure? Press OK to confirm, or cancel to Back out.");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && (result.get() == ButtonType.OK)){
            //database_manage.delete_table();
        }
    }

}
