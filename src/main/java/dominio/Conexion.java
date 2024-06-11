package dominio;

public class Conexion {

    private String codigoAeropuertoOrigen;
    private String codigoAeropuertoDestino;
    private double kilometros;
    private Lista<Vuelo> vuelos;


    public Lista<Vuelo> getVuelos() {
        return vuelos;
    }

    public String getCodigoAeropuertoOrigen() {
        return codigoAeropuertoOrigen;
    }

    public void setCodigoAeropuertoOrigen(String codigoAeropuertoOrigen) {
        this.codigoAeropuertoOrigen = codigoAeropuertoOrigen;
    }

    public String getCodigoAeropuertoDestino() {
        return codigoAeropuertoDestino;
    }

    public void setCodigoAeropuertoDestino(String codigoAeropuertoDestino) {
        this.codigoAeropuertoDestino = codigoAeropuertoDestino;
    }

    // Método para verificar si existe un vuelo con la aerolínea especificada
    public boolean existeVueloConAerolinea(String codigoAerolinea) {
      Lista<Vuelo>.NodoLista<Vuelo> aux= vuelos.getInicio();
        while (aux != null) {
            if (aux.getDato().getCodigoAerolinea().equals(codigoAerolinea)) {
                return true;
            }
            aux = aux.getSig();
        }
        return false;
    }
    public double getKilometros() {
        return kilometros;
    }

    public void setKilometros(double kilometros) {
        this.kilometros = kilometros;
    }

    public Conexion(String codigoAeropuertoOrigen, String codigoAeropuertoDestino, double kilometros) {
        this.codigoAeropuertoOrigen = codigoAeropuertoOrigen;
        this.codigoAeropuertoDestino = codigoAeropuertoDestino;
        this.kilometros = kilometros;
        vuelos= new Lista<>();
    }
}
