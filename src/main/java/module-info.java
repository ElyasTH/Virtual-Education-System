module com.example.educationsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.educationsystem to javafx.fxml;
    exports com.example.educationsystem;
}