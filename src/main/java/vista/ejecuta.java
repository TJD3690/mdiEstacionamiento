package vista;

import conexion.Conexion;

public class ejecuta {
      public static void main(String[] args) {
        frmLogin m = new frmLogin();
        m.setVisible(true);
        
        Conexion objconex = new Conexion();
        objconex.establecerConexion();
    }
}