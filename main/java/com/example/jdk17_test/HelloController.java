package com.example.jdk17_test;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ListView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    private static String keyWord;

    @FXML
    private TextField wordIn;

    @FXML
    private Button buttomSearch;

    @FXML
    private ListView<String> listView;

    @FXML
    private Label wordsOut;

    @FXML
    private Button sound;

    @FXML
    private Button add;

    @FXML
    private Button pin;

    @FXML
    private TextArea meanView;

    @FXML
    private WebView webView;

    WebEngine webEngine = null;

    @FXML
    void showWord(MouseEvent event) {
        keyWord = wordIn.getText();
        wordsOut.setText(keyWord);
        System.out.println(keyWord);
        try {
            String a = keyWord;
            webEngine.loadContent(database_manage.search(a));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void EnterSearch(KeyEvent event)  {
        if(event.getCode() == KeyCode.ENTER){
            keyWord = wordIn.getText();
            wordsOut.setText(keyWord);
            System.out.println(keyWord);
            try {
                String a = keyWord;
                webEngine.loadContent(database_manage.search(a));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void KeyReturn(KeyEvent event) {
        listView.getItems().clear();
        /** truyen List ARRY thay the cho WordIn.getText() */
        /**Tham so dau vao cua ham search la wordIn.getText(). */
        listView.getItems().addAll(wordIn.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView.getEngine();
        database_manage.set_database();
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                keyWord = listView.getSelectionModel().getSelectedItem();
                wordsOut.setText(keyWord);
            }

        });

    }
}