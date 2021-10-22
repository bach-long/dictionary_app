package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchWordMemo implements Initializable {
    private int x;
    private List<String> listWord = new ArrayList<>();
    @FXML
    private AnchorPane anchor;

    @FXML
    private JFXButton prev;

    @FXML
    private AnchorPane web;

    @FXML
    private Label wordIn;

    @FXML
    private StackPane root;

    @FXML
    private JFXButton later;

    @FXML
    private JFXButton next;

    @FXML
    private JFXDialog dialog;

    @FXML
    void remove(ActionEvent event) {
        dialog.show();
    }

    @FXML
    private WebView html_view;
    public WebEngine engine = null;

    /**doc*/
    public void audioAction (ActionEvent event) throws SQLException {
        try {
            TextToSpeech speech = new TextToSpeech();
            speech.toSpeech(listWord.get(x));
        } catch(Exception e) {
            engine.loadContent("<h1>This word doesn't exist</h1>");
        }
    }

    @FXML
    void later(ActionEvent event) {
        if(x > 0) {
            x--;
        } else {
            x = listWord.size() - 1;
        }
        wordIn.setText(listWord.get(x));
        engine = html_view.getEngine();
        String[] s = new String[0];
        try {
            s = database_manage.search(listWord.get(x),"av");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (s[1] == null) {
            s[1] = "<h1 style=\"color:Tomato;\"> Chưa có góp ý </h1>";
        }
        String v = s[0] + "\n Góp ý " + s[1];
        if(s[0].equals("<h1 style=\"color:Tomato;\"> This word doesn't exist </h1>")) {
            try {
                s[0] = database_manage.user_search(listWord.get(x));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            v = s[0] + "\n Góp ý" + "<h1 style=\"color:Tomato;\"> Chưa có góp ý </h1>";
        }
        engine.loadContent(v);
    }

    @FXML
    void dec(ActionEvent event) {
        dialog.close();
    }

    @FXML
    void acpt(ActionEvent event) throws SQLException, IOException {
        database_manage.user_delete(listWord.get(x),ListGroupOut.getGroup());
        dialog.close();
        Stage stage = (Stage)next.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("ListMemoView.fxml"));
        stage.setScene(new Scene(root));
    }

    @FXML
    void next(ActionEvent event) {
        if(x < listWord.size() - 1) {
            x++;
        } else {
            x = 0;
        }
        wordIn.setText(listWord.get(x));
        engine = html_view.getEngine();
        String[] s = new String[0];
        try {
            s = database_manage.search(listWord.get(x),"av");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (s[1] == null) {
            s[1] = "<h1 style=\"color:Tomato;\"> Chưa có góp ý </h1>";
        }
        String v = s[0] + "\n Góp ý " + s[1];
        if(s[0].equals("<h1 style=\"color:Tomato;\"> This word doesn't exist </h1>")) {
            try {
                s[0] = database_manage.user_search(listWord.get(x));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            v = s[0] + "\n Góp ý" + "<h1 style=\"color:Tomato;\"> Chưa có góp ý </h1>";
        }
        engine.loadContent(v);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
        dialog.setDialogContainer(root);
        try {
            listWord = database_manage.get_list(ListGroupOut.group);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        x = ListMemoView.vitri;
        wordIn.setText(listWord.get(x));
        engine = html_view.getEngine();
        String[] s = new String[0];
        try {
            s = database_manage.search(listWord.get(x),"av");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (s[1] == null) {
            s[1] = "<h1 style=\"color:Tomato;\"> Chưa có góp ý </h1>";
        }
        String v = s[0] + "\n Góp ý " + s[1];
        if(s[0].equals("<h1 style=\"color:Tomato;\"> This word doesn't exist </h1>")) {
            try {
                s[0] = database_manage.user_search(listWord.get(x));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            v = s[0] + "\n Góp ý" + "<h1 style=\"color:Tomato;\"> Chưa có góp ý </h1>";
        }
        engine.loadContent(v);
        prev.setOnMouseClicked(MouseEvent->{
            Stage stage = (Stage)prev.getScene().getWindow();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("ListMemoView.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setScene(new Scene(root));
        });
    }
}
