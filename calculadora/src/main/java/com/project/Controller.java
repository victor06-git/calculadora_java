package com.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

public class Controller {

    @FXML
    private Button buttonAdd, buttonMinus, buttonMult, buttonDiv;
    @FXML
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0;

    @FXML
    private Text textCounter;

    private double firstNumber = 0;
    private String operation = "";
    private boolean start = true;

    @FXML
    private void addOperation(ActionEvent event) {
        setOperation("+");
    }

    @FXML
    private void minusOperation(ActionEvent event) {
        setOperation("-");
    }

    @FXML
    private void multiplyOperation(ActionEvent event) {
        setOperation("*");
    }

    @FXML
    private void divideOperation(ActionEvent event) {
        setOperation("/");
    }

    private void setOperation(String op) {
        String display = textCounter.getText();
        // Si ya hay una operación y un segundo número, calcula el resultado antes de continuar
        if (display.matches(".*[+\\-*/] .+")) {
            String[] parts = display.split(" ");
            if (parts.length == 3) {
                double secondNumber;
                try {
                    secondNumber = Double.parseDouble(parts[2]);
                } catch (Exception e) {
                    textCounter.setText("Error");
                    start = true;
                    operation = "";
                    return;
                }
                double result = 0;
                switch (operation) {
                    case "+": result = firstNumber + secondNumber; break;
                    case "-": result = firstNumber - secondNumber; break;
                    case "*": result = firstNumber * secondNumber; break;
                    case "/":
                        if (secondNumber != 0) result = firstNumber / secondNumber;
                        else {
                            textCounter.setText("Error");
                            start = true;
                            operation = "";
                            return;
                        }
                        break;
                }
                firstNumber = result;
            }
        } else {
            firstNumber = Double.parseDouble(display);
        }
        operation = op;
        textCounter.setText(firstNumber + " " + operation + " ");
        start = true;
    }

    // Métodos para los botones numéricos
    @FXML
    private void add1(ActionEvent event) { appendNumber("1"); }
    @FXML
    private void add2(ActionEvent event) { appendNumber("2"); }
    @FXML
    private void add3(ActionEvent event) { appendNumber("3"); }
    @FXML
    private void add4(ActionEvent event) { appendNumber("4"); }
    @FXML
    private void add5(ActionEvent event) { appendNumber("5"); }
    @FXML
    private void add6(ActionEvent event) { appendNumber("6"); }
    @FXML
    private void add7(ActionEvent event) { appendNumber("7"); }
    @FXML
    private void add8(ActionEvent event) { appendNumber("8"); }
    @FXML
    private void add9(ActionEvent event) { appendNumber("9"); }
    @FXML
    private void add0(ActionEvent event) { appendNumber("0"); }

    private void appendNumber(String num) {
        // Corregir la expresión regular para Java: los caracteres +, -, *, / deben ir precedidos de doble barra
        String regex = ".*[+\\-*/] ?$";
        if (start || textCounter.getText().equals("0") || textCounter.getText().matches(regex)) {
            if (textCounter.getText().matches(regex)) {
                textCounter.setText(textCounter.getText() + num);
            } else {
                textCounter.setText(num);
            }
            start = false;
        } else {
            textCounter.setText(textCounter.getText() + num);
        }
    }

    // Método para el botón igual
    @FXML
    private void equalsOperation(ActionEvent event) {
        String display = textCounter.getText();
        String[] parts = display.split(" ");
        double secondNumber;
        if (parts.length == 3) {
            // formato: "num op num"
            try {
                secondNumber = Double.parseDouble(parts[2]);
            } catch (Exception e) {
                textCounter.setText("Error");
                start = true;
                operation = "";
                return;
            }
        } else {
            // Si el usuario no ha pulsado operación, usar el display
            secondNumber = Double.parseDouble(display);
        }
        double result = 0;
        switch (operation) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    textCounter.setText("Error");
                    start = true;
                    operation = "";
                    return;
                }
                break;
            default:
                result = secondNumber;
        }
        textCounter.setText(firstNumber + " " + operation + " " + secondNumber + " = " + result);
        start = true;
        operation = "";
    }

    // Método para limpiar
    @FXML
    private void clearOperation(ActionEvent event) {
        textCounter.setText("0");
        firstNumber = 0;
        operation = "";
        start = true;
    }
}
