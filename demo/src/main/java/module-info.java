module Sodoku {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;

    opens com.example.demo to javafx.fxml;
        exports sudoku;
}


