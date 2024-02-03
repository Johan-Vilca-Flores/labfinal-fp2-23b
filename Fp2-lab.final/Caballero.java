public class Caballero extends Soldado {
    //Atributos de la clase
    private boolean usaEspada;
    private boolean estaMontando;
    private String tag;
    // Métodos mutadores
    public void atacar(Soldado objetivo) {
        int daño = this.getAtaque();
    
        if (this.estaMontando) {
            daño += 2;
        }
        // Aplicar daño al objetivo
        objetivo.recibirDaño(daño);
    }
    
    public void setUsaEspada(boolean usa){
        usaEspada=usa;
    }

    public void setMontando(boolean mont){
        estaMontando=mont;
    }

    public Caballero(boolean ejer){
        super("Caballero",(((int) (Math.random() * 3)) + 10),ejer);
        this.setAtaque(7);
        this.setDefensa(3);
        if(this.getVida()>=12){
            this.setEsEspecial(true);
            if(this.getReino().equals("Francia")){
                this.setNombre("Caballero Franco");
                this.setVida(15);
                this.tag="CF";
            }
            if(this.getReino().equals("Moros")){
                this.setNombre("Caballero Moro");
                this.setVida(13);
                this.tag="CM";
            }                     
        }
    }  

    // Métodos accesores
    public boolean getUsaEspada(){
        return usaEspada;
    }
    public boolean getMontando(){
        return estaMontando;
    }
    public String getTag(){
        return tag;
    }

    // métodos
    public void montar(){
        if(!estaMontando){
            estaMontando=true;
            usaEspada=false;
        }
    }
    public void desmontar(){
        if(estaMontando){
            estaMontando=false;
            usaEspada=true;
        }
    }        
}
