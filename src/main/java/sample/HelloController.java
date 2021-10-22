package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;

import java.io.IOException;

import java.net.URISyntaxException;
import java.net.URL;
import java.net.URI;
import java.sql.SQLException;
import java.util.*;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.awt.*;
import javafx.util.Duration;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

public class HelloController implements Initializable {
    private static String namePerson = "Person";
    public static String wordMemoAdd;
    public  boolean an = false;
    public static String nameTable = "av";

    public static String[] listWord = null;
    public static String[] lang = {"Anh-Viet", "Viet-Anh"};

    public static void getListWord() throws SQLException {
        listWord = database_manage.list_word();
    }

    @FXML
    public TextField tfinput;
    private AutoCompletionBinding<String> autoCompletionBinding;
    Set<String> psWord = new HashSet<>();

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    public Button bt;

    @FXML
    private Label name, wordIn;

    @FXML
    public AnchorPane anchor, slider, web;

    @FXML
    private JFXHamburger h1;

    @FXML
    private JFXButton dec, acpt, login, next, memo, ListMemo, searchOnline, send;

    @FXML
    private JFXDialog dialogLogin;

    @FXML
    public WebView html_view;

    @FXML
    private VBox bottomBox;

    @FXML
    private TextField inName, boxComment;

    @FXML
    private StackPane root;

    public WebEngine engine = null;

    @FXML
    void gopY(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.facebook.com/anh.tranthe.98622"));
    }

    @FXML
    void acpt(MouseEvent event) {
        namePerson = inName.getText();
        name.setText(namePerson);
        dialogLogin.close();
    }


    @FXML
    void searchOnGG(ActionEvent event) throws URISyntaxException, IOException {
       Desktop.getDesktop().browse(new URI("https://translate.google.com"));
    }

    @FXML
    void switchToScene1(MouseEvent event) throws IOException {
        Stage stage = (Stage)next.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("trans.fxml"));
        stage.setScene(new Scene(root));
    }

    @FXML
    void addWord(MouseEvent event) throws IOException {
        Stage stage = (Stage)next.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("AddWord.fxml"));
        stage.setScene(new Scene(root));
    }


    public void submit(ActionEvent event) throws SQLException {
        String input = tfinput.getText();
        wordMemoAdd = input;
        engine = html_view.getEngine();
        wordIn.setText(input);
        try {
            an = true;
            bottomBox.setVisible(true);
            memo.setVisible(true);
            String[] s = database_manage.search(input,nameTable);
            if (s[1] == null) {
                s[1] = "<h1 style=\"color:Tomato;\"> Chưa có góp ý </h1>";
            }
            String v = s[0] + "\n Góp ý " + s[1];
            if(s[0].equals("<h1 style=\"color:Tomato;\"> This word doesn't exist </h1>")) {
                s[0] = database_manage.user_search(input);
                v = s[0] + "\n Góp ý" + "<h1 style=\"color:Tomato;\"> Chưa có góp ý </h1>";
            }
            engine.loadContent(v);
        } catch(Exception e) {
            engine.loadContent("<h1>This word doesn't exist</h1>");
        }
    }

    /**doc*/
    public void audioAction (ActionEvent event) throws SQLException {
        try {
            TextToSpeech speech = new TextToSpeech();
            speech.toSpeech(tfinput.getText());
        } catch(Exception e) {
            engine.loadContent("<h1>This word doesn't exist</h1>");
        }
    }

    /**buttonMemoAdd.*/
    @FXML
    void memo(MouseEvent event) throws IOException {
        Stage stage = (Stage)memo.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("listGroup.fxml"));
        stage.setScene(new Scene(root));
    }

    /**ViewMemoAdd.*/
    @FXML
    void openList(MouseEvent event) throws IOException {
        Stage stage = (Stage)ListMemo.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("listGroupOut.fxml"));
        stage.setScene(new Scene(root));
    }

    @FXML
    void sendComment(ActionEvent event) {
            String c = namePerson + ": "+boxComment.getText() + "\n";
            try {
                database_manage.comment(c,tfinput.getText(),nameTable);
                submit(event);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }


    @Override
    public void initialize(URL url, ResourceBundle rsc) {
        bottomBox.setVisible(an);
        memo.setVisible(an);

        /**chon ngon ngu.*/
        choiceBox.getItems().addAll(lang);
        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String language = t1;
                if (language == "Viet-Anh") {
                    nameTable = "va";
                } else {
                    nameTable = "av";
                }
            }
        });

        /**Login.*/
        dialogLogin.setTransitionType(JFXDialog.DialogTransition.BOTTOM);
        dialogLogin.setDialogContainer(root);
        dec.setOnMouseClicked(MouseEvent ->{
            dialogLogin.close();
        });

        login.setOnMouseClicked(MouseEvent->{
            dialogLogin.show();
        });

        try {
            getListWord();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /**Hoan tu dong, goi y.*/
        Collections.addAll(psWord, listWord);
        autoCompletionBinding = TextFields.bindAutoCompletion(tfinput,psWord);
        tfinput.setOnKeyPressed((KeyEvent e) -> {
            switch (e.getCode()) {
                case ENTER:
                    learnWord(tfinput.getText());
                    String input = tfinput.getText();
                    wordMemoAdd = input;
                    engine = html_view.getEngine();
                    wordIn.setText(input);
                    try {
                        an = true;
                        bottomBox.setVisible(true);
                        memo.setVisible(true);
                        String[] s = database_manage.search(input,nameTable);
                        if (s[1] == null) {
                            s[1] = "<h1 style=\"color:Tomato;\"> Chưa có góp ý </h1>";
                        }
                        String v = s[0] + "\n Góp ý " + s[1];
                        if(s[0].equals("<h1 style=\"color:Tomato;\"> This word doesn't exist </h1>")) {
                            s[0] = database_manage.user_search(input);
                            v = s[0] + "\n Góp ý" + "<h1 style=\"color:Tomato;\"> Chưa có góp ý </h1>";
                        }
                        engine.loadContent(v);
                    } catch(Exception e1) {
                        engine.loadContent("<h1>This word doesn't exist</h1>");
                    }
                    break;
                default:
                    break;
            }
        });

        /**them cua so.*/
        HamburgerNextArrowBasicTransition burgerTask = new HamburgerNextArrowBasicTransition(h1);
        burgerTask.setRate(-1);
        double leng = slider.getHeight();
        slider.setTranslateX(-200);
        web.setTranslateX(-100);
        h1.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
            burgerTask.setRate(burgerTask.getRate()*-1);
            burgerTask.play();

            if(burgerTask.getRate() == -1) {
                /**mo.*/
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);
            slide.setToX(-200);
            slide.play();
            slider.setTranslateX(0);

            TranslateTransition slide2 = new TranslateTransition();
            slide2.setDuration(Duration.seconds(0.4));
            slide2.setNode(web);
            slide2.setToX(-100);
            slide2.play();
            web.setTranslateX(0);
            } else {

                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(0.4));
                slide.setNode(slider);
                slide.setToX(0);
                slide.play();
                slider.setTranslateX(-200);

                TranslateTransition slide2 = new TranslateTransition();
                slide2.setDuration(Duration.seconds(0.4));
                slide2.setNode(web);
                slide2.setToX(0);
                slide2.play();
                web.setTranslateX(-100);
                /** dong */

            }
        });


    }

    /**hoc tu.*/
    private void learnWord(String text) {
        psWord.add(text);
        if(autoCompletionBinding != null){
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(tfinput, psWord);
    }

}