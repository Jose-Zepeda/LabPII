package org.example;

import java.util.Scanner;

public class Menu {



    public void menu() {
        Conector persona = new Conector();
        persona.conectarMongoDB();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Bienvenido al menú CRUD de MongoDB");
            System.out.println("1. Agregar nuevo usuario");
            System.out.println("2. Ver usuario");
            System.out.println("3. Actualizar datos de usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("5. Salir");
            System.out.print("Ingrese su elección: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consumir la línea vacía después del número

            switch (choice) {
                case 1:
                    System.out.println("Opción seleccionada: Agregar nuevo usuario");
                    persona.insertarPersona();
                    break;
                case 2:
                    System.out.println("Opción seleccionada: Ver usuario");
                    persona.mostrarPersonas();
                    break;
                case 3:
                    System.out.println("Opción seleccionada: Actualizar datos de usuario");
                    persona.actualizarPersona();
                    break;
                case 4:
                    System.out.println("Opción seleccionada: Eliminar usuario");
                    persona.eliminarPersonas();
                    break;
                case 5:
                    System.out.println("Saliendo del programa...");
                    persona.desconectarMongoDB();
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, ingrese un número válido.");
            }
        } while (choice != 5);

        scanner.close();
    }


}
