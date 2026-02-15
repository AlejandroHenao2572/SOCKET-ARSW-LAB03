import java.net.*;
import java.io.*;

public class HttpServerPro {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(35000);
            System.out.println("Servidor HTTP iniciado en puerto 35000");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        while (true) {

            // Aceptar al cliente
            try (Socket clientSocket = serverSocket.accept()) {

                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                // Parsear la peticion
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream())
                );

                // Extraer la ruta del archivo solicitado
                String requestLine = in.readLine();
                
                // Validar que la peticion no sea nula
                if (requestLine == null || requestLine.isEmpty()) {
                    continue;
                }

                System.out.println("Peticion: " + requestLine);

                // Leer y consumir el resto de headers
                String line;
                while ((line = in.readLine()) != null && !line.isEmpty()) {
                    // Consumir headers hasta linea vacia
                }

                String[] tokens = requestLine.split(" ");
                if (tokens.length < 2) {
                    continue; // Peticion invalida
                }

                String path = tokens[1]; // /index.html
                if (path.equals("/")) {
                    path = "/index.html";
                }

                System.out.println("Archivo solicitado: " + path);

                // Verificar si el archivo existe
                File file = new File("www" + path);

                if (file.exists() && !file.isDirectory()) {
                    // Enviar archivo

                    // Determinar el tipo de contenido
                    String contentType = "text/html"; // Por defecto
                    if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
                        contentType = "image/jpeg";
                    } else if (path.endsWith(".png")) {
                        contentType = "image/png";
                    } else if (path.endsWith(".css")) {
                        contentType = "text/css";
                    } else if (path.endsWith(".js")) {
                        contentType = "application/javascript";
                    } else if (path.endsWith(".gif")) {
                        contentType = "image/gif";
                    } else if (path.endsWith(".ico")) {
                        contentType = "image/x-icon";
                    }

                    // Leer el archivo como bytes
                    FileInputStream fis = new FileInputStream(file);
                    byte[] fileContent = new byte[(int) file.length()];
                    fis.read(fileContent);
                    fis.close();

                    // Enviar headers HTTP
                    OutputStream os = clientSocket.getOutputStream();
                    PrintWriter pw = new PrintWriter(os, true);
                    pw.println("HTTP/1.1 200 OK");
                    pw.println("Content-Type: " + contentType);
                    pw.println("Content-Length: " + fileContent.length);
                    pw.println(); // Linea vacia separa headers del body

                    // Enviar el contenido
                    os.write(fileContent);
                    os.flush();

                    System.out.println("Archivo enviado: " + path + " (" + contentType + ")");

                } else {
                    // Enviar 404
                    String response404 = "HTTP/1.1 404 Not Found\r\n"
                            + "Content-Type: text/html\r\n"
                            + "\r\n"
                            + "<!DOCTYPE html>"
                            + "<html><body>"
                            + "<h1>404 - File Not Found</h1>"
                            + "<p>El archivo " + path + " no existe</p>"
                            + "</body></html>";

                    OutputStream os = clientSocket.getOutputStream();
                    os.write(response404.getBytes());
                    os.flush();

                    System.out.println("404 - Archivo no encontrado: " + path);
                }

                in.close();
                // clientSocket se cierra automaticamente con try-with-resources

            } catch (IOException e) {
                System.err.println("Error procesando peticion: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}