package com.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

// Controlador de la calculadora: gestiona la lógica y la interfaz
public class Controller {

    // Botones de operaciones aritméticas (suma, resta, multiplicación, división)
    @FXML
    private Button buttonAdd, buttonMinus, buttonMult, buttonDiv;
    
    // Botones numéricos (del 0 al 9)
    @FXML
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0;

    // Display principal: muestra el número actual o el resultado
    @FXML
    private Text textCounter;

    // Display secundario para mostrar la operación en curso (por ejemplo, "5 + 3")
    @FXML
    private Text textOperation;

    // Variables de estado para la lógica de la calculadora
    // Primer número de la operación (se establece al presionar un operador)
    private double firstNumber = 0;
    // Operación seleccionada (+, -, *, /)
    private String operation = "";
    // Indica si se debe empezar a escribir un nuevo número (después de un operador o resultado)
    private boolean start = true;
    // Guarda el último resultado para operaciones encadenadas (por ejemplo, 5 + 3 = 8, luego + 2)
    private double lastResult = 0;
    // Indica si ya se ha realizado una operación antes (para manejar encadenamientos)
    private boolean hasResult = false;

    // Evento para el botón de suma: establece la operación como "+"
    @FXML
    private void addOperation(ActionEvent event) {
        setOperation("+");
    }

    // Evento para el botón de resta: establece la operación como "-"
    @FXML
    private void minusOperation(ActionEvent event) {
        setOperation("-");
    }

    // Evento para el botón de multiplicación: establece la operación como "*"
    @FXML
    private void multiplyOperation(ActionEvent event) {
        setOperation("*");
    }

    // Evento para el botón de división: establece la operación como "/"
    @FXML
    private void divideOperation(ActionEvent event) {
        setOperation("/");
    }

    // Método privado que establece la operación actual y maneja operaciones encadenadas
    // Si ya hay una operación en curso, calcula el resultado intermedio antes de cambiar
    private void setOperation(String op) {
        double currentNumber = Double.parseDouble(textCounter.getText());
        // Si ya hay una operación previa y no estamos empezando un nuevo número, realiza el cálculo
        if (!operation.isEmpty() && !start) {
            int result = calculate(firstNumber, currentNumber, operation);
            textCounter.setText(String.valueOf(result));
            // Actualiza el display secundario con la operación realizada
            if (textOperation != null) {
                textOperation.setText((int)firstNumber + " " + operation + " " + (int)currentNumber);
            }
            firstNumber = result;
            lastResult = result;
            hasResult = true;
        } else {
            // Si no hay operación previa, solo guarda el número actual como primero
            firstNumber = currentNumber;
        }
        operation = op;  // Actualiza la operación seleccionada
        start = true;    // Prepara para ingresar el siguiente número
        // Muestra la operación actual en el display secundario (incompleta, esperando el segundo número)
        if (textOperation != null) {
            textOperation.setText((int)firstNumber + " " + operation + " ");
        }
    }

    // Métodos para los botones numéricos: cada uno llama a appendNumber con el dígito correspondiente
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

    // Método privado que añade un dígito al display principal
    // Si estamos empezando un nuevo número o el display es "0", lo reemplaza; de lo contrario, lo concatena
    private void appendNumber(String num) {
        if (start || textCounter.getText().equals("0")) {
            textCounter.setText(num);
            start = false;  // Ya no estamos empezando un nuevo número
        } else {
            textCounter.setText(textCounter.getText() + num);
        }
        // Actualiza el display secundario para mostrar la operación en tiempo real
        showCurrentOperation();
    }

    // Método privado que calcula el resultado de una operación dada (devuelve un entero)
    // Maneja las cuatro operaciones básicas; para división por cero, devuelve NaN (pero se maneja en equalsOperation)
    private int calculate(double a, double b, String op) {
        double result;
        switch (op) {
            case "+": result = a + b; break;
            case "-": result = a - b; break;
            case "*": result = a * b; break;
            case "/": result = (b != 0) ? a / b : Double.NaN; break;  // Evita división por cero
            default: result = b;
        }
        return (int) result;  // Convierte a entero (puede truncar decimales)
    }

    // Evento para el botón de igual (=): realiza el cálculo final y muestra el resultado
    @FXML
    private void equalsOperation(ActionEvent event) {
        double secondNumber = Double.parseDouble(textCounter.getText());
        int result = calculate(firstNumber, secondNumber, operation);
        // Maneja el caso especial de división por cero
        if (operation.equals("/") && secondNumber == 0) {
            textCounter.setText("Error");
            // Muestra la operación que causó el error en el display secundario
            if (textOperation != null) {
                textOperation.setText((int)firstNumber + " " + operation + " " + (int)secondNumber);
            }
        } else {
            // Muestra el resultado en el display principal
            textCounter.setText(String.valueOf(result));
            // Actualiza el display secundario con la operación completa
            if (textOperation != null) {
                textOperation.setText((int)firstNumber + " " + operation + " " + (int)secondNumber);
            }
        }
        // Actualiza el estado para operaciones encadenadas
        firstNumber = result;
        lastResult = result;
        hasResult = true;
        start = true;  // Prepara para un nuevo número
        operation = "";  // Limpia la operación actual
    }

    // Evento para el botón de limpiar (C): resetea toda la calculadora a su estado inicial
    @FXML
    private void clearOperation(ActionEvent event) {
        textCounter.setText("0");  // Display principal a cero
        if (textOperation != null) textOperation.setText("");  // Limpia display secundario
        firstNumber = 0;
        operation = "";
        start = true;
        lastResult = 0;
        hasResult = false;
    }

    // Método privado que actualiza el display secundario para mostrar la operación en tiempo real
    // Se llama cada vez que se añade un número, para reflejar el progreso (por ejemplo, "5 + 3")
    private void showCurrentOperation() {
        if (textOperation != null) {
            if (!operation.isEmpty() && !start) {
                // Si hay operación y ya se ha ingresado el segundo número
                textOperation.setText(firstNumber + operation + textCounter.getText());
            } else if (!operation.isEmpty()) {
                // Si hay operación pero aún no se ha ingresado el segundo número
                textOperation.setText(firstNumber + operation);
            } else {
                // Si no hay operación en curso, limpia el display secundario
                textOperation.setText("");
            }
        }
    }
}