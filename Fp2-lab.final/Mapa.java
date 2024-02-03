import java.util.ArrayList;

public class Mapa {
    private String territorio;
    public Soldado[][]tableroSoldados=new Soldado[11][11];
    private String territorios[]={"Bosque", "Campo abierto", "Montaña", "Desierto", "Playa"};
    
    //Métodos
    public Mapa(){
        territorio=territorios[(int) (Math.random() * 5) ];
    }
    public void generarMapa(ArrayList<Soldado> ejercitos){
        int varTablero=0;
        while(varTablero<ejercitos.size()){
            tableroSoldados[ejercitos.get(varTablero).getFila()][ejercitos.get(varTablero).getColumna2()] = ejercitos.get(varTablero);
            varTablero++;
        }
    }
    public String getTerritorio(){
        return territorio;
    }
}
