# CardUno - Juego UNO en Java Swing

## Descripción del Proyecto
CardUno es una aplicación desarrollada en Java que simula el juego de cartas UNO con interfaz gráfica usando Java Swing.

Permite interacción entre jugador humano y bots, aplicando reglas oficiales del juego como colores, números, cartas especiales, penalizaciones y control de turnos.

---

## Objetivo
- Implementar la lógica del juego UNO.
- Crear interfaz gráfica en Java Swing.
- Manejar turnos entre jugadores.
- Aplicar reglas de cartas especiales.
- Generar pruebas unitarias con JUnit 5.

---

## Tecnologías
- Java JDK 21
- Java Swing
- Eclipse IDE
- JUnit 5
- GitHub

---

## Funcionalidades

### Logica del juego
- Validación de cartas por color, número y tipo.
- Cartas especiales: Reversa, Salto, Roba 2, Roba 4, Comodín.
- Penalizaciones automáticas.
- Cambio de dirección.
- Control de turnos.

### Interfaz gráfica
- Visualización de cartas.
- Botón “UNO”.
- Historial de jugadas.
- Selección de cartas con botones.
- Configuración inicial del juego.

---

## Estructura del proyecto

```
src/
 └── uno/
     ├── Carta.java
     ├── ControlTurnos.java
     ├── Jugador.java
     ├── JugadorHumano.java
     ├── JugadorComputadora.java
     ├── Main.java
     ├── Mazo.java
     ├── ReglasJuego.java
     └── VentanaUno.java

test/
 └── uno/
     ├── CartaTest.java
     ├── ControlTurnosTest.java
     ├── ReglasJuegoTest.java
     ├── VentanaUnoTest.java
     └── MainTest.java
```

---

## Ejecución
1. Abrir el proyecto en Eclipse.
2. Ejecutar JuegoUnoEjecutable.jar

---

## Pruebas
Se realizaron pruebas con JUnit 5 para:

- Reglas del juego
- Turnos
- Penalizaciones
- Interfaz gráfica
- Validación de cartas


---

## Capturas

---

## JavaDoc
La documentación se encuentra en la carpeta:

```
doc/index.html
```

---

## Evolución del proyecto
- Inicio: versión en consola
- Migración a interfaz gráfica Swing
- Refactorización de clases
- Implementación de pruebas
- Generación de archivo .jar

---

## Integrantes
- González Ocampo Javier
- Martínez Guerrero Juan Carlos
- Orduña Luna Leonardo
- López Franceschy Felipe
- Martínez Vera María Izel

---

## Estado
✔ Proyecto funcional  
✔ Interfaz gráfica  
✔ Pruebas JUnit  
✔ Documentación  
✔ Versión final lista para entrega  
```


