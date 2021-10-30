package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ListMemoView implements Initializable {
    private String groupSave;
    public static String word;
    public static int vitri;
    @FXML
    private JFXButton prev;

    @FXML
    private ListView<String> listWord;

    private List<String> wordMemo;

    @FXML
    private JFXDialog dialog;

    @FXML
    private StackPane root;


    /**an button flash.*/
    @FXML
    void onFlashCard(ActionEvent event) throws IOException {
        Stage stage = (Stage) prev.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("flashCard.fxml"));
        stage.setScene(new Scene(root));
    }

    /**quay tro ve.*/
    @FXML
    void prevController(MouseEvent event) throws IOException {
        Stage stage = (Stage) prev.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("listGroupOut.fxml"));
        stage.setScene(new Scene(root));
    }

    /**acept tu.*/
    @FXML
    void acpt(ActionEvent event) throws SQLException, IOException {
        database_manage.delete_table(ListGroupOut.group);
        dialog.close();
        Stage stage = (Stage) prev.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("listGroupOut.fxml"));
        stage.setScene(new Scene(root));
    }

    /**dong dialog.*/
    @FXML
    void dec(ActionEvent event) {
        dialog.close();
    }

    /**mo dialog xoa.*/
    @FXML
    void removeGroup(ActionEvent event) {
        dialog.show();
    }

    /**ham chinh.*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
        dialog.setDialogContainer(root);
        groupSave = ListGroupOut.getGroup();
        //tao danh sach
        try {
            wordMemo = database_manage.get_list(groupSave);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //chon tu
        listWord.getItems().addAll(wordMemo);
        listWord.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                word = t1;
                vitri = listWord.getSelectionModel().getSelectedIndex();
                Stage stage = (Stage) listWord.getScene().getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("SearchWordMemo.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setScene(new Scene(root));
            }
        });
    }
}
