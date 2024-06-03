package calculadora.científica.git.hub;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;

public class CalculadoraCientíficaGitHub {

    public static void main(String[] args) {

        ArrayList<String> operacion = new ArrayList<>();

        Scanner scan = new Scanner(System.in);
        int i = 0;

        System.out.println(" * Ingrese la operacion, para finalizar ingrese = * ");
        System.out.println(" * Ingrese off para apagar la calculadora* ");
        String entrada;
        entrada = scan.nextLine();

        if (entrada.equals("off")) {
            System.out.println(" * Sistema Apagado* ");
        } else {
            while (!entrada.equals("=")) {
                operacion.add(entrada);
                entrada = scan.nextLine();
            }

            System.out.println("Operación ingresada: " + operacion);
            // Mando operacion para cambiar a polaca y operar
            ArrayList<String> PolacaInversa = pasarPolacaInversa(operacion);
            System.out.println("Polaca Inversa: " + PolacaInversa);
            // Resuelvo el cálculo pasándole la operación en polaca
            String resultado = evaluarExpresion(PolacaInversa);
            System.out.println("Resultado: " + resultado);
            // Muestro el resultado de toda la operacion
        }
    }

    // Shunting Yard con prioridad de operadores
    public static ArrayList<String> pasarPolacaInversa(ArrayList<String> operacion) {
        Stack<String> signos = new Stack<>();
        ArrayList<String> numeros = new ArrayList<>();

        for (String entrada : operacion) { //foreach que recorra todo el array operacion
            if (esNumero(entrada)) {
                numeros.add(entrada);
            } else if (esSigno(entrada)) {
                while (!signos.isEmpty() && prioridad(signos.peek()) >= prioridad(entrada)) {
                    numeros.add(signos.pop());                                               
                }
                signos.push(entrada);
                //Si es un signo, agarra los dos primeros numeros y los opera segun el signo y pone el resultado en numeros
                
            } else if (entrada.equals("(")) {
                signos.push(entrada);
            } else if (entrada.equals(")")) {
                while (!signos.peek().equals("(")) {
                    numeros.add(signos.pop());
                }
                signos.pop(); 
            }
            //Cuando encuentra el '(' lo manda a signos y sigue hasta encontrar ')'
            //Cuando encuentra ')' se pasan los signos hasta '(' a numeros y los saca de signos
        }

        while (!signos.isEmpty()) {
            numeros.add(signos.pop());
        }
        // Comprueba que no haya quedado ningun signo, de ser asi lo manda directo a numeros
        return numeros;
    }

    public static String evaluarExpresion(ArrayList<String> operacion) {
        LinkedList<Double> numeros = new LinkedList<>();
        double op;

        for (String entrada : operacion) {
            if (esSigno(entrada)) {
                double num2 = numeros.pop();
                double num1 = numeros.pop();

                switch (entrada) {
                    case "+":
                        op = num1 + num2;
                        numeros.push(op);
                        break;
                    case "-":
                        op = num1 - num2;
                        numeros.push(op);
                        break;
                    case "x":
                        op = num1 * num2;
                        numeros.push(op);
                        break;
                    case "/":
                        op = num1 / num2;
                        numeros.push(op);
                        break;
                    case "X^":
                        op = Math.pow(num1, num2);
                        numeros.push(op);
                        break;
                    case "V":
                        op = Math.pow(num1, 1.0 / num2);
                        numeros.push(op);
                        break;
                    default:
                        System.out.println("Operador no válido: " + entrada);
                        operacion.add(entrada);
                }
            } else {
                try {
                    numeros.push(Double.valueOf(entrada));
                } catch (NumberFormatException e) {
                    System.out.println("Numero no válido: " + entrada);
                    operacion.add(entrada);
                }
            }
        }
        return String.valueOf(numeros.pop());
    }

    public static boolean esSigno(String entrada) {
        return entrada.equals("+") || entrada.equals("-") || entrada.equals("x") || entrada.equals("/") || entrada.equals("X^") || entrada.equals("V");
    }

    public static boolean esNumero(String entrada) {
        try {
            Double.parseDouble(entrada);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int prioridad(String operador) {
        switch (operador) {
            case "+":
            case "-":
                return 1;
            case "x":
            case "/":
                return 2;
            case "X^":
            case "V":
                return 3;
            default:
                return 0;
        }
    }
}

