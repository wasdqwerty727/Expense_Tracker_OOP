module com.example.expense_tracker_oop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.expense_tracker_oop to javafx.fxml;
    exports com.example.expense_tracker_oop;
}