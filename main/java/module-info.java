module com.example.jdk17_test {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.base;
    requires org.xerial.sqlitejdbc;
    requires javafx.web;

    opens com.example.jdk17_test to javafx.fxml;
    exports com.example.jdk17_test;
}