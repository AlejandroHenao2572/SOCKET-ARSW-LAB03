import java.net.*;
import java.io.*;

/**
 * Servidor TCP que calcula funciones trigonometricas (sen, cos, tan).
 * Recibe numeros del cliente y responde con el calculo segun el modo activo.
 * Por defecto calcula coseno.
 */
public class FunServer {
    // Puerto donde el servidor escucha conexiones
    private static final int PORT = 35000;
    // Constantes para los diferentes modos de operacion
    private static final String MODE_ALL = "completo";  // Calcula sen, cos y tan
    private static final String MODE_SIN = "sin";       // Solo seno
    private static final String MODE_COS = "cos";       // Solo coseno
    private static final String MODE_TAN = "tan";       // Solo tangente

    /**
     * Metodo principal que inicia el servidor.
     * Abre el puerto, espera conexiones y procesa peticiones.
     */
    public static void main(String[] args) {
        // try-with-resources: cierra automaticamente el ServerSocket
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado en puerto " + PORT);
            
            // Espera y acepta una conexion de cliente
            try (
                Socket clientSocket = serverSocket.accept();
                // out: envia respuestas al cliente
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                // in: recibe peticiones del cliente
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
                // Procesa todas las peticiones del cliente
                handleClient(in, out);
                
            } catch (IOException e) {
                System.err.println("Error al aceptar conexion: " + e.getMessage());
            }
            
        } catch (IOException e) {
            System.err.println("No se pudo iniciar el servidor en el puerto " + PORT);
            System.exit(1);
        }
    }

    /**
     * Maneja la comunicacion con un cliente conectado.
     * Lee las peticiones, procesa y envia respuestas.
     * 
     * @param in  BufferedReader para leer datos del cliente
     * @param out PrintWriter para enviar respuestas al cliente
     */
    private static void handleClient(BufferedReader in, PrintWriter out) throws IOException {
        String inputLine;
        // Estado inicial: calcula coseno por defecto
        String estadoOperacion = MODE_COS;

        // Lee continuamente lo que envia el cliente
        while ((inputLine = in.readLine()) != null) {
            // Procesa la entrada (comando o numero)
            String response = processInput(inputLine, estadoOperacion);
            
            // Verifica si el cliente quiere cambiar de modo
            String newState = updateOperationMode(inputLine);
            if (newState != null) {
                // Cambio de modo: actualiza el estado
                estadoOperacion = newState;
                out.println(response);
            } else {
                // Es un numero: calcula con el modo actual
                out.println(response);
            }
        }
    }

    /**
     * Detecta si la entrada es un comando de cambio de modo.
     * 
     * @param input Texto recibido del cliente
     * @return El nuevo modo si es un comando, null si es un numero
     */
    private static String updateOperationMode(String input) {
        switch (input) {
            case "fun:":
                return MODE_ALL;  // Modo completo
            case "fun:sin":
                return MODE_SIN;  // Modo seno
            case "fun:cos":
                return MODE_COS;  // Modo coseno
            case "fun:tan":
                return MODE_TAN;  // Modo tangente
            default:
                return null;      // No es un comando de cambio
        }
    }

    /**
     * Procesa la entrada del cliente y genera la respuesta apropiada.
     * 
     * @param input Texto recibido (comando o numero)
     * @param mode  Modo de operacion actual
     * @return Mensaje de confirmacion o resultado del calculo
     */
    private static String processInput(String input, String mode) {
        switch (input) {
            case "fun:":
                return "Modo operacion sen, cos, tan";
            case "fun:sin":
                return "Modo operacion sin";
            case "fun:cos":
                return "Modo operacion cos";
            case "fun:tan":
                return "Modo operacion tan";
            default:
                // No es un comando, debe ser un numero
                return calculateFunction(input, mode);
        }
    }

    /**
     * Calcula la funcion trigonometrica segun el modo activo.
     * 
     * @param input Numero en formato String
     * @param mode  Modo de operacion (sin, cos, tan o completo)
     * @return Resultado del calculo o mensaje de error
     */
    private static String calculateFunction(String input, String mode) {
        try {
            // Convierte el texto a numero decimal
            double n = Double.parseDouble(input);
            
            // Calcula segun el modo activo
            switch (mode) {
                case MODE_ALL:
                    // Modo completo: calcula las tres funciones
                    double seno = Math.sin(n);
                    double coseno = Math.cos(n);
                    double tangente = Math.tan(n);
                    return String.format("Sen: %.6f Cos: %.6f Tan: %.6f", seno, coseno, tangente);
                    
                case MODE_SIN:
                    // Solo calcula seno
                    return String.format("Sen: %.6f", Math.sin(n));
                    
                case MODE_COS:
                    // Solo calcula coseno
                    return String.format("Cos: %.6f", Math.cos(n));
                    
                case MODE_TAN:
                    // Solo calcula tangente
                    return String.format("Tan: %.6f", Math.tan(n));
                    
                default:
                    return "Estado de operacion desconocido";
            }
            
        } catch (NumberFormatException e) {
            // Error: el cliente no envio un numero valido
            return "Error: Entrada invalida. Por favor ingrese un numero valido.";
        }
    }
}