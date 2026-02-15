# SOCKET-ARSW-LAB03

## Descripcion ejercicio 4.3.1
Ejercicio basico de cliente-servidor con sockets en Java. El cliente envia un numero entero y el servidor responde con su cuadrado.

## Estructura
- [EchoServer.java](EchoServer.java): servidor TCP que recibe enteros y devuelve el cuadrado.
- [EchoClient.java](EchoClient.java): cliente TCP que envia el numero digitado y muestra la respuesta.

## Funcionamiento del codigo
### Servidor
- Abre un `ServerSocket` en el puerto 35000.
- Espera una conexion con `accept()`.
- Lee lineas con `BufferedReader`.
- Convierte la entrada a entero con `Integer.parseInt`.
- Calcula el cuadrado con `n * n`.
- Responde con un `PrintWriter`.

### Cliente
- Crea un `Socket` hacia la IP configurada y el puerto 35000.
- Lee la entrada del usuario desde consola.
- Envia la linea al servidor.
- Muestra la respuesta del servidor en consola.

## Notas
- La IP del servidor esta fija en [EchoClient.java](EchoClient.java); ajustala a tu red si es necesario.
- El servidor espera entradas numericas; si envias texto no numerico lanzara una excepcion.

---


