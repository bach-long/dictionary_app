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
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ListGroupOut implements Initializable {
    public static String group;
    public List<String> arrayGroup = null;
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
    private ListView<String> listGroup;

    @FXML
    void addGroup(MouseEvent event) {
        dialog.show();
    }

    public static String getGroup(){
        return group;
    }

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
                group = newVal;
                Stage stage = (Stage)listGroup.getScene().getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("listMemoView.fxml"));
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
}
