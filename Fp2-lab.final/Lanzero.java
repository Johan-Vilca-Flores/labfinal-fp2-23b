public class Lanzero extends Soldado {
    //Atributos de la clase
    private int longLanza;

    public void atacar(Soldado objetivo) {
        int dañoAdicional = calcularDañoPorLongitudLanza(longLanza);
        // Aplicar el daño total al objetivo
        this.setAtaque(this.getAtaque() + dañoAdicional);
        objetivo.recibirDaño(this.getAtaque());
        // Aplicar las reglas especiales de combate
    }

    private int calcularDañoPorLongitudLanza(int longitud) {
        return longitud / 10;
    }

    // Métodos mutadores

    public void setLongLanza(int newL){
        longLanza=newL;
    }

    public Lanzero(boolean ejer){
        super("Lanzero",(((int) (Math.random() * 4)) + 5),ejer);
        this.setAtaque(6);
        this.setDefensa(3);
    }

    // Métodos accesores
    public int getLongLanza(){
        return longLanza;
    }

    //Otros métodos
    public void schiltrom(){
        this.setDefensa(this.getDefensa()+1);
    }
    
}
