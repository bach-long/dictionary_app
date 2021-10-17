module sample {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;

    requires org.controlsfx.controls;
    requires javafx.graphics;
    requires com.jfoenix;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires freetts;

    opens sample to javafx.fxml;
    exports sample;
}