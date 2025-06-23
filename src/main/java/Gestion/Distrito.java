package Gestion;

/**
 *
 * @author LENOVO
 */
public class Distrito {
    private int id;
    private String nombre;

    public Distrito(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;   // esto se mostrará en el combo
    }
    
}
