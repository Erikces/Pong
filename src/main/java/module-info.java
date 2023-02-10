module com.example.di_3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.di_3 to javafx.fxml;
    exports com.example.di_3;
}