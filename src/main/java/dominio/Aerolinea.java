package dominio;

import java.util.Objects;

public class Aerolinea implements Comparable<Aerolinea>{
    private String codigo;
    private String nombre;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Aerolinea(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
    public boolean validar(){
        if(codigo==null || codigo.isEmpty() || nombre==null || nombre.isEmpty()){
            return false;

        }
        return true;

    }
    @Override
    public int compareTo(Aerolinea o) {
        return this.codigo.compareTo(o.codigo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aerolinea aerolinea = (Aerolinea) o;
        return Objects.equals(codigo, aerolinea.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return
                codigo + ';' +
                nombre
               ;
    }
}
