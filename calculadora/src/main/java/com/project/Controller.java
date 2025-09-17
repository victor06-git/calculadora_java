package com.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;


// Controlador de la calculadora: gestiona la lógica y la interfaz
public class Controller {


    // Botones de operaciones
    @FXML
    private Button buttonAdd, buttonMinus, buttonMult, buttonDiv;
    // Botones numéricos
    @FXML
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0;

    // Display principal
    @FXML
    private Text textCounter;

    // Display secundario para mostrar la operación (nuevo)
    @FXML
    private Text textOperation;

    // Primer número de la operación
    private double firstNumber = 0;
    // Operación seleccionada (+, -, *, /)
    private String operation = "";
    // Indica si se debe empezar a escribir un nuevo número
    private boolean start = true;
    // Guarda el último resultado para operaciones encadenadas
    private double lastResult = 0;
    // Indica si ya se ha realizado una operación antes
    private boolean hasResult = false;


    // Evento para botón suma
    @FXML
    private void addOperation(ActionEvent event) {
        setOperation("+");
    }

    // Evento para botón resta
    @FXML
    private void minusOperation(ActionEvent event) {
        setOperation("-");
    }

    // Evento para botón multiplicación
    @FXML
    private void multiplyOperation(ActionEvent event) {
        setOperation("*");
    }

    // Evento para botón división
    @FXML
    private void divideOperation(ActionEvent event) {
        setOperation("/");
    }


    // Establece la operación y gestiona operaciones encadenadas
    private void setOperation(String op) {
        double currentNumber = Double.parseDouble(textCounter.getText());
        // Si ya hay una operación previa, realiza el cálculo antes de continuar
        if (!operation.isEmpty() && !start) {
            int result = calculate(firstNumber, currentNumber, operation);
            textCounter.setText(String.valueOf(result));
            if (textOperation != null) {
                textOperation.setText((int)firstNumber + " " + operation + " " + (int)currentNumber);
            }
            firstNumber = result;
            lastResult = result;
            hasResult = true;
        } else {
            firstNumber = currentNumber;
        }
        operation = op;
        start = true;
        // Mostrar la operación actual aunque no se pulse igual
        if (textOperation != null) {
            textOperation.setText((int)firstNumber + " " + operation + " ");
        }
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


    // Añade un dígito al display
    private void appendNumber(String num) {
        if (start || textCounter.getText().equals("0")) {
            textCounter.setText(num);
            start = false;
        } else {
            textCounter.setText(textCounter.getText() + num);
        }
        showCurrentOperation();
    }


    // Calcula el resultado de la operación actual
    // Calcula el resultado de la operación actual y devuelve un int
    private int calculate(double a, double b, String op) {
        double result;
        switch (op) {
            case "+": result = a + b; break;
            case "-": result = a - b; break;
            case "*": result = a * b; break;
            case "/": result = (b != 0) ? a / b : Double.NaN; break;
            default: result = b;
        }
        return (int) result;
    }

    // Evento para el botón igual
    @FXML
    private void equalsOperation(ActionEvent event) {
        double secondNumber = Double.parseDouble(textCounter.getText());
        int result = calculate(firstNumber, secondNumber, operation);
        if (operation.equals("/") && secondNumber == 0) {
            textCounter.setText("Error");
            if (textOperation != null) {
                textOperation.setText((int)firstNumber + " " + operation + " " + (int)secondNumber);
            }
        } else {
            textCounter.setText(String.valueOf(result));
            if (textOperation != null) {
                textOperation.setText((int)firstNumber + " " + operation + " " + (int)secondNumber);
            }
        }
        firstNumber = result;
        lastResult = result;
        hasResult = true;
        start = true;
        operation = "";
    }


    // Limpia la calculadora
    @FXML
    private void clearOperation(ActionEvent event) {
        textCounter.setText("0");
        if (textOperation != null) textOperation.setText("");
        firstNumber = 0;
        operation = "";
        start = true;
        lastResult = 0;
        hasResult = false;
    }

    // Muestra la operación actual en el display secundario (si existe)
    private void showCurrentOperation() {
        if (textOperation != null) {
            if (!operation.isEmpty() && !start) {
                textOperation.setText(firstNumber + operation + textCounter.getText());
            } else if (!operation.isEmpty()) {
                textOperation.setText(firstNumber + operation);
            } else {
                textOperation.setText("");
            }
        }
    }
}
