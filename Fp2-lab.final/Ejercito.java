import java.io.Serializable;
import java.util.*;

public class Ejercito implements Serializable {
    private int fila;
    private String columna;
    private int columna2;
    private ArrayList<Soldado> misSoldados=new ArrayList<Soldado>();
    private int sumaDeVidas;
    private int numSoldados;
    private boolean player;
    private String reino;
    //datos generales
    public static int totalEjercitos;
    public static int totalEjercitos1;
    public static int totalEjercitos2;  
    public static final int maxEjercitos=3;
    public static int totalSoldados;
    public static int totalSoldados1;
    public static int totalSoldados2;  
    public static final int maxSoldados=10;
    public static String datoReino1;
    public static String datoReino2;


    //Métodos modificadores
    public Ejercito(){

    }
    public Ejercito(boolean player12){
        player=player12;
        int numDeEjercito;
        if(player){
            numSoldados=totalSoldados1;
            numDeEjercito=1;
            reino=datoReino1;
        }
        else{
            numSoldados=totalSoldados2;
            numDeEjercito=2;
            reino=datoReino2;
        }
        for(int i=0;i<numSoldados;i++){
            int numRandom=(((int) (Math.random() * 4)) + 1);
            Soldado soldadoParcial;
            if(numRandom==1){
                soldadoParcial=new Espadachin(player);
            }
            else if(numRandom==2){
                soldadoParcial=new Caballero(player);
            }
            else if(numRandom==3){
                soldadoParcial=new Lanzero(player);
            }
            else{
                soldadoParcial=new Arquero(player);
            }
            soldadoParcial.setNombre(soldadoParcial.getNombre()+i+"X"+numDeEjercito);
            misSoldados.add(soldadoParcial);
        }
        sumarVidas();
    }
    public static void setReinos(String reino1, String reino2){
        datoReino1=reino1;
        datoReino2=reino2;        
    }
   
    public void setFila(int f) {
        fila = f;
    }

    public void setColumna(String c) {
        columna = c;
    }

    public void setColumna2(int c2) {
        columna2 = c2;
    }

    public void addSoldado(Soldado soldado){
        misSoldados.add(soldado);
    }
    public void setArrayPrivate(ArrayList<Soldado> soldados){
        misSoldados=soldados;
    }

    public ArrayList<Soldado> getMisSoldados() {
        return misSoldados;
    }

    //Métodos accesores

    public int getFila() {
        return fila;
    }

    public String getColumna() {
        return columna;
    }

    public int getColumna2() {
        return columna2;
    }

    public int getNumSoldados(){
        return numSoldados;
    }
    public int getSumaVidas(){
        return sumaDeVidas;
    }

    public void sumarVidas(){
        sumaDeVidas=0;
        for(int i=0;i<misSoldados.size();i++){
            sumaDeVidas=sumaDeVidas+(misSoldados.get(i).getVida());
        }
    }
    //Métodos adicionales
    public ArrayList<Soldado> arregloAuxiliar(){
        ArrayList<Soldado> auxiliar=(ArrayList<Soldado>) misSoldados.clone();
        return auxiliar;
    }
    public void mostrarDatos(){
        for(int i=0;i<misSoldados.size();i++){
            misSoldados.get(i).mostrarDatos();
        }
    }
    public void ordenarEjercito(){
        Soldado.ordenarArray(misSoldados);
    }
}

