package sample;

public class wordMultiple {

    String nombre;
    String valorEsp;
    String desc;

    public wordMultiple(String nombre, String valorEsp, String desc) {
        this.nombre = nombre;
        this.valorEsp = valorEsp;
        this.desc = desc;
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
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
