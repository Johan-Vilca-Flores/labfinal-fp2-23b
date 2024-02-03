import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//FILES
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;

//DATABASE
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MapaGUI extends JFrame {
    private final int NUM_FILAS = 10;
    private final int NUM_COLUMNAS = 10;
    private Ejercito ejercito1;
    private Ejercito ejercito2;
    private JPanel panelBotones;
    private JButton[][] botonesMapa = new JButton[NUM_FILAS][NUM_COLUMNAS];
    private Soldado[][] tableroSoldados = new Soldado[NUM_FILAS][NUM_COLUMNAS];
    private JLabel etiquetaTurno;
    private JLabel etiquetaActividad;
    private JLabel infoSoldadoLabel;
    private JButton botonMontarDesmontar;
    private boolean jugadorActual; // True para jugador 1, false para jugador 2
    private boolean soldadoSeleccionado = false;
    private int filaSeleccionada, columnaSeleccionada;
    private Soldado soldadoActual;

    // DATABASE
    private Connection obtenerConexion() {
        String url = "jdbc:mysql://localhost:3306/fp2_23b";
        String usuario = "fp2_23b";  // Sin espacio antes del nombre de usuario
        String contraseña = "12345678";  // La contraseña debe tener 8 caracteres
        try {
            return DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public MapaGUI() {
        this.setTitle("JUEGO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Actividades interfaz
        JPanel panelEtiquetas = new JPanel(new GridLayout(4, 1)); 
        // Panel para contener las dos etiquetas
        etiquetaActividad = new JLabel("Bienvenido, gracias por venir al juego ");
        etiquetaActividad.setHorizontalAlignment(JLabel.CENTER);
        panelEtiquetas.add(etiquetaActividad);
        // Turno interfaz
        jugadorActual = Math.random() < 0.5; 
        // Determinar al jugador inicial 50%
        etiquetaTurno = new JLabel("Turno del " + (jugadorActual ? "Jugador A" : "Jugador B"));
        etiquetaTurno.setHorizontalAlignment(JLabel.CENTER);
        panelEtiquetas.add(etiquetaTurno);
        // Soldad Interfaz
        infoSoldadoLabel = new JLabel("");
        infoSoldadoLabel.setHorizontalAlignment(JLabel.CENTER);
        panelEtiquetas.add(infoSoldadoLabel);

        botonMontarDesmontar = new JButton("Montar");
        botonMontarDesmontar.addActionListener(e -> montarDesmontar());
        botonMontarDesmontar.setVisible(false);
        panelEtiquetas.add(botonMontarDesmontar);

        panelEtiquetas.setForeground(Color.BLACK);
        add(panelEtiquetas, BorderLayout.NORTH);
        // Añade el panel de etiquetas en la parte superior

        // Escoger Reino
        String reino1 = elegirReino("Jugador A");
        String reino2 = elegirReino("Jugador B");
        iniciarPartida(reino1, reino2);
        setMinimumSize(new Dimension(700, 700));
        add(panelEtiquetas, BorderLayout.NORTH);
        // Añade el panel de etiquetas en la parte superior

        System.out.println("El jugador inicial es: " + (jugadorActual ? "Jugador A" : "Jugador B"));
        JPanel gridPanel = new JPanel(new GridLayout(NUM_FILAS, NUM_COLUMNAS));
        inicializarTablero(gridPanel);
        add(gridPanel, BorderLayout.CENTER); 
        // Añade el panel de la cuadrícula en el centro
        crearPanelBotones();
        add(panelBotones, BorderLayout.SOUTH);
        // Añade el panel de botones al sur
        pack(); // Ajusta el tamaño del JFrame basado en sus componentes
        setVisible(true);
    }

    private void montarDesmontar() {
        if (soldadoActual instanceof Caballero) {
            Caballero caballero = (Caballero) soldadoActual;
            boolean estadoMontadoActual = caballero.getMontando();
            caballero.setMontando(!estadoMontadoActual);
            // Cambia el estado
    
            // Actualizar el estado en el tablero de soldados
            tableroSoldados[filaSeleccionada][columnaSeleccionada] = caballero;
    
            if (!estadoMontadoActual) {
                etiquetaActividad.setText("El caballero " + caballero.getNombre() + " montado su caballo! +2 ataque");
                botonMontarDesmontar.setText("Desmontar");
                caballero.setAtaque(caballero.getAtaque() + 2); // Aumenta ataque al montar
            } else {
                etiquetaActividad.setText("El caballero " + caballero.getNombre() + " desmonto su caballo! -2 ataque");
                botonMontarDesmontar.setText("Montar");
                caballero.setAtaque(caballero.getAtaque() - 2); // Disminuye ataque al desmontar
            }
    
            // Actualizar información en la interfaz de usuario
            String infoSoldado = "Nombre: " + caballero.getNombre() +
                                             ", Vida: " + caballero.getVida() +
                                             ", Defensa: " + caballero.getDefensa() +
                                             ", Ataque: " + caballero.getAtaque() +
                                             ", Montado: " + (caballero.getMontando() ? " montado" : "No está montado");
                        infoSoldadoLabel.setText(infoSoldado);
        }
    }
    

    private String elegirReino(String jugador) {
        String[] reinos = { "Inglaterra", "Francia", "Sacro Imperio Romano Germánico", "Castilla Aragón", "Moros" };
        String reino = (String) JOptionPane.showInputDialog(
                this,
                jugador + ", elige tu reino:",
                "Selección de Reino",
                JOptionPane.QUESTION_MESSAGE,
                null,
                reinos,
                reinos[0]);
        return reino;
    }

    private void cambiarTurno() {
        jugadorActual = !jugadorActual;
        etiquetaTurno.setText("Turno del " + (jugadorActual ? "Jugador A" : "Jugador B"));
    }

    private void crearPanelBotones() {
        panelBotones = new JPanel(new FlowLayout());

        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean guardado1 = guardarEjercito(ejercito1, "ejercitoA");
                boolean guardado2 = guardarEjercito(ejercito2, "ejercitoB");

                if (guardado1 && guardado2) {
                    JOptionPane.showMessageDialog(null, "se guardo ambos ejercitos .");
                } else {
                    String mensajeError = "no fue posible guardar los ejercitos:";
                    if (!guardado1)
                        mensajeError += "\n- Ejército A";
                    if (!guardado2)
                        mensajeError += "\n- Ejército b";
                    JOptionPane.showMessageDialog(null, mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton botonCargar = new JButton("Cargar");
        botonCargar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Ejercito ejercitoCargado1 = cargarEjercito("ejercitoA");
                Ejercito ejercitoCargado2 = cargarEjercito("ejercitoB");

                if (ejercitoCargado1 != null && ejercitoCargado2 != null) {
                    ejercito1 = ejercitoCargado1;
                    ejercito2 = ejercitoCargado2;

                    // Limpia el tablero actual
                    for (int i = 0; i < NUM_FILAS; i++) {
                        Arrays.fill(tableroSoldados[i], null);
                    }

                    // Coloca los ejércitos cargados en el tablero
                    colocarEjercitoEnTablero(ejercito1);
                    colocarEjercitoEnTablero(ejercito2);

                    // Actualiza el mapa
                    actualizarMapa();

                    // Mensaje de éxito
                    JOptionPane.showMessageDialog(null, "sse guardo con exito.", "Carga Exitosa",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Mensaje de error
                    JOptionPane.showMessageDialog(null, " error para cargar Ejercito.", "Error de Carga",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton botonCerrar = new JButton("Cerrar");
        botonCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Cierra la aplicación
                System.exit(0);
            }
        });

        panelBotones.add(botonGuardar);
        panelBotones.add(botonCargar);
        panelBotones.add(botonCerrar);

        // Agrega el panel de botones al JFrame
        this.add(panelBotones, BorderLayout.SOUTH);
    }

    private void colocarEjercitoEnTablero(Ejercito ejercito) {
        for (Soldado soldado : ejercito.getMisSoldados()) {
            if (soldado.getFila() >= 0 && soldado.getFila() < NUM_FILAS &&
                    soldado.getColumna2() >= 0 && soldado.getColumna2() < NUM_COLUMNAS) {
                tableroSoldados[soldado.getFila()][soldado.getColumna2()] = soldado;
            }
        }
    }

    private boolean guardarEjercito(Ejercito ejercito, String nombreArchivo) {
        Connection conexion = obtenerConexion();
        // Verifica conexion
        if (conexion == null) {
            return false;
        }
        // Crear archivo Byte
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            //Escribe ejercito
            oos.writeObject(ejercito);
            byte[] ejercitoAsBytes = baos.toByteArray();

            // Verificar si el ejército ya existe
            String sqlExistencia = "SELECT COUNT(*) FROM ejercito WHERE nombre = ?";
            try (PreparedStatement pstmtExistencia = conexion.prepareStatement(sqlExistencia)) {
                //Archivo consulta
                pstmtExistencia.setString(1, nombreArchivo);
                //Resultado de consulta
                ResultSet rs = pstmtExistencia.executeQuery();
                //Verifica si hay registros
                if (rs.next() && rs.getInt(1) > 0) {
                    // Actualizar si existe
                    String sqlUpdate = "UPDATE ejercito SET datos = ? WHERE nombre = ?";
                    try (PreparedStatement pstmtUpdate = conexion.prepareStatement(sqlUpdate)) {
                        //Parametros
                        pstmtUpdate.setBytes(1, ejercitoAsBytes);
                        pstmtUpdate.setString(2, nombreArchivo);
                        pstmtUpdate.executeUpdate();
                        return true;
                    }
                } else {
                    // Insertar si no existe
                    String sqlInsert = "INSERT INTO ejercito (nombre, datos) VALUES (?, ?)";
                    try (PreparedStatement pstmtInsert = conexion.prepareStatement(sqlInsert)) {
                        pstmtInsert.setString(1, nombreArchivo);
                        pstmtInsert.setBytes(2, ejercitoAsBytes);
                        pstmtInsert.executeUpdate();
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private Ejercito cargarEjercito(String nombreArchivo) {
        Connection conexion = obtenerConexion();
        if (conexion == null) {
            return null;
        }
        try (PreparedStatement pstmt = conexion.prepareStatement("SELECT datos FROM ejercito WHERE nombre = ?");) {
            pstmt.setString(1, nombreArchivo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                byte[] ejercitoAsBytes = rs.getBytes("datos");
                try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(ejercitoAsBytes))) {
                    return (Ejercito) ois.readObject();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    private void iniciarPartida(String reino1, String reino2) {
        if (reino1 != null && reino2 != null && !reino1.equals(reino2)) {
            Ejercito.setReinos(reino1, reino2);
        } else {
            JOptionPane.showMessageDialog(this, "Debes seleccionar reinos diferentes para comenzar el juego.");
            System.exit(0); // o reiniciar el proceso de selección
        }
    }

    private void inicializarTablero(JPanel gridPanel) {
    for (int i = 0; i < NUM_FILAS; i++) {
        for (int j = 0; j < NUM_COLUMNAS; j++) {
            JButton boton = new JButton();
            boton.setMargin(new Insets(0, 0, 0, 0));
            boton.setBackground(Color.WHITE);
            boton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            boton.setContentAreaFilled(false);
            boton.setFocusPainted(false);
            boton.setOpaque(true);

            final int fila = i, columna = j;
            boton.addActionListener(e -> manejarClicBoton(fila, columna));

            // Añadir MouseListener aquí
            boton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    Soldado soldado = tableroSoldados[fila][columna];
                    if (soldado != null && !(soldado instanceof Arquero) && !(soldado instanceof Caballero)) {
                        String infoSoldado = "Nombre: " + soldado.getNombre() +
                                             ", Vida: " + soldado.getVida() +
                                             ", Defensa: " + soldado.getDefensa() +
                                             ", Ataque: " + soldado.getAtaque();
                        infoSoldadoLabel.setText(infoSoldado);
                    } else if (soldado instanceof Arquero) {
                        Arquero arquero = (Arquero) soldado;
                        String infoSoldado = "Nombre: " + arquero.getNombre() +
                                             ", Vida: " + arquero.getVida() +
                                             ", Defensa: " + arquero.getDefensa() +
                                             ", Ataque: " + arquero.getAtaque() +
                                             ", Flechas: " + arquero.getNumFlechas();
                        infoSoldadoLabel.setText(infoSoldado);
                    } else if (soldado instanceof Caballero) {
                        Caballero caballero = (Caballero) soldado;
                        String infoSoldado = "Nombre: " + caballero.getNombre() +
                                             ", Vida: " + caballero.getVida() +
                                             ", Defensa: " + caballero.getDefensa() +
                                             ", Ataque: " + caballero.getAtaque() +
                                             ", Montado: " + (caballero.getMontando() ? "  montado" : "No está montado");
                        infoSoldadoLabel.setText(infoSoldado);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    infoSoldadoLabel.setText("");
                }
            });

            botonesMapa[i][j] = boton;
            gridPanel.add(boton);
        }
    }
    generarSoldados();
    actualizarMapa();
}

    private void manejarClicBoton(int fila, int columna) {
        JButton boton = botonesMapa[fila][columna];

        if (!soldadoSeleccionado) {
            // Primer clic - seleccionar soldado
            if (tableroSoldados[fila][columna] != null) {
                if (tableroSoldados[fila][columna].getEjercito() == jugadorActual) {
                    soldadoSeleccionado = true;
                    filaSeleccionada = fila;
                    columnaSeleccionada = columna;
                    soldadoActual = tableroSoldados[fila][columna];
                    boton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
                    // Verificar si el soldado seleccionado es un Caballero de tu ejército y no está montado
                    if (soldadoActual instanceof Caballero) {
                        botonMontarDesmontar.setVisible(true);
                        botonMontarDesmontar.setText(((Caballero) soldadoActual).getMontando() ? "Desmontar" : "Montar");
                    } else {
                        botonMontarDesmontar.setVisible(false);
                    }
                }
            }
            if (soldadoSeleccionado) {
                etiquetaActividad.setForeground(Color.BLACK);
                etiquetaActividad.setText("Seleccionaste a " + soldadoActual.getNombre());
            }
        } else {
            // Segundo clic - seleccionar posición de destino
            if (fila == filaSeleccionada && columna == columnaSeleccionada) {
                // Cancelar selección si se hace clic en el mismo soldado
                soldadoSeleccionado = false;
                botonesMapa[fila][columna].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                botonMontarDesmontar.setVisible(false);
            } else {
                // Segundo clic - seleccionar otro soldado o mover
                if (tableroSoldados[fila][columna] != null
                        && tableroSoldados[fila][columna].getEjercito() == jugadorActual) {
                    // Seleccionar otro soldado del mismo ejército
                    etiquetaActividad.setText("¡error, es de tu propio Ejercito!");
                    botonesMapa[filaSeleccionada][columnaSeleccionada]
                            .setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    soldadoSeleccionado = false;
                } else {
                    // Mover soldado a espacio en blanco o realizar una acción de batalla
                    realizarMovimiento(filaSeleccionada, columnaSeleccionada, fila, columna);
                    botonesMapa[filaSeleccionada][columnaSeleccionada]
                            .setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    soldadoSeleccionado = false;
                }
            }
        }
    }

    private void realizarMovimiento(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        etiquetaActividad.setForeground(Color.BLACK);
        int distanciaFila = Math.abs(filaDestino - filaOrigen);
        int distanciaColumna = Math.abs(colDestino - colOrigen);
        int distancia = Math.max(distanciaFila, distanciaColumna);
        
        Soldado soldadoAtacante = tableroSoldados[filaOrigen][colOrigen];
        Soldado soldadoObjetivo = tableroSoldados[filaDestino][colDestino];
        int rangoAtaque = soldadoAtacante instanceof Arquero ? Arquero.ALCANCE_ATAQUE : 1;
        // Por ejemplo, mover el soldado al espacio en blanco o iniciar una batalla si
        // hay un soldado enemigo
        if (Math.abs(distanciaFila) <= 1 && Math.abs(distanciaColumna) <= 1
                && tableroSoldados[filaDestino][colDestino] == null) {
            // Mover soldado
            tableroSoldados[filaDestino][colDestino] = tableroSoldados[filaOrigen][colOrigen];
            tableroSoldados[filaOrigen][colOrigen] = null;
            actualizarMapa();
            etiquetaActividad.setText("Moviste a " + soldadoActual.getNombre() + " nueva posicion.");
            cambiarTurno();
        } else if (Math.abs(distanciaFila) <= rangoAtaque && Math.abs(distanciaColumna) <= rangoAtaque
                && tableroSoldados[filaDestino][colDestino] != null
                && tableroSoldados[filaDestino][colDestino].getEjercito() != jugadorActual) {
            // Atacar Soldado
              if (distancia <= rangoAtaque) {
                soldadoAtacante.atacar(soldadoObjetivo);
                if (soldadoObjetivo.getVida() <= 0) {
                    // Manejar la eliminación del soldado objetivo si su vida es 0 o menos
                    tableroSoldados[filaDestino][colDestino] = null;
                    etiquetaActividad.setForeground(Color.RED);
                    etiquetaActividad.setText("Eliminaste a " + soldadoObjetivo.getNombre());
                    infoSoldadoLabel.setText(null);
                } else {
                    etiquetaActividad.setText("Atacaste a " + soldadoObjetivo.getNombre());
                    infoSoldadoLabel.setText("Soldado Atacado: " + soldadoObjetivo.getNombre() + 
                             " - Vida: " + soldadoObjetivo.getVida() +
                             ", Defensa: " + soldadoObjetivo.getDefensa());
                }
                actualizarMapa();
                cambiarTurno();
                verificarFinJuego();
            } else {
                etiquetaActividad.setText("El objetivo está fuera de alcance para atacar.");
            }
        } else {
            etiquetaActividad.setText("No puedes moverte a esta casilla. Está muy lejos!");
        }
    }

    private void verificarFinJuego() {
        int soldadosJugador1 = contarSoldados(true);
        int soldadosJugador2 = contarSoldados(false);

        if (soldadosJugador1 == 0 || soldadosJugador2 == 0) {
            String ganador = soldadosJugador1 > 0 ? "Jugador A" : "Jugador B";
            JOptionPane.showMessageDialog(this, "Fin del juego. Ganó " + ganador);
            // Aquí puedes agregar lógica para terminar o reiniciar el juego
        }
    }

    private int contarSoldados(boolean ejercito) {
        int contador = 0;
        for (int i = 0; i < NUM_FILAS; i++) {
            for (int j = 0; j < NUM_COLUMNAS; j++) {
                if (tableroSoldados[i][j] != null && tableroSoldados[i][j].getEjercito() == ejercito) {
                    contador++;
                }
            }
        }
        return contador;
    }

    private void actualizarMapa() {
        for (int i = 0; i < NUM_FILAS; i++) {
            for (int j = 0; j < NUM_COLUMNAS; j++) {
                JButton boton = botonesMapa[i][j];
                Soldado soldado = tableroSoldados[i][j];
                if (soldado != null) {
                    ImageIcon icono = cargarIconoDeImagen(soldado);
                    if (icono != null) {
                        boton.setIcon(icono);
                        boton.setText(""); // Limpiar texto si se usa icono
                    } else {
                        boton.setText(soldado.getNombre() + " (" + soldado.getVida() + ")");
                    }
                    boton.setBackground(soldado.getEjercito() ? Color.BLUE : Color.RED);
                } else {
                    boton.setIcon(null);
                    boton.setText("");
                    boton.setBackground(Color.WHITE);
                }
            }
        }
    }

    private ImageIcon cargarIconoDeImagen(Soldado soldado) {
        String nombreImagen = obtenerRutaImagenSoldado(soldado);
        if (nombreImagen != null) {
            java.net.URL imageURL = getClass().getResource(nombreImagen);
            if (imageURL != null) {
                ImageIcon icono = new ImageIcon(imageURL);
                Image imagen = icono.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                return new ImageIcon(imagen);
            } else {
                System.err.println("No se encontró el recurso: " + nombreImagen);
            }
        }
        return null;
    }

    private String obtenerRutaImagenSoldado(Soldado soldado) {
        // El directorio base donde se encuentran las imágenes de los soldados.
        String basePath = "/img/";

        if (soldado instanceof Arquero) {
            return basePath + "arquero.jpg";
        } else if (soldado instanceof Caballero) {
            return basePath + "caballero.jpg";
        } else if (soldado instanceof Lanzero) {
            return basePath + "lanzero.jpg";
        } else if (soldado instanceof Espadachin) {
            return basePath + "espadachin_real.jpg";
        } else {
            return null;
        }
    }

    private void generarSoldados() {
        Random random = new Random();

        // Decidir cuántos soldados habrá en cada ejército
        Ejercito.totalSoldados1 = (((int) (Math.random() * Ejercito.maxSoldados)) + 1);
        Ejercito.totalSoldados2 = (((int) (Math.random() * Ejercito.maxSoldados)) + 1);
        Ejercito.totalSoldados = Ejercito.totalSoldados1 + Ejercito.totalSoldados2;

        // Crear ejércitos
        ejercito1 = new Ejercito(true);
        ejercito2 = new Ejercito(false);

        // Colocar soldados en el tablero
        for (Soldado soldado : ejercito1.getMisSoldados()) {
            int fila, columna;
            do {
                fila = random.nextInt(NUM_FILAS);
                columna = random.nextInt(NUM_COLUMNAS);
            } while (tableroSoldados[fila][columna] != null);
            soldado.setFila(fila);
            soldado.setColumna2(columna);
            tableroSoldados[fila][columna] = soldado;
        }

        for (Soldado soldado : ejercito2.getMisSoldados()) {
            int fila, columna;
            do {
                fila = random.nextInt(NUM_FILAS);
                columna = random.nextInt(NUM_COLUMNAS);
            } while (tableroSoldados[fila][columna] != null);
            soldado.setFila(fila);
            soldado.setColumna2(columna);
            tableroSoldados[fila][columna] = soldado;
        }

        imprimirEstadisticasIniciales(ejercito1, ejercito2);

    }

    private void imprimirEstadisticasIniciales(Ejercito ejercito1, Ejercito ejercito2) {
        System.out.println("Estadísticas iniciales:");
        System.out.println("Ejército1 con " + Ejercito.totalSoldados1 + " soldados.");
        for (Soldado soldado : ejercito1.getMisSoldados()) {
            System.out.println("\tNombre: " + soldado.getNombre() + "\tVida: " + soldado.getVida() + "  \tReino: "
                    + soldado.getReino() + "\tfila: " + (soldado.getFila() + 1) + "\tcolumna: "
                    + (soldado.getColumna2() + 1));
        }
        System.out.println("Ejército2 con " + Ejercito.totalSoldados2 + " soldados.");
        for (Soldado soldado : ejercito2.getMisSoldados()) {
            System.out.println("\tNombre: " + soldado.getNombre() + "\tVida: " + soldado.getVida() + " \tReino: "
                    + soldado.getReino() + (soldado.getFila() + 1) + "\tcolumna: " + (soldado.getColumna2() + 1));
        }
        System.out.println("INICIA LA BATALLA !");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MapaGUI::new);
    }
}
