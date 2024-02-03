import javax.swing.JOptionPane;

public class BatallaInterfaz extends Lab22 {

    public static void main(String[] args) {
        while (true) {
            Ejercito ejercito1 = new Ejercito();
            Ejercito ejercito2 = new Ejercito();
            Mapa mapa = new Mapa();

            ejercito1.ordenarEjercito();
            ejercito2.ordenarEjercito();

            JOptionPane.showMessageDialog(null, "Estadísticas iniciales:\n\n" +
                    "Ejército 1 con " + Ejercito.totalSoldados1 + " soldados.\n" +
                    "Ejército 2 con " + Ejercito.totalSoldados2 + " soldados.");

            // Lógica de la batalla
            while ((Ejercito.totalSoldados1 != 0) && (Ejercito.totalSoldados2 != 0)) {
                JOptionPane.showMessageDialog(null,
                        "Soldados activos del ejercito 1: " + Ejercito.totalSoldados1 + "\n" +
                                "Soldados activos del ejercito 2: " + Ejercito.totalSoldados2);

                // Lógica del turno de batalla
                turnoBatalla(mapa.tableroSoldados, true);
                Soldado.imprimirTablero(mapa.tableroSoldados);

                if ((Ejercito.totalSoldados1 == 0) || (Ejercito.totalSoldados2 == 0)) {
                    break;
                }

                turnoBatalla(mapa.tableroSoldados, false);
                Soldado.imprimirTablero(mapa.tableroSoldados);
            }

            if (Ejercito.totalSoldados1 == 0) {
                JOptionPane.showMessageDialog(null, "Gana el jugador 2. El ejército 1 ha sido eliminado");
            } else {
                JOptionPane.showMessageDialog(null, "Gana el jugador 1. El ejército 2 ha sido eliminado");
            }

            // Estadísticas finales
            JOptionPane.showMessageDialog(null,
                    "Estadísticas finales:\n\n" +
                            "Ejército 1 con " + Ejercito.totalSoldados1 + " soldados activos.\n" +
                            "Ejército 2 con " + Ejercito.totalSoldados2 + " soldados activos.");

            // Pregunta para generar otra batalla
            String control = JOptionPane.showInputDialog("¿Desea generar otra batalla? (s/n)").toLowerCase();
            if (control.equals("n")) {
                break;
            }
        }
    }}
