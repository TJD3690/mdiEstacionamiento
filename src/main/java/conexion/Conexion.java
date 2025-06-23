package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Conexion {

    static Connection conectar= null;
    
    static String usuario = "UserSQL_T";
    static String contrasenia = "#Tania002244";
    static String baseDatos= "EstacionamientoPRUEBA";
    static String ip= "localhost";
    static String puerto="1433";
    
    String cadena = "jdbc:sqlserver://"+ip+":"+puerto+"/"+baseDatos;
    //si seras
    public static Connection establecerConexion() {
    try {
        String cadena = "jdbc:sqlserver://localhost:1433;databaseName=" + baseDatos + ";encrypt=true;trustServerCertificate=true";
        conectar = DriverManager.getConnection(cadena, usuario, contrasenia);
       // JOptionPane.showMessageDialog(null, "Se conectó correctamente a la base de datos '" + baseDatos + "'");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos '" + baseDatos + "': " + e.getMessage());
        e.printStackTrace();
    }
    return conectar;
    }
}
