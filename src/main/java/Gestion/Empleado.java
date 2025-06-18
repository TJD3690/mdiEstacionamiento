package Gestion;

public class Empleado {//O Usuario
    
    //----------------Atributos--------------
   private int IdEmpleado;
   private int CodNacio;
   private int CodSexo;
   private int CodDistrito;
   private String Nombres;
   private String Apellidos;
   private String Usuario;
   private String Password;
   private String NumDoc;
   private int Telefono;
   private String Correo;
   private String Direccion;
   
   
   //CONSTRUCTORES
   //Cons Sobrecargado
    public Empleado(int IdEmpleado, int CodNacio, int CodSexo, int CodDistrito, String Nombres, String Apellidos, String Usuario, String Password, String NumDoc, int Telefono, String Correo, String Direccion) {
        this.IdEmpleado = IdEmpleado;
        this.CodNacio = CodNacio;
        this.CodSexo = CodSexo;
        this.CodDistrito = CodDistrito;
        this.Nombres = Nombres;
        this.Apellidos = Apellidos;
        this.Usuario = Usuario;
        this.Password = Password;
        this.NumDoc = NumDoc;
        this.Telefono = Telefono;
        this.Correo = Correo;
        this.Direccion = Direccion;
    }
    public Empleado(){
        this.IdEmpleado = 0;
        this.CodNacio = 0;
        this.CodSexo = 0;
        this.CodDistrito = 0;
        this.Nombres = "";
        this.Apellidos = "";
        this.Usuario = "";
        this.Password = "";
        this.NumDoc = "";
        this.Telefono = 0;
        this.Correo = "";
        this.Direccion = "";
    }

    //Setters and getters

    public int getIdEmpleado() {
        return IdEmpleado;
    }

    public void setIdEmpleado(int IdEmpleado) {
        this.IdEmpleado = IdEmpleado;
    }

    public int getCodNacio() {
        return CodNacio;
    }

    public void setCodNacio(int CodNacio) {
        this.CodNacio = CodNacio;
    }

    public int getCodSexo() {
        return CodSexo;
    }

    public void setCodSexo(int CodSexo) {
        this.CodSexo = CodSexo;
    }

    public int getCodDistrito() {
        return CodDistrito;
    }

    public void setCodDistrito(int CodDistrito) {
        this.CodDistrito = CodDistrito;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String Nombres) {
        this.Nombres = Nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getNumDoc() {
        return NumDoc;
    }

    public void setNumDoc(String NumDoc) {
        this.NumDoc = NumDoc;
    }

    public int getTelefono() {
        return Telefono;
    }

    public void setTelefono(int Telefono) {
        this.Telefono = Telefono;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }
    
    
}
