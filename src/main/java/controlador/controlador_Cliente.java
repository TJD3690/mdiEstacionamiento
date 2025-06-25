package controlador;

import Gestion.Cliente;
import conexion.Conexion;
import java.sql.*;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LENOVO
 */
public class controlador_Cliente {
    
    public boolean guardar(Cliente cliente) {
    Connection con = null;
    PreparedStatement ps = null;
    try {
        con = Conexion.establecerConexion();
        String sql = "INSERT INTO Cliente (idCliente, CodNacio, CodDistrito, nombres, apellidos, NumDoc, telefono, correo, direccion, usuario, CodSexo, NroLicencia) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = con.prepareStatement(sql);
        ps.setInt(1, cliente.getIdCliente());
        ps.setInt(2, cliente.getCodNacio());
        ps.setInt(3, cliente.getCodDistrito());
        ps.setString(4, cliente.getNombres());
        ps.setString(5, cliente.getApellidos());
        ps.setString(6, cliente.getNumDoc());
        ps.setString(7, cliente.getTelefono());
        ps.setString(8, cliente.getCorreo());
        ps.setString(9, cliente.getDireccion());
        ps.setString(10, cliente.getUSUARIO());
        ps.setInt(11, cliente.getCodSexo());
        ps.setString(12, cliente.getNumLicencia()); // Añadir esta línea
        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error en la base de datos: " + e.getMessage());
        return false;
    } finally {
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar conexión: " + e.getMessage());
        }
    }
}
    
}
