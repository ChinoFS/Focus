package sample;

public class dateFashion {

    String nombre;
    String valorEsp;
    String descripcion;

    public dateFashion(String nombre, String valorEsp, String descripcion) {
        this.nombre = nombre;
        this.valorEsp = valorEsp;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValorEsp() {
        return valorEsp;
    }

    public void setValorEsp(String valorEsp) {
        this.valorEsp = valorEsp;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
