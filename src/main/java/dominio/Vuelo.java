package dominio;

import java.util.Objects;

public class Vuelo implements Comparable<Vuelo>{

    private String codigoAeropuertoOrigen;
    private String codigoAeropuertoDestino;
    private String codigoDeVuelo;
    private double combustible;
    private double minutos;
    private double costoEnDolares;
    private String codigoAerolinea;

    public String getCodigoAeropuertoOrigen() {
        return codigoAeropuertoOrigen;
    }


    public String getCodigoAeropuertoDestino() {
        return codigoAeropuertoDestino;
    }


    public String getCodigoDeVuelo() {
        return codigoDeVuelo;
    }


    public double getCombustible() {
        return combustible;
    }


    public double getMinutos() {
        return minutos;
    }


    public double getCostoEnDolares() {
        return costoEnDolares;
    }


    public String getCodigoAerolinea() {
        return codigoAerolinea;
    }



    public Vuelo(String codigoAeropuertoOrigen, String codigoAeropuertoDestino, String codigoDeVuelo, double combustible, double minutos, double costoEnDolares, String codigoAerolinea) {
        this.codigoAeropuertoOrigen = codigoAeropuertoOrigen;
        this.codigoAeropuertoDestino = codigoAeropuertoDestino;
        this.codigoDeVuelo = codigoDeVuelo;
        this.combustible = combustible;
        this.minutos = minutos;
        this.costoEnDolares = costoEnDolares;
        this.codigoAerolinea = codigoAerolinea;
    }

    @Override
    public int compareTo(Vuelo o) {
        return this.codigoDeVuelo.compareTo(o.codigoDeVuelo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vuelo vuelo = (Vuelo) o;
        return Objects.equals(codigoDeVuelo, vuelo.codigoDeVuelo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigoDeVuelo);
    }
}
