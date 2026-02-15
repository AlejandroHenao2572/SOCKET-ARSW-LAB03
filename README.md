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

## Descripcion del Ejercicio 4.3.2

El servidor puede recibir numeros y responde con operaciones trigonometricas sobre estos. El servidor acepta mensajes que empiezan con "fun:" para cambiar la operacion actual. Por defecto calcula el coseno.

**Funciones disponibles:**
- Seno (sin)
- Coseno (cos) - operacion por defecto
- Tangente (tan)
- Todas las anteriores (fun:)

## Ejecucion

### Iniciar el Servidor

```bash
java FunServer
```

Salida esperada:
```
Servidor iniciado en puerto 35000
```

### Iniciar el Cliente

```bash
java FunClient
```

Salida esperada:
```
CLIENTE DE FUNCIONES TRIGONOMETRICAS
Operaciones disponibles:
  fun:     - Calcular sen, cos y tan
  fun:sin  - Modo seno
  fun:cos  - Modo coseno
  fun:tan  - Modo tangente

Ingrese el modo seguido del numero a calcular
Escriba 'exit' o 'quit' para salir
```

## Ejemplos de Uso

### Ejemplo 1: Calculo de coseno por defecto

```
Usuario: 0
Servidor: Cos: 1.000000

Usuario: 1.5707963267948966
Servidor: Cos: 0.000000
```

### Ejemplo 2: Cambio a modo seno

```
Usuario: fun:sin
Servidor: Modo operacion sin

Usuario: 0
Servidor: Sen: 0.000000

Usuario: 1.5707963267948966
Servidor: Sen: 1.000000
```

### Ejemplo 3: Cambio a modo tangente

```
Usuario: fun:tan
Servidor: Modo operacion tan

Usuario: 0
Servidor: Tan: 0.000000

Usuario: 0.7853981633974483
Servidor: Tan: 1.000000
```

### Ejemplo 4: Modo completo (todas las funciones)

```
Usuario: fun:
Servidor: Modo operacion sen, cos, tan

Usuario: 0
Servidor: Sen: 0.000000 Cos: 1.000000 Tan: 0.000000

Usuario: 1.5707963267948966
Servidor: Sen: 1.000000 Cos: 0.000000 Tan: 16331239353195370.000000
```

### Ejemplo 5: Secuencia completa del enunciado

```
Usuario: 0
Servidor: Cos: 1.000000

Usuario: 1.5707963267948966
Servidor: Cos: 0.000000

Usuario: fun:sin
Servidor: Modo operacion sin

Usuario: 0
Servidor: Sen: 0.000000
```

## Configuracion

### Modificar IP y Puerto

En `FunClient.java`:
```java
private static final String SERVER_HOST = "192.168.2.58";  // Cambiar IP
private static final int SERVER_PORT = 35000;              // Cambiar puerto
```

En `FunServer.java`:
```java
private static final int PORT = 35000;  // Cambiar puerto
```

##