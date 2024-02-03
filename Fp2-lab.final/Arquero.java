
public class Arquero extends Soldado {
    //Atributos de la clase
    public static final int ALCANCE_ATAQUE = 2; 
    private int numFlechas;
    public boolean tieneFlechas = true;

    public void atacar(Soldado objetivo) {
        this.setNumFlechas((int) (Math.random()*6) + 4);
        if (this.numFlechas > 0) {
            this.disparar();
            objetivo.recibirDaño(this.getAtaque());
        }
    }

    // Métodos mutadores

    public void setNumFlechas(int newN){
        numFlechas=newN;
    }

    public void recargarFlechas(int cantidad) {
        this.numFlechas += cantidad;
    }

    public Arquero(boolean ejer){
        super("Arquero",(((int) (Math.random() * 3)) + 3),ejer);
        this.setAtaque(9);
        this.setDefensa(2);
    }    

    // Métodos accesores
    public int getNumFlechas(){
        return numFlechas;
    }

    //Otros métodos
    public void disparar(){
        if(numFlechas!=0){
            numFlechas--;
        } else {
            tieneFlechas = false;
        }
    }
}
