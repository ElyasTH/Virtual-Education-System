module com.example.educationsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.educationsystem to javafx.fxml;
    exports com.example.educationsystem;
}