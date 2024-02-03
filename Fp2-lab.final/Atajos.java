import java.util.ArrayList;
import java.util.Scanner;

public class Atajos {
    //colores
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String BLACK = "\u001B[30m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    //elegir opciones menu
    public static int elegirOpcion(String[]listaOpciones){
        Scanner scan=new Scanner(System.in);
        int i;
        do{
            System.out.println("seleciones una de las alternativas, ingresando 1 y "+listaOpciones.length+" según corresponda:");
            for(int j=0;j<listaOpciones.length;j++){
                System.out.println((j+1)+". "+listaOpciones[j]);
            }
            i=scan.nextInt();
        }
        while(i<1||i>listaOpciones.length);
        return (i-1);
    }
    //conversiones
    //letra a número
    public static int convertir(String letra){
        String[] letras = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
        for (int i = 0; i < letras.length; i++) {
            if (letra.equals(letras[i])) {
                return (i + 1);
            }
        }
        return -1;
    }
    //número a letra
    public static String convertir(int numero){
        String[] letras = {" ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
        return letras[numero];
    }
    //intercambio de soldados
    public static void intercambiar(ArrayList<Soldado>soldados, int i, int j) {
        Soldado temp = new Soldado();
        temp = soldados.get(i);
        soldados.set(i, soldados.get(j));
        soldados.set(j, temp);
    }
    //fila
    public static int fila() {
        Scanner scan = new Scanner(System.in);
        try {
            System.out.println("digite la fila:");
            return scan.nextInt();
        } catch (Exception e) {
            return fila();
        }  
    }
    
    //columna
    public static int columna() {
        Scanner scan = new Scanner(System.in);
        System.out.println("digite la columna:");
        return convertir(scan.next());
    }
    
}

