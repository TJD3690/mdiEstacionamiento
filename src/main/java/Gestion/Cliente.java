/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion;

/**
 *
 * @author LENOVO
 */
public class Cliente {
    private int idCliente;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;
    private int codNacio;
    private int codDistrito;
    private String direccion;
    private int codSexo;
    private String numDoc;
    private String USUARIO;
    private String numLicencia; 

    public void setNumLicencia(String numLicencia) {
        this.numLicencia = numLicencia;
    }

    public String getNumLicencia() {
        return numLicencia;
    }
    public int getIdCliente() {
        return idCliente;
    }

    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    public String getUSUARIO() {
        return USUARIO;
    }
    
    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public int getCodNacio() {
        return codNacio;
    }

    public int getCodDistrito() {
        return codDistrito;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getCodSexo() {
        return codSexo;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setIdCliente(int idCliente) { 
        this.idCliente = idCliente; }
    public void setNombres(String nombres) { 
        this.nombres = nombres; }
    public void setApellidos(String apellidos) { 
        this.apellidos = apellidos; }
    public void setTelefono(String telefono) {
        this.telefono = telefono; }
    public void setCorreo(String correo) {
        this.correo = correo; }
    public void setCodNacio(int codNacio) { 
        this.codNacio = codNacio; }
    public void setCodDistrito(int codDistrito) {
        this.codDistrito = codDistrito; }
    public void setDireccion(String direccion) { 
        this.direccion = direccion; }
    public void setCodSexo(int codSexo) {
        this.codSexo = codSexo; }
    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc; }
    
}

