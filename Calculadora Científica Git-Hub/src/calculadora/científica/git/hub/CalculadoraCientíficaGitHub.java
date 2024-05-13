package calculadora.científica.git.hub;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class CalculadoraCientíficaGitHub {

    public static void main(String[] args) {

        ArrayList<String> operacion = new ArrayList();
        ArrayList<String> numeros = new ArrayList();
        ArrayList<String> signos = new ArrayList();
        Scanner scan = new Scanner(System.in);
        int contador = 0;

        System.out.println(" *Ingrese toda la operacion en notacion polaca inversa, para finalizar ingrese =* ");
        System.out.println(" *Ingrese off para apagar la calculadora* ");
        operacion.add(scan.nextLine());

        if (operacion.get(0).equals("off")) {

            System.out.println(" *Sistema Apagado* ");

        } else {

            int i = 0;
            while (!operacion.get(i).equals("=")) {

                i++;
                operacion.add(scan.nextLine());

                if (esSigno(operacion.get(i))) {
                    signos.add(operacion.get(i));

                } else {

                    if (esNumero(operacion.get(i))) {
                        numeros.add(operacion.get(i));
                    }
                }
                
                //evaluarExpresion(numeros,signos);
            }
        }
    }
/*/
    public static double evaluarExpresion(ArrayList<String> numeros, ArrayList<String> signos) {

            if (esOperador(entrada)) {
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
                        numeros.push(num1 / num2);
                        break;
                    case "X^": 
                        numeros.push(Math.pow(num1, num2));
                        break;
                    case "V": 
                        numeros.push(Math.pow(num1, 1.0 / num2));
                        break;
                    default:
                        throw new IllegalArgumentException("Operador no válido: " + entrada);
                }
            } else {
                numeros.push(Double.parseDouble(token));
            }
        }
        return numeros.pop();
    }/*/

    public static boolean esSigno(String entrada) {
        return entrada.equals("+") || entrada.equals("-") || entrada.equals("x") || entrada.equals("/") || entrada.equals("X^") || entrada.equals("V");
    }

    public static boolean esNumero(String entrada) {
        return !entrada.equals("+") || !entrada.equals("-") || !entrada.equals("x") || !entrada.equals("/") || !entrada.equals("X^") || !entrada.equals("V");
    }
}
