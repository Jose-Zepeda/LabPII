package org.example;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.example.mdPersona;
public class Conector
{
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public void conexion()
    {
        //Cadena de conexión, contiene la informacion de la instalacion mongodb
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");

        //Se crean las configuraciones especificas para conexion y manejo de la db
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        //Crea la conexion y establece la comunicacion
        mongoClient = MongoClients.create(settings);

        //Busca la base de datos y la coleccion, si no existe, la crea

        database = mongoClient.getDatabase("dbCRUD");
        collection = database.getCollection("usuarios");

    }
    public void conectarMongoDB()
    {
        // Conexión a MongoDB
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        mongoClient = MongoClients.create(settings);

        // Obtener la base de datos y la colección
        database = mongoClient.getDatabase("PersonaCRUD");
        collection = database.getCollection("usuarios");
        System.out.println("Conectado a MongoDB");
    }

    public void insertarPersona() {
        //Pedir datos al usuario
        Scanner sc = new Scanner(System.in);

        System.out.println("Nombre:");
        String nombre = sc.nextLine();

        System.out.println("Edad:");
        int edad = sc.nextInt();

        // Consumir la nueva línea pendiente en el búfer del Scanner
        sc.nextLine();

        System.out.println("Ciudad:");
        String ciudad = sc.nextLine();

        //Crear un documento con los datos
        mdPersona usuario = new mdPersona();
        usuario.setNombre(nombre);
        usuario.setEdad(edad);
        usuario.setCiudad(ciudad);

        Document document = new Document("Nombre", usuario.getNombre())
                .append("Edad", usuario.getEdad())
                .append("Ciudad", usuario.getCiudad());

        collection.insertOne(document);
        System.out.println("Usuario creado exitosamente.");

    }
    public void mostrarPersonas()
    {
        List<mdPersona> usuarios = new ArrayList<>();

        //Leer todos los documentos de la coleccion
        for (Document doc : collection.find()){
            mdPersona usuario = new mdPersona();
            usuario.setNombre(doc.getString("Nombre"));
            usuario.setEdad(doc.getInteger("Edad"));
            usuario.setCiudad(doc.getString("Ciudad"));
            usuarios.add(usuario);


        System.out.printf("Usuarios en la base de datos: %d\n", usuarios.size());

            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("Edad: " + usuario.getEdad());
            System.out.println("Ciudad: " + usuario.getCiudad());
            System.out.println("__________________________");

        }
    }

    public void actualizarPersona() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese el nombre de la persona que quiere actualizar:");
        String nombre = sc.nextLine();

        System.out.println("Ingrese la nueva edad:");
        int edad = sc.nextInt();

        // Consumir el carácter de nueva línea
        sc.nextLine();

        System.out.println("Ingrese la nueva ciudad:");
        String ciudad = sc.nextLine();

        collection.updateOne(Filters.eq("Nombre", nombre),
                Updates.combine(
                        Updates.set("Edad", edad),
                        Updates.set("Ciudad", ciudad)
                ));
        System.out.println("Usuario actualizado");
    }


    public void eliminarPersonas()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el nombre de la persona que quiere eliminar:");
        String nombre = sc.nextLine();

        collection.deleteOne(Filters.eq("Nombre", nombre));
        System.out.println("Usuario eliminado");
    }

    public void desconectarMongoDB()
    {
        // Cerrar la conexión
        mongoClient.close();
        System.out.println("Desconectado de MongoDB");
    }


}
