import java.io.Serializable;
import java.util.ArrayList;

public abstract class Soldado implements Serializable {

    //Atributos de la clase

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
    private int vida;
    private int ataque;
    private int defensa;
    private int fila;
    private String columna;
    private int columna2;
    private boolean ejercito;
    private String reino;
    private boolean esEspecial;
    
    public abstract void atacar(Soldado objetivo);

    // Métodos mutadores
    public Soldado() {

    }

    public Soldado(String no, int vi,boolean ejer) {
        this.nombre = no ;
        this.vida = vi;
        this.ejercito=ejer;
        if(ejer){
            this.reino=Ejercito.datoReino1;
        }
        else{
            this.reino=Ejercito.datoReino2;
        }
    }

    public void recibirDaño(int daño) {
        while (daño > 0 && defensa > 0) {
            defensa--;
            daño--;
        }

        vida -= daño;
        if (vida < 0) {
            vida = 0;
        }
    }

    public void setNombre(String n) {
        nombre = n;
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

    public void setVida(int v) {
        vida = v;
    }

    public void setDefensa(int def) {
        defensa = def;
    }

    public void setAtaque(int atq) {
        ataque = atq;
    }

    public void setEjercito(boolean b) {
        ejercito = b;
    }
    public void setEsEspecial(boolean es) {
        esEspecial = es;
    }
     
    //Métodos accesores

    public String getNombre() {
        return nombre;
    }

    public int getFila() {
        return fila;
    }

    public String getColumna() {
        return columna;
    }

    public int getVida() {
        return vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }
    

    public int getColumna2() {
        return columna2;
    }

    public boolean getEjercito() {
        return ejercito;
    }
    public String getReino(){
        return reino;
    }
    public boolean getEsEspecial() {
        return esEspecial;
    }

    // Otros métodos
    public void mostrarDatos(){
        if(ejercito){
            System.out.print(Atajos.BLUE);
        }
        else{
            System.out.print(Atajos.RED);
        }
        System.out.println("Nombre: "+nombre+"\tVida: "+vida+"\tReino: "+reino);
        System.out.print(Atajos.RESET);
    }
    //Mostrar un tablero bidimensional
    public static void imprimirTablero(Soldado[][]tablero){
        String vidaParcial;
         for (int fila = 0; fila < tablero.length; fila++) {
            for (int columna = 0; columna < tablero[fila].length; columna++) {         
                if ((tablero[fila][columna] != null)) {
                    vidaParcial=""+tablero[fila][columna].getVida();
                    if(tablero[fila][columna].getVida()<10){
                        vidaParcial="0"+vidaParcial;
                    } 
                        if (tablero[fila][columna].getEjercito()) {
                            System.out.print("|"+Atajos.BLUE+(tablero[fila][columna].getNombre()).substring(0,1)+vidaParcial+Atajos.RESET);
                        }
                        else {
                            System.out.print("|"+Atajos.RED+(tablero[fila][columna].getNombre()).substring(0,1)+vidaParcial+Atajos.RESET);
                        }
                }
                else {
                    if(fila==0){
                        if(columna==0){
                           System.out.print("  "+"\t "); 
                        }
                        else{
                            System.out.print(Atajos.convertir(columna)+"   ");
                        }
                    }
                    else if(columna==0){
                        System.out.print(fila+"\t");
                    }
                    else{
                        System.out.print("|___");
                    }
                }
            }
            if(fila!=0){
                System.out.println("|");
            }
            else{
                System.out.println("");
            }
        }  
    }
    //mostrar datos de un ArrayList
    public static void mostrarDatosSol(ArrayList<Soldado>soldados){
        for(int i=0;i<soldados.size();i++){
            soldados.get(i).mostrarDatos();
        }
    }
    //ordenar un ArrayListe
    public static void ordenarArray(ArrayList<Soldado>soldados){
        for (int i = 1; i < soldados.size(); i++) {
            for (int j = 0; j < soldados.size() - i; j++) {
                if ((soldados.get(j).getVida()) < (soldados.get(j+1).getVida())) {
                    Atajos.intercambiar(soldados, j, j + 1);
                }
            }
        }        
    }
    public static void aplicarBonus(ArrayList<Soldado>soldados, String territorio){
        String beneficio[]={"IB","FC","CM","MD","SB","SP","SC"};
        String rei1, rei2;
        boolean br1=false;
        boolean br2=false;
        rei1=soldados.get(0).getReino();
        rei2=soldados.get(soldados.size()-1).getReino();
        String reino1=rei1.substring(0,1)+territorio.substring(0, 1);
        String reino2=rei2.substring(0,1)+territorio.substring(0, 1);
        for(int i=0;i<beneficio.length;i++){
            if(reino1.equals(beneficio[i])){
                br1=true;
            }
            if(reino2.equals(beneficio[i])){
                br2=true;
            }
        }
        System.out.println("Enfrentamiento en el territorio "+territorio);
        if(br1){
            System.out.println(Atajos.CYAN+"Se aplicará un beneficio de territorio (en la vida) al reino de: "+rei1+Atajos.RESET);
        }
        if(br2){
            System.out.println(Atajos.CYAN+"Se aplicará un beneficio de territorio (en la vida) al reino de: "+rei2+Atajos.RESET);
        }                      
        for(int i=0;i<soldados.size();i++){       
            if(soldados.get(i).getEjercito()){
                if(br1){
                    System.out.println((soldados.get(i).getNombre())+"\t" +(soldados.get(i).getVida())+"+ 1= "+((soldados.get(i).getVida())+1));
                    soldados.get(i).setVida((soldados.get(i).getVida())+1);
                }
            }
            else{
                if(br2){
                    System.out.println((soldados.get(i).getNombre())+"\t" +(soldados.get(i).getVida())+"+ 1= "+((soldados.get(i).getVida())+1));
                    soldados.get(i).setVida((soldados.get(i).getVida())+1);
                }
            }         
        }
    }
    public static void reglas(Soldado soldado1, Soldado soldado2){
        String tipo1=soldado1.getNombre().substring(0,1);
        String tipo2=soldado2.getNombre().substring(0,1);
        if(tipo1.equals("C")){
            if(tipo2.equals("A")){
                soldado1.setVida(soldado1.getVida()+1);
                if(soldado1.getEsEspecial()){
                    soldado1.setVida(soldado1.getVida()+1);
                }
            }
            if(tipo2.equals("L")&&!soldado1.getEsEspecial()){
                soldado2.setVida(soldado2.getVida()+1);
            }
            if(tipo2.equals("E")){
                soldado1.setVida(soldado1.getVida()+1);
            }
            if(tipo2.equals("C")&&soldado2.getEsEspecial()){
                soldado2.setVida(soldado2.getVida()+1);
            }
        }
        if (tipo2.equals("C")) {
            if (tipo1.equals("A")) {
                soldado2.setVida(soldado2.getVida() + 1);
                if (soldado2.getEsEspecial()) {
                    soldado2.setVida(soldado2.getVida() + 1);
                }
            }
            if (tipo1.equals("L") && !soldado2.getEsEspecial()) {
                soldado1.setVida(soldado1.getVida() + 1);
            }
            if (tipo1.equals("E")) {
                soldado2.setVida(soldado2.getVida() + 1);
            }
            if (tipo1.equals("C") && soldado1.getEsEspecial()) {
                soldado1.setVida(soldado1.getVida() + 1);
            }
        }
        
        if(tipo1.equals("L")){
            if(tipo2.equals("A")){
                soldado2.setVida(soldado2.getVida()+1);
            }
            if(tipo2.equals("E")){
                soldado2.setVida(soldado2.getVida()+1);
            }
        }
        if (tipo2.equals("L")) {
            if (tipo1.equals("A")) {
                soldado1.setVida(soldado1.getVida() + 1);
            }
            if (tipo1.equals("E")) {
                soldado1.setVida(soldado1.getVida() + 1);
            }
        }
        if(tipo1.equals("E")){
            if(tipo2.equals("E")&&soldado2.getEsEspecial()){
                soldado2.setVida(soldado2.getVida()+1);
            }
        }
        if (tipo2.equals("E")) {
            if (tipo1.equals("E") && soldado1.getEsEspecial()) {
                soldado1.setVida(soldado1.getVida() + 1);
            }
        }
    }
}

