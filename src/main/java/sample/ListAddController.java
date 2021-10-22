package sample;

import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ListAddController implements Initializable {
    public static String wordAdd;
    private List<String> listWordAdd = new ArrayList<>();
    @FXML
    private JFXButton prev;

    @FXML
    private StackPane root;

    @FXML
    private ListView<String> listGroup;

    @FXML
    void prev(ActionEvent event) throws IOException {
        Stage stage = (Stage)prev.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("addWord.fxml"));
        stage.setScene(new Scene(root));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            listWordAdd = database_manage.get_list("user");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listGroup.getItems().addAll(listWordAdd);

        listGroup.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> obj, String oldVal, String newVal) {
                wordAdd = newVal;
                Stage stage = (Stage)listGroup.getScene().getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("makeWordAdd.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setScene(new Scene(root));
            }
        });
    }
}
