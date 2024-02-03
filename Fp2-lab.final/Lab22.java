import java.util.*;

public class Lab22 {
    //turno batalla ejercitos
    
    public static void turnoBatalla(Soldado[][] tablero, boolean ejercito) {
        int turno;
        int enemigo;
        if (ejercito) {
            turno = 1;
            enemigo = 2;
        } else {
            turno = 2;
            enemigo = 1;
        }       
        System.out.println("Turno del jugador " + turno + ":");
        System.out.println("Eliga la posición del soldado que utilizará: ");
        int filaSol1 =Atajos.fila();
        int colSol1 =Atajos.columna();
        while (true) {
            if (filaSol1 > 10 || filaSol1 < 1 || colSol1 == -1) {
                System.out.println("Soldado inválido, fuera de los límites.");
                System.out.println("Eliga la posición del soldado que utilizará: ");
                filaSol1 = Atajos.fila();
                colSol1 = Atajos.columna();
            } else {
                if (tablero[filaSol1 ][colSol1 ] == null) {
                    System.out.println("Soldado inválido, casilla vacía. ");
                    System.out.println("Eliga la posición del soldado que utilizará: ");
                    filaSol1 = Atajos.fila();
                    colSol1 = Atajos.columna();
                } else {
                    if ((!(tablero[filaSol1 ][colSol1 ].getEjercito()) || (ejercito))
                            && (!(ejercito) || (tablero[filaSol1 ][colSol1 ].getEjercito()))) {
                        System.out.println("Soldado válido.");
                        System.out.println("Fila: " + filaSol1);
                        System.out.println("Columna: " + Atajos.convertir(colSol1));
                        break;
                    } else {
                        System.out.println("Soldado inválido, soldado enemigo. ");
                        System.out.println("Eliga la posición del soldado que utilizará: ");
                        filaSol1 = Atajos.fila();
                        colSol1 = Atajos.columna();
                    }
                }
            }
        }
        System.out.println("Eliga la posición para realizar el movimiento: ");
        int filaMov1 = Atajos.fila();
        int colMov1 = Atajos.columna();
        while (true) {
            if (filaMov1 > 10 || filaMov1 < 1 || colMov1 == -1) {
                System.out.println("Movimiento inválido, fuera de los límites.");
                System.out.println("Eliga la posición para realizar el movimiento: ");
                filaMov1 = Atajos.fila();
                colMov1 = Atajos.columna();
            } else {
                if (tablero[filaMov1 ][colMov1 ] == null) {
                    System.out.println("Movimiento válido: ");
                    System.out.println("Fila: " + filaMov1);
                    System.out.println("Columna: " + Atajos.convertir(colMov1));
                    tablero[filaMov1 ][colMov1 ] = new Soldado();
                    tablero[filaMov1 ][colMov1 ] = tablero[filaSol1 ][colSol1 ];
                    tablero[filaSol1 ][colSol1 ] = null;
                    break;
                } else {
                    if ((!(tablero[filaMov1 ][colMov1 ].getEjercito()) || (ejercito))
                            && (!(ejercito) || (tablero[filaMov1 ][colMov1 ].getEjercito()))) {
                        System.out.println("Movimiento inválido, ya hay un aliado en esa posición.");
                        System.out.println("Eliga la posición para realizar el movimiento: ");
                        filaMov1 = Atajos.fila();
                        colMov1 = Atajos.columna();
                    } else {
                        System.out.println("Movimiento válido: ");
                        System.out.println("Fila: " + filaMov1);
                        System.out.println("Columna: " + Atajos.convertir(colMov1));
                        System.out.println(" ¡EMPIEZA UNA BATALLA!");
                        Soldado.reglas(tablero[filaSol1 ][colSol1 ], tablero[filaMov1 ][colMov1 ]);
                        int vida1 = tablero[filaSol1 ][colSol1 ].getVida();
                        int vida2 = tablero[filaMov1 ][colMov1 ].getVida();
                        int suma = vida1 + vida2;
                        int random = (int) ((Math.random() * suma) + 1);
                        System.out.println("Posibilidades de ganar : 1-" + vida1 + "  " + (vida1 * 100 / suma) + "%");
                        System.out.println("Posibilidades de ganar del enemigo : " + (vida1 + 1) + "-" + suma + "  "
                                + (vida2 * 100 / suma) + "%");
                        System.out.println("Random:" + random);
                        if (random <= vida1) {
                            System.out.println("Gana el jugador: " + turno);
                            tablero[filaMov1 ][colMov1 ].setVida(0);
                            tablero[filaMov1 ][colMov1 ] = tablero[filaSol1 ][colSol1 ];
                            tablero[filaMov1 ][colMov1 ].setVida((tablero[filaMov1 ][colMov1 ].getVida()) + 1);
                            tablero[filaSol1 ][colSol1 ] = null;
                            if (ejercito) {
                                Ejercito.totalSoldados2--;
                            } else {
                                Ejercito.totalSoldados1--;
                            }
                        } else {
                            System.out.println("Gana el jugador: " + enemigo);
                            tablero[filaSol1 ][colSol1 ].setVida(0);
                            tablero[filaSol1 ][colSol1 ] = null;
                            tablero[filaMov1 ][colMov1 ]
                                    .setVida((tablero[filaMov1 ][colMov1 ].getVida()) + 1);
                            if (ejercito) {
                                Ejercito.totalSoldados1--;
                            } else {
                                Ejercito.totalSoldados2--;
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        while(true){
            System.out.println("Empieza la batalla entre el "+Atajos.BLUE+"Ejercito 1"+Atajos.RESET+" y el "+Atajos.RED+"Ejercito 2"+Atajos.RESET);
            //eleccion del reino
            String[]reinos={"Inglaterra", "Francia", "Sacro Imperio Romano Germánico",  "Castilla Aragón ", "Moros"};
            System.out.println("El juego empezará cuando los 2 juagadores eligan el reino que deseen usar:");
            System.out.println("Es turn1o de elegir del jugador 1.");
            int reinoJugador1=Atajos.elegirOpcion(reinos);
            String reino1=reinos[reinoJugador1];
            System.out.println("El jugador 1 ha elegido: "+reino1);
            System.out.println("Es turno de elegir del jugador 2, recuerde que no se puede usar el mismo ejercito.");
            int reinoJugador2;
            String reino2;  
            do{
                reinoJugador2=Atajos.elegirOpcion(reinos);
                reino2=reinos[reinoJugador2];
            }
            while(reinoJugador1==reinoJugador2);
            System.out.println("El jugador 2 ha elegido: "+reino2);
            //fin eleccion reino
            
            //Datos generales
            Ejercito.totalSoldados1=(((int) (Math.random() * Ejercito.maxSoldados)) + 1);
            Ejercito.totalSoldados2=(((int) (Math.random() * Ejercito.maxSoldados)) + 1);
            Ejercito.totalSoldados=Ejercito.totalSoldados1+Ejercito.totalSoldados2;
            Ejercito.setReinos(reino1, reino2);
            //Creación de los ejercitos
            Ejercito ejercito1=new Ejercito(true);
            Ejercito ejercito2=new Ejercito(false);
            ArrayList<Soldado> actual1=ejercito1.arregloAuxiliar();
            ArrayList<Soldado> actual2=ejercito2.arregloAuxiliar();
            //Ordenamiento en el tablero bidimensional
            ArrayList<Soldado>ejercitos=(ArrayList<Soldado>) actual1.clone();
            for(int i=0;i<actual2.size();i++){
                ejercitos.add(actual2.get(i));
            }
            for(int i=0;i<ejercitos.size();i++){
                    ejercitos.get(i).setFila((int) (Math.random() * 10) + 1);
                    ejercitos.get(i).setColumna2((int) (Math.random() * 10) + 1);
                    ejercitos.get(i).setColumna(Atajos.convertir(ejercitos.get(i).getColumna2()));
                    int j=0;
                    while (j<i) {
                        if(ejercitos.get(j).getFila()==ejercitos.get(i).getFila()&&ejercitos.get(j).getColumna2()==ejercitos.get(i).getColumna2()){
                            if(ejercitos.get(j).getFila()==ejercitos.get(i).getFila()){
                                ejercitos.get(i).setFila((int) (Math.random() * 10) + 1);
                            }
                            else{
                                ejercitos.get(i).setColumna2((int) (Math.random() * 10) + 1);
                                ejercitos.get(i).setColumna(Atajos.convertir((ejercitos.get(i).getColumna2())));
                            }
                            j=0;
                        }
                        else{
                            j++;
                        }
                    }
            }
            //rellenar el tablero bidimensional
            Mapa mapa=new Mapa();
            Soldado.aplicarBonus(ejercitos,mapa.getTerritorio());
            mapa.generarMapa(ejercitos);
            //fin relleno tablero
            Soldado.imprimirTablero(mapa.tableroSoldados);
            //iniciar batalla
            ejercito1.ordenarEjercito();
            ejercito2.ordenarEjercito();
            System.out.println("Estadísticas iniciales:");
            System.out.println(Atajos.BLUE+"Ejército"+1+ " con "+Ejercito.totalSoldados1+" soldados."+Atajos.RESET);
            ejercito1.mostrarDatos();
            System.out.println(Atajos.RED+"Ejército"+2+ " con "+Ejercito.totalSoldados2+" soldados."+Atajos.RESET);
            ejercito2.mostrarDatos();
            System.out.println("INICIA LA BATALLA !");
            while ((Ejercito.totalSoldados1 != 0) && (Ejercito.totalSoldados2 != 0)) {
                System.out.println(Atajos.BLUE+"Soldados activos del ejercito 1: " + Ejercito.totalSoldados1+Atajos.RESET);
                System.out.println(Atajos.RED+"Soldados activos del ejercito 2: " + Ejercito.totalSoldados2+Atajos.RESET);
                turnoBatalla(mapa.tableroSoldados, true);
                Soldado.imprimirTablero(mapa.tableroSoldados);
                if((Ejercito.totalSoldados1 == 0) || (Ejercito.totalSoldados2 == 0)){
                    break;
                }
                turnoBatalla(mapa.tableroSoldados, false);
                Soldado.imprimirTablero(mapa.tableroSoldados);
            }
            if (Ejercito.totalSoldados1== 0) {
                System.out.println(Atajos.BLUE+"Gana el jugador 2. El ejercito 1 ha sido eliminado"+Atajos.RESET);
            } else {
                System.out.println(Atajos.RED+"Gana el jugador 1. El ejercito 2 ha sido eliminado"+Atajos.RESET);
            }
            //finbatalla
            System.out.println("Estadísticas finales:");
            System.out.println(Atajos.BLUE+"Ejército"+1+ " con "+Ejercito.totalSoldados1+" soldados activos."+Atajos.RESET);
            Soldado.mostrarDatosSol(actual1);
            System.out.println(Atajos.RED+"Ejército"+2+ " con "+Ejercito.totalSoldados2+" soldados activos."+Atajos.RESET);
            Soldado.mostrarDatosSol(actual2);
            //Secuencia de control
            System.out.println("¿ Desea generar otra batalla ? (s/n)");
            String control=(scan.next()).toLowerCase();
            if(control.equals("n")){
                break;
            }
        }
    }        
}
