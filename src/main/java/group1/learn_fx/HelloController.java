package group1.learn_fx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import dictionary.database_manage;
import dictionary.translator;


import com.gtranslate.Audio;
import com.gtranslate.Language;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class HelloController implements Initializable {
    String globe = "";

    @FXML
    public TextField tfinput;

    @FXML
    public Button bt;

    @FXML
    public AnchorPane anchor;

    @FXML
    public Label tf_label;

    @FXML
    public WebView html_view;

    @FXML
    public TextArea input_prg;

    @FXML
    public WebView prg_view;

    @FXML
    public Button prg;

    @FXML
    public Button speak;

    public WebEngine engine = null;

    public void submit(ActionEvent event) throws SQLException {
        try {
            String input = tfinput.getText();
            engine = html_view.getEngine();
            String s = database_manage.search(input);
            engine.loadContent(s);
            globe = input;
        } catch(Exception e) {
            engine.loadContent("<h1>This word doesn't exist</h1>");
        }
    }

    public void handle(ActionEvent event) {
        if(globe == ""){}
        else {
            try {
                InputStream sound = null;
                Audio audio = Audio.getInstance();
                sound = audio.getAudio("globe", Language.ENGLISH);
                audio.play(sound);
                System.out.println("globe");
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public void trans_prg(ActionEvent event) throws IOException {
        String text = input_prg.getText();
        engine = prg_view.getEngine();
        String s = translator.translate("en", "vi", text);
        engine.loadContent(s);
    }

    @Override
    public void initialize(URL url, ResourceBundle rsc) {

    }
}