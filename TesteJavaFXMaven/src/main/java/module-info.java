module org.example.testejavafxmaven {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens view to javafx.fxml;
    opens modelo to javafx.base;
    exports view;
    exports modelo;

    requires java.sql;
    requires com.h2database;
    requires java.desktop;
}