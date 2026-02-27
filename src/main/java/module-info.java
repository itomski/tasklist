module de.lubowiecki {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens de.lubowiecki to javafx.fxml;
    exports de.lubowiecki;
}
