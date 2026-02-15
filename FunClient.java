import java.io.*;
import java.net.*;

/**
 * Cliente TCP que se conecta a un servidor para calcular funciones trigonometricas.
 * Envia numeros y comandos al servidor, recibe los resultados calculados.
 */
public class FunClient {
    // Direccion IP del servidor al que nos conectamos
    private static final String SERVER_HOST = "192.168.2.58";
    // Puerto donde escucha el servidor
    private static final int SERVER_PORT = 35000;

    /**
     * Metodo principal que inicia el cliente.
     * Establece conexion con el servidor y permite interaccion con el usuario.
     */
    public static void main(String[] args) {
        // try-with-resources cierra automaticamente los recursos al terminar
        try (
            // Socket: conexion TCP con el servidor
            Socket echoSocket = new Socket(SERVER_HOST, SERVER_PORT);
            // out: envia datos al servidor
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            // in: recibe datos del servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            // stdIn: lee lo que escribe el usuario en consola
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            // Muestra instrucciones al usuario
            printInstructions();
            
            String userInput;
            // Lee continuamente lo que escribe el usuario
            while ((userInput = stdIn.readLine()) != null) {
                // Permite salir escribiendo exit o quit
                if (userInput.equalsIgnoreCase("exit") || userInput.equalsIgnoreCase("quit")) {
                    System.out.println("Cerrando conexion...");
                    break;
                }
                
                // Envia lo que escribio el usuario al servidor
                out.println(userInput);
                // Lee la respuesta del servidor
                String response = in.readLine();
                
                // Muestra la respuesta o informa si se perdio la conexion
                if (response != null) {
                    System.out.println("Servidor: " + response);
                } else {
                    System.out.println("Conexion cerrada por el servidor");
                    break;
                }
            }

        } catch (UnknownHostException e) {
            // Error: no se encuentra el servidor en esa direccion
            System.err.println("No se pudo encontrar el host: " + SERVER_HOST);
            System.exit(1);
            
        } catch (IOException e) {
            // Error: problemas de red o conexion
            System.err.println("Error de conexion con " + SERVER_HOST + ":" + SERVER_PORT);
            System.err.println("Detalle: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Muestra en pantalla las instrucciones de uso para el usuario.
     * Explica los comandos disponibles y como usar el cliente.
     */
    private static void printInstructions() {
        System.out.println("CLIENTE DE FUNCIONES TRIGONOMETRICAS");
        System.out.println("Operaciones disponibles:");
        System.out.println("  fun:     - Calcular sen, cos y tan");
        System.out.println("  fun:sin  - Modo seno");
        System.out.println("  fun:cos  - Modo coseno");
        System.out.println("  fun:tan  - Modo tangente");
        System.out.println("\nIngrese el modo seguido del numero a calcular");
        System.out.println("Escriba 'exit' o 'quit' para salir\n");
    }
}