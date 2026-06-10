# Sistema de Alquiler de Películas

Aplicación de consola desarrollada en Java con conexión a SQL Server, aplicando principios de Programación Orientada a Objetos, patrón DAO y separación de responsabilidades por capas.

---

## Tecnologías

- Java 21+
- SQL Server (motor de base de datos)
- JDBC (Microsoft SQL Server Driver)
- IntelliJ IDEA

---

## Estructura del proyecto

```
src/
├── config/
│   └── ConexionBD.java
├── dao/
│   ├── AlquilerDAO.java
│   ├── CategoriaDAO.java
│   ├── ClienteDAO.java
│   └── PeliculaDAO.java
├── interfaces/
│   └── dao/
│       ├── IBuscable.java
│       └── IRegistrable.java
├── model/
│   ├── Alquiler.java
│   ├── Categoria.java
│   ├── Cliente.java
│   ├── DetalleAlquiler.java
│   ├── Pelicula.java
│   ├── Persona.java
│   └── Producto.java
├── service/
│   ├── AlquilerService.java
│   ├── CategoriaService.java
│   ├── ClienteService.java
│   └── PeliculaService.java
└── view/
    ├── AlquilerView.java
    ├── ClienteView.java
    ├── MainView.java
    └── PeliculaView.java
```

---

## Arquitectura por capas

El proyecto está dividido en cuatro capas con responsabilidades bien definidas:

| Capa      | Responsabilidad                                 |
| --------- | ----------------------------------------------- |
| `model`   | Representa las entidades del dominio            |
| `dao`     | Acceso directo a la base de datos mediante JDBC |
| `service` | Validaciones y reglas de negocio                |
| `view`    | Interacción con el usuario por consola          |

Cada capa solo conoce a la inmediatamente inferior. Las vistas no acceden al DAO directamente, y los DAOs no contienen lógica de negocio.

---

## Modelo de datos

```
CATEGORIA (1) ──> (N) PELICULA
CLIENTE   (1) ──> (N) ALQUILER
ALQUILER  (1) ──> (N) DETALLE_ALQUILER
PELICULA  (1) ──> (N) DETALLE_ALQUILER
```

---

## Jerarquía de clases (POO)

### Persona -> Cliente

```
Persona (abstracta)
├── nombres, apellidos, telefono, email
├── mostrarDetalle()  [concreto — datos comunes]
└── Cliente
    ├── idCliente, cedula, direccion, fechaReg
    └── mostrarDetalle()  [override — agrega datos propios con super()]
```

### Producto -> Pelicula

```
Producto (abstracta)
├── nombre, precio, stock
├── validarStock(int cantidad) -> boolean
├── mostrarDetalle()  [concreto — datos comunes]
└── Pelicula
    ├── idPelicula, idCategoria, director, anioEstreno
    └── mostrarDetalle()  [override — agrega datos propios con super()]
```

Ambas jerarquías demuestran herencia, encapsulamiento y polimorfismo. El método `mostrarDetalle` / `mostrarInfo` reutiliza la implementación del padre mediante `super` y extiende con los atributos propios de la subclase.

---

## Interfaces

### IBuscable\<T>

```java
T buscarPorId(int id);
T buscarPorNombre(String nombre);
```

Implementada por `ClienteDAO`, `PeliculaDAO` y `CategoriaDAO`.

### IRegistrable\<T>

```java
boolean registrar(T entidad);
List<T> listarTodos();
```

Implementada por `ClienteDAO`, `PeliculaDAO` y `AlquilerDAO`.

---

## Conexión a la base de datos

`ConexionBD` implementa el patrón Singleton — solo existe una instancia de la conexión durante toda la ejecución. La URL se construye por partes para facilitar su mantenimiento:

```java
private static final String HOST     = "localhost";
private static final int    PORT     = 1433;
private static final String DATABASE = "db_alquiler_peliculas";

private static final String URL = String.format(
    "jdbc:sqlserver://%s:%d;databaseName=%s;encrypt=true;trustServerCertificate=true",
    HOST, PORT, DATABASE
);
```

Si la conexión es nula o está cerrada, se abre una nueva. De lo contrario, se reutiliza la existente.

---

## Manejo de recursos JDBC

Todos los métodos de los DAOs utilizan `try-with-resources` para garantizar el cierre automático de `Connection` y `PreparedStatement`, incluso ante excepciones:

```java
try (Connection con = ConexionBD.getConexion();
     PreparedStatement ps = con.prepareStatement(sql)) {

    // uso de ps
    ResultSet rs = ps.executeQuery();

} catch (Exception e) {
    System.out.println("Error: " + e.getMessage());
}
```

`Connection`, `PreparedStatement` y `ResultSet` se declaran como variables locales, no como atributos de instancia, para evitar fugas de memoria y conexiones colgadas.

---

## Transacciones (AlquilerDAO)

El registro de un alquiler involucra tres operaciones que deben ejecutarse de forma atómica: insertar la cabecera en `alquiler`, insertar cada fila en `detalle_alquiler`, y descontar el stock en `pelicula`. Si cualquiera falla, se revierte todo.

```java
con.setAutoCommit(false); // BEGIN TRAN

// 1. INSERT alquiler  -> obtener id generado
// 2. INSERT detalle_alquiler (por cada pelicula)
// 3. UPDATE pelicula SET stock = stock - cantidad

con.commit();   // COMMIT

// En caso de excepcion:
con.rollback(); // ROLLBACK
```

La conexión en este método se maneja manualmente con `finally` en lugar de `try-with-resources`, porque necesita `commit` / `rollback` antes de cerrarse.

---

## Inyección de dependencias por constructor

Ninguna clase crea sus propias dependencias internamente. Todas las reciben por constructor, lo que reduce el acoplamiento y centraliza la construcción del grafo de objetos en `MainView`:

```java
// MainView.java
CategoriaDAO categoriaDAO   = new CategoriaDAO();
ClienteDAO   clienteDAO     = new ClienteDAO();
PeliculaDAO  peliculaDAO    = new PeliculaDAO();
AlquilerDAO  alquilerDAO    = new AlquilerDAO();

CategoriaService categoriaService = new CategoriaService(categoriaDAO);
ClienteService   clienteService   = new ClienteService(clienteDAO);
PeliculaService  peliculaService  = new PeliculaService(peliculaDAO);
AlquilerService  alquilerService  = new AlquilerService(alquilerDAO, clienteDAO, peliculaDAO);

this.clienteView  = new ClienteView(sc, clienteService);
this.peliculaView = new PeliculaView(sc, peliculaService, categoriaService);
this.alquilerView = new AlquilerView(sc, alquilerService, peliculaService);
```

---

## Scanner compartido

Se instancia un único `Scanner` en `MainView` y se pasa por constructor a todas las vistas. Abrir múltiples `Scanner` sobre `System.in` provoca comportamientos inesperados en el buffer de entrada.

Todas las lecturas de consola usan `sc.nextLine().trim()` en lugar de `sc.nextInt()` o `sc.nextDouble()`, y los valores numéricos se parsean con `Integer.parseInt` / `Double.parseDouble` dentro de bloques `try-catch` para manejar entradas inválidas sin detener la aplicación.

---

## Reglas de negocio

| Regla                               | Implementación                                                  |
| ----------------------------------- | --------------------------------------------------------------- |
| No alquilar con stock = 0           | `Producto.validarStock()` + `AlquilerService.validarPelicula()` |
| Descontar stock al confirmar        | `UPDATE` dentro de la transacción en `AlquilerDAO`              |
| Fecha de devolución posterior a hoy | `AlquilerService.validarFechaDevolucion()`                      |
| Cédula única por cliente            | `ClienteService` consulta al DAO antes de insertar              |
| Total = suma de subtotales          | `Alquiler.calcularTotal()` itera sobre los detalles             |
| Estado inicial = ACTIVO             | Asignado en el constructor de `Alquiler`                        |

---

## Consulta de alquileres por cliente

Al listar los alquileres de un cliente, se realiza un JOIN entre `alquiler`, `cliente`, `detalle_alquiler` y `pelicula`. Como el JOIN produce una fila por cada detalle, un `LinkedHashMap<Integer, Alquiler>` agrupa los detalles bajo su alquiler correspondiente usando `id_alquiler` como clave:

```
Fila 1: id_alquiler=1, pelicula="John Wick 4"  -> crea Alquiler(1), agrega detalle
Fila 2: id_alquiler=1, pelicula="Barbie"        -> Alquiler(1) ya existe, agrega detalle
Fila 3: id_alquiler=1, pelicula="Oppenheimer"   -> Alquiler(1) ya existe, agrega detalle
```

`LinkedHashMap` se usa en lugar de `HashMap` para preservar el orden de inserción en el resultado final.

---

## Formato de salida (toString)

Todos los modelos siguen el mismo formato en `toString`:

```
=== ENTIDAD ===
Campo1: valor
Campo2: valor
...
```

Los valores monetarios usan `String.format("%.2f", valor)` para garantizar siempre dos decimales, independientemente de la representación interna del `double`.
