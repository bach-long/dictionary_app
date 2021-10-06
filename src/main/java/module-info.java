module group1.learn_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires gtranslateapi;

    opens group1.learn_fx to javafx.fxml;
    exports group1.learn_fx;
}