module org.example.testejavafxmaven {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens view to javafx.fxml;
    exports view;

    requires java.sql;
    requires com.h2database;
    requires java.desktop;
}