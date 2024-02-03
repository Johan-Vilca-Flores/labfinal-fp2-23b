
public class Espadachin extends Soldado {

    //Atributos de la clase

    private int longEspada;
    public boolean esConquistador;
    public boolean esReal;
    public boolean esTeutonico;
    private String tag;

    public void atacar(Soldado objetivo) {
        int dañoBase = this.getAtaque();
        int dañoAdicional = 0;

        // Longitud de la espada
        if (esConquistador) {
            longEspada = 100;
        } else if (esReal) {
            longEspada = 115;
        } else if (esTeutonico) {
            longEspada = 130;
        }

        // Considerar la longitud de la espada para aumentar el daño
        dañoAdicional += calcularDañoPorLongitudEspada(longEspada);

        // Aplicar el daño total al objetivo
        int dañoTotal = dañoBase + dañoAdicional;
        this.setAtaque(dañoTotal);
        objetivo.recibirDaño(this.getAtaque());
    }

    private int calcularDañoPorLongitudEspada(int longitudEspada) {
        if (longitudEspada <= 100) {
            return 2;
        } else if (longitudEspada <= 120) {
            return 3;
        } else {
            return 4;
        }
    }

    // Métodos mutadores

    public void setLongEspada(int newL){
        longEspada=newL;
    }

    public Espadachin(boolean ejer){
        super("Espadachin",(((int) (Math.random() * 3)) + 8),ejer);
        this.setAtaque(7);
        this.setDefensa(2);
        if(this.getVida()>=9){
            this.setEsEspecial(true);
            if(this.getReino().equals("Castilla Aragón ")){
                this.setNombre("Espadachin Conquistador");
                this.setVida(14);
                this.esConquistador=true;
                this.tag="EC";
            }
            if(this.getReino().equals("Inglaterra")){
                this.setNombre("Espadachin Real");
                this.setVida(12);
                this.esReal=true;
                this.tag="ER";
            }
            if(this.getReino().equals("Sacro Imperio Romano Germánico")){
                this.setNombre("Espadachin Teutonico");
                this.setVida(13);
                this.esTeutonico=true;
                this.tag="ET";
            }                      
        }
    }

    // Métodos accesores
    public int getLongEspada(){
        return longEspada;
    }
    public String getTag(){
        return tag;
    }    
}

