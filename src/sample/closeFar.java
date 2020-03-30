package sample;

public class closeFar {
    String nombre;
    String valorEsp;
    String Desc;

    public closeFar(String nombre, String valorEsp, String desc) {
        this.nombre = nombre;
        this.valorEsp = valorEsp;
        Desc = desc;
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

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }
}
