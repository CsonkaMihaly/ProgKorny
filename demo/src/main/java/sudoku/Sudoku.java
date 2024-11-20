package sudoku;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.util.Objects;
import java.util.Random;


public class Sudoku extends Application {

    private static int size = 9;
    private TextField[][] cells = new TextField[size][size];
    private  SudokuGenerator rboard;

    //cells=load(cells);



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        primaryStage.setTitle("Sodoku");

        BorderPane root = new BorderPane();

        GridPane gridPane = createGrid();
        VBox controlPanel = createControlPanel();

        root.setCenter(gridPane);
        root.setBottom(controlPanel);


        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private VBox createControlPanel() {
        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(9));
        controlPanel.setStyle("-fx-alignment: center;");

        Label messageLabel = new Label("Welcome to Sudoku!");
        messageLabel.setFont(new Font("Arial", 16));
        messageLabel.setStyle("-fx-text-fill: #333333;");

        Button validateButton = new Button("check");
        validateButton.setOnAction(e -> {
                    if (isValidGrid()) {
                        messageLabel.setText("Congratulations! You won!");
                        messageLabel.setStyle("-fx-text-fill: #1a6e1d;");
                    } else {
                        messageLabel.setText("Keep trying! There are errors.");
                        messageLabel.setStyle("-fx-text-fill: #d9201a;");
                    }
                });

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> resetGrid());

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> System.exit(0));

        styleButton(validateButton, "#1a6e1d", "#FFFFFF");
        styleButton(resetButton, "#d9201a", "#FFFFFF");
        styleButton(closeButton, "#000000", "#FFFFFF");

        HBox buttonBox = new HBox(10);
        buttonBox.setStyle("-fx-alignment: center;");
        buttonBox.getChildren().addAll(validateButton, resetButton, closeButton);


        controlPanel.getChildren().addAll(buttonBox, messageLabel);
        return controlPanel;
    }

    private void styleButton(Button button, String backgroundColor, String textColor) {
        button.setStyle(
                "-fx-background-color: " + backgroundColor + ";" +
                        "-fx-text-fill: " + textColor + ";" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;" +
                        "-fx-padding: 10 20 10 20;"
        );

        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #05096b;" +
                        "-fx-text-fill: " + textColor + ";" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;" +
                        "-fx-padding: 10 20 10 20;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: " + backgroundColor + ";" +
                        "-fx-text-fill: " + textColor + ";" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;" +
                        "-fx-padding: 10 20 10 20;"
        ));
    }

    private void resetGrid() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                TextField cell = cells[row][col];
                if (cell.isEditable()) {
                    cell.setText("");
                }
            }
        }
    }


    private GridPane createGrid() {
        Random rnd = new Random();
        SudokuGenerator rboard = new SudokuGenerator();
        int[][] t=rboard.getBoard();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                TextField cell = new TextField(String.valueOf(rnd.nextInt(10)));
                cell.setFont(new Font(18));
                cell.setPrefWidth(50);
                cell.setPrefHeight(50);
                cell.setStyle("-fx-alignment: center;");

                String borderStyle = "-fx-border-color: black; -fx-border-width: ";
                if (row % 3 == 0 && col % 3 != 0) borderStyle = "-fx-border-color: black; -fx-border-width: 2px 0px 0px 0px; "; // felső szegély
                if (col % 3 == 0 && row % 3 != 0) borderStyle = "-fx-border-color: black; -fx-border-width: 0px 0px 0px 2px; "; // bal szegély
                if (col % 3 == 0 && row % 3 == 0) borderStyle = "-fx-border-color: black; -fx-border-width: 2px 0px 0px 2px; "; // bal + felső szegély
                if (col % 3 == 2 && row % 3 != 0) borderStyle = "-fx-border-color: black; -fx-border-width: 0px 2px 0px 0px; "; // bal szegély
                if (row % 3 == 2 && col % 3 != 0) borderStyle = "-fx-border-color: black; -fx-border-width: 0px 0px 2px 0px; "; // bal szegély
                if (col % 3 == 2 && row % 3 == 2) borderStyle = "-fx-border-color: black; -fx-border-width: 0px 2px 2px 0px; "; // bal + felső szegély
                if (row % 3 == 0 && col % 3 == 2) borderStyle = "-fx-border-color: black; -fx-border-width: 2px 2px 0px 0px; "; // bal szegély
                if (row % 3 == 2 && col % 3 == 0) borderStyle = "-fx-border-color: black; -fx-border-width: 0px 0px 2px 2px; "; // bal szegély
                if (row % 3 == 1 && col % 3 == 1) borderStyle = "-fx-border-color: black; -fx-border-width: 0px 0px 0px 0px; "; // bal szegély

                cell.setStyle(cell.getStyle() + borderStyle);

                cell.textProperty().addListener((obs, oldValue, newValue) -> {
                    if (!newValue.matches("[1-9]?")) {
                        cell.setText(oldValue);
                    }
                });

                cell.setText( String.valueOf(t[row][col]));
                cells[row][col] = cell;
                int cellValue = t[row][col];
                if (cellValue == 0) {
                    cell.setStyle(cell.getStyle() + "-fx-background-color: lightgreen;");
                    cell.setText("");
                } else {
                    cell.setText(String.valueOf(cellValue));
                    cell.setEditable(false);
                }

                cell.setStyle(cell.getStyle() + borderStyle);
                gridPane.add(cell, col, row);
            }
        }

        return gridPane;
    }



    private  boolean isValidGrid(){
        boolean isValid = true;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                String value = cells[row][col].getText();
                if (value == null || value.trim().isEmpty()) {
                    return false;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            if (!isValidRow(i) || !isValidColumn(i)) {
                isValid = false;
                break;
            }
        }

        for (int row = 0; row < size; row += 3) {
            for (int col = 0; col < size; col += 3) {
                if (!isValidSubGrid(row, col)) {
                    isValid = false;
                    break;
                }
            }
        }

        if (isValid) {
            return true;
        } else {
            return false;
        }
    }


    private boolean isValidRow(int row) {
        boolean[] seen = new boolean[size];
        for (int col = 0; col < size; col++) {
            String value = cells[row][col].getText();
            if (!value.isEmpty() || (!Objects.equals(value, "0"))) {
                int num = Integer.parseInt(value) - 1;
                if (seen[num]) return false;
                seen[num] = true;
            }
        }
        return true;
    }

    private boolean isValidColumn(int col) {
        boolean[] seen = new boolean[size];
        for (int row = 0; row < size; row++) {
            String value = cells[row][col].getText();
            if (!value.isEmpty() || (Objects.equals(value, "0"))) {
                int num = Integer.parseInt(value) - 1;
                if (seen[num]) return false;
                seen[num] = true;
            }
        }
        return true;
    }

    private boolean isValidSubGrid(int startRow, int startCol) {
        boolean[] seen = new boolean[size];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                String value = cells[startRow + row][startCol + col].getText();
                if (!value.isEmpty()) {
                    int num = Integer.parseInt(value) - 1;
                    if (seen[num]) return false;
                    seen[num] = true;
                }
                if (value.isEmpty()) return false;

            }
        }
        return true;
    }
}







