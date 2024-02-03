import javax.swing.JOptionPane;

public class BatallaInterfaz extends Lab22 {

    public static void main(String[] args) {
        while (true) {
            Ejercito ejercito1 = new Ejercito();
            Ejercito ejercito2 = new Ejercito();
            Mapa mapa = new Mapa();

            ejercito1.ordenarEjercito();
            ejercito2.ordenarEjercito();

            JOptionPane.showMessageDialog(null, "Estadísticas :\n\n" +
                    "Ejército 1 con " + Ejercito.totalSoldados1 + " soldados.\n" +
                    "Ejército 2 con " + Ejercito.totalSoldados2 + " soldados.");

            // Lógica de la batalla
            while ((Ejercito.totalSoldados1 != 0) && (Ejercito.totalSoldados2 != 0)) {
                JOptionPane.showMessageDialog(null,
                        "Soldados activos, Ejercito 1: " + Ejercito.totalSoldados1 + "\n" +
                                "Soldados activos, Ejercito 2: " + Ejercito.totalSoldados2);

                // Logica para el turno de batalla
                turnoBatalla(mapa.tableroSoldados, true);
                Soldado.imprimirTablero(mapa.tableroSoldados);

                if ((Ejercito.totalSoldados1 == 0) || (Ejercito.totalSoldados2 == 0)) {
                    break;
                }

                turnoBatalla(mapa.tableroSoldados, false);
                Soldado.imprimirTablero(mapa.tableroSoldados);
            }

            if (Ejercito.totalSoldados1 == 0) {
                JOptionPane.showMessageDialog(null, "El ganador es el jugador: 2. Lastimosamente el ejército 1 fue derrotado");
            } else {
                JOptionPane.showMessageDialog(null, "El ganador es eljugador: 1. El ejército 2 fue derrotado");
            }

            // Estadísticas finales
            JOptionPane.showMessageDialog(null,
                    "Estadísticas finales:\n\n" +
                            "Ejército 1 tiene " + Ejercito.totalSoldados1 + " activos.\n" +
                            "Ejército 2 tiene " + Ejercito.totalSoldados2 + " activos.");

            // consulta para generar una nueva tabla
            String control = JOptionPane.showInputDialog("¿Le gustaria generar una nueva tabla? (s/n)").toLowerCase();
            if (control.equals("n")) {
                break;
            }
        }
    }}
