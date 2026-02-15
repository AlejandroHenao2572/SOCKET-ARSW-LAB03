import java.net.*;
import java.io.*;

public class EchoServer {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(35000);

        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        Socket clientSocket = null;

        try {
            clientSocket = serverSocket.accept();

        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true
        );

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()
                )
        );

        String inputLine;
        String outputLine;

        while ((inputLine = in.readLine()) != null) {
            
            System.out.println("numero recivido" + inputLine);
            int n = Integer.parseInt(inputLine);
            int cuadrado = n*n;
            outputLine = "Numero al cuadrado:" + String.valueOf(cuadrado);;
            out.println(outputLine);

        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
