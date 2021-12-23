module com.example.peymanmoshfegh_comp228lab5 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;
    requires java.sql;
    requires java.sql.rowset;

    exports exercise1;
    opens exercise1 to javafx.fxml;
}