package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class transController implements Initializable {
    @FXML
    private TextArea input_prg;

    @FXML
    private WebView prg_view;

    public WebEngine engine = null;

    @FXML
    public Button prg;

    @FXML
    private Button prev;

    @FXML
    protected void prevhController(MouseEvent event) throws IOException {
        Stage stage = (Stage)prev.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage.setScene(new Scene(root));
    }

    public void trans_prg(ActionEvent event) throws IOException {
        String text = input_prg.getText();
        engine = prg_view.getEngine();
        String s = translator.translate("en", "vi", text);
        engine.loadContent(s);
    }

    public void audioAction (ActionEvent event) throws SQLException {
        try {
            TextToSpeech speech = new TextToSpeech();
            speech.toSpeech(input_prg.getText());
        } catch(Exception e) {
            engine.loadContent("<h1>This word doesn't exist</h1>");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
