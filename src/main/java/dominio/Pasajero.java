package dominio;

import interfaz.Categoria;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pasajero implements Comparable<Pasajero> {
    private String cedula;
    private String nombre;
    private String telefono;
    private Categoria categoria;

    // Constructor
    public Pasajero(String cedula, String nombre, String telefono, Categoria categoria) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.categoria = categoria;
    }

    public Pasajero(String cedula){
        this.cedula = cedula;
        this.nombre = "";
        this.telefono = "";
        this.categoria = null;
    }

    // Getters y Setters
    public String getCedula() {
        return cedula;
    }



    public String getNombre() {
        return nombre;
    }



    public String getTelefono() {
        return telefono;
    }



    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pasajero pasajero = (Pasajero) o;
        return Objects.equals(cedula, pasajero.cedula);
    }

    public int compareTo(Pasajero otroPasajero) {
        return this.cedula.compareTo(otroPasajero.cedula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }


    @Override
    public String toString() {
        return cedula + ';' +
                nombre + ';' + telefono + ';' + categoria.getTexto();
    }
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public boolean validar() {
        if(isNullOrEmpty(cedula)||isNullOrEmpty(nombre)||isNullOrEmpty(telefono)||getCategoria()==null){
            return false;
        }
        return true;
    }
    public boolean validarCedula(){
        char primerCaracter=  cedula.charAt(0);

        if(Character.isDigit(primerCaracter) && primerCaracter!='0'){
            String regex = "\\d{1,3}(\\.\\d{3})?\\.\\d{3}-\\d";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(cedula);
            return matcher.matches();
        }else {
            return false;

        }
    }
}
