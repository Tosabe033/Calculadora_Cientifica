package calculadoracientifica;

import java.util.ArrayList;
import java.util.Stack;
import java.util.LinkedList;
import javax.swing.JOptionPane;

public class CalculadoraCientíficaGitHub {

    // Método para calcular la expresión ingresada
    public static void calcular(String cuenta) {
        ArrayList<String> operacion = new ArrayList<>();

        // Procesar la entrada de la cuenta
        String entrada = cuenta + "=";
        System.out.println(entrada);

        if (entrada.equals("off")) {
            System.out.println(" * Sistema Apagado * ");
        } else {
            // Agregar cada operando a la lista de operación
            operacion.add(entrada);
            System.out.println("Operación ingresada: " + operacion);

            // Convertir la operación a notación polaca inversa
            ArrayList<String> polacaInversa = pasarPolacaInversa(operacion);
            System.out.println("Polaca Inversa: " + polacaInversa);

            try {
                String resultado = evaluarExpresion(polacaInversa);
                System.out.println("Resultado: " + resultado);
                // Mostrar el resultado en la interfaz (actualmente en consola)
            } catch (Exception e) {
                // Mostrar mensaje de error si ocurre una excepción
                JOptionPane.showMessageDialog(null, "Error al evaluar la expresión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para convertir una operación en notación polaca inversa
    public static ArrayList<String> pasarPolacaInversa(ArrayList<String> operacion)  {
        Stack<String> signos = new Stack<>();
        ArrayList<String> numeros = new ArrayList<>();
        int contadorSigno = 0;
        boolean esNumNegativo = false;

        for (int i = 0; i < operacion.size(); i++) {
            String entrada = operacion.get(i);

            if (esNumero(entrada)) {
                if (esNumNegativo) {
                    numeros.add("-" + entrada); // Agrega el número como negativo
                    esNumNegativo = false;
                } else {
                    numeros.add(entrada);
                }
                contadorSigno = 0; // Reiniciar el contador de signos
            } else if (esSigno(entrada)) {
                // Verificar si el signo es un negativo en lugar de un operador
                contadorSigno++;
                if (contadorSigno > 1) {
                    //throw new Exception("Error: Dos operadores consecutivos no son válidos.");
                }
                if (entrada.equals("-") && (i == 0 || operacion.get(i - 1).equals("(") || esSigno(operacion.get(i - 1)))) {
                    esNumNegativo = true; // Activar el indicador de número negativo
                } else {
                    while (!signos.isEmpty() && prioridad(signos.peek()) >= prioridad(entrada)) {
                        numeros.add(signos.pop());
                    }
                    signos.push(entrada);
                    contadorSigno++;
                }
            } else if (entrada.equals("(")) {
                signos.push(entrada);
            } else if (entrada.equals(")")) {
                while (!signos.peek().equals("(")) {
                    numeros.add(signos.pop());
                }
                signos.pop(); // Retirar el paréntesis de apertura
            }
        }

        while (!signos.isEmpty()) {
            numeros.add(signos.pop());
        }

        return numeros;
    }

    // Método para evaluar la expresión en notación polaca inversa
    public static String evaluarExpresion(ArrayList<String> operacion) throws Exception {
        LinkedList<Double> numeros = new LinkedList<>();
        for (String entrada : operacion) {
            if (esSigno(entrada)) {
                if (numeros.size() < 2) {
                    throw new Exception("Faltan operandos para el operador " + entrada);
                }
                double num2 = numeros.pop();
                double num1 = numeros.pop();
                switch (entrada) {
                    case "+":
                        numeros.push(num1 + num2);
                        break;
                    case "-":
                        numeros.push(num1 - num2);
                        break;
                    case "x":
                        numeros.push(num1 * num2);
                        break;
                    case "/":
                        try{
                            numeros.push(num1 / num2);
                        } catch(Exception e){
                            throw new Exception("Error Matematico, division por 0");
                        }
                        break;
                    case "^":
                        numeros.push(Math.pow(num1, num2));
                        break;
                    case "V":
                        numeros.push(Math.pow(num2, 1.0 / num1));
                        break;
                    default:
                        throw new Exception("Operador no válido: " + entrada);
                }
            } else {
                try {
                    numeros.push(Double.valueOf(entrada));
                } catch (NumberFormatException e) {
                    throw new Exception("Número no válido: " + entrada);
                }
            }
        }

        if (numeros.isEmpty()) {
            throw new Exception("La expresión no tiene suficientes operandos o está vacía");
        }

        return String.valueOf(numeros.pop());
    }

    // Método para verificar si una cadena es un operador
    public static boolean esSigno(String entrada) {
        return entrada.equals("+") || entrada.equals("-") || entrada.equals("x") || entrada.equals("/") || entrada.equals("^") || entrada.equals("V");
    }

    // Método para verificar si una cadena es un número
    public static boolean esNumero(String entrada) {
        try {

            Double.parseDouble(entrada);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Método para determinar la prioridad de un operador
    public static int prioridad(String operador) {
        switch (operador) {
            case "+":
            case "-":
                return 1;
            case "x":
            case "/":
                return 2;
            case "^":
            case "V":
                return 3;
            default:
                return 0;
        }
    }
}
