package sample;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import javafx.scene.shape.Circle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

public class HelloController implements Initializable {
    String globe = "";
    public static String[] listWord = null;

    public static void getListWord() throws SQLException {
        listWord = database_manage.list_word();
    }

    @FXML
    public TextField tfinput;
    private AutoCompletionBinding<String> autoCompletionBinding;
    Set<String> psWord = new HashSet<>();

    @FXML
    public Button bt;

    @FXML
    private Label wordIn;

    @FXML
    public AnchorPane anchor;


    @FXML
    public WebView html_view;

    @FXML
    private Button next;

    public WebEngine engine = null;

    @FXML
    protected void switchToScene1(MouseEvent event) throws IOException {
        Stage stage = (Stage)next.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("trans.fxml"));
        stage.setScene(new Scene(root));
    }

    public void submit(ActionEvent event) throws SQLException {
        try {
            String input = tfinput.getText();
            wordIn.setText(input);
            engine = html_view.getEngine();
            String s = database_manage.search(input);
            engine.loadContent(s);
        } catch(Exception e) {
            engine.loadContent("<h1>This word doesn't exist</h1>");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rsc) {
        try {
            getListWord();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.addAll(psWord, listWord);
        autoCompletionBinding = TextFields.bindAutoCompletion(tfinput,psWord);
        tfinput.setOnKeyPressed((KeyEvent e) -> {
            switch (e.getCode()) {
                case ENTER:
                    learnWord(tfinput.getText());
                    try {
                        engine.loadContent(database_manage.search(tfinput.getText()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        });
    }

    private void learnWord(String text) {
        psWord.add(text);
        if(autoCompletionBinding != null){
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(tfinput, psWord);
    }

}