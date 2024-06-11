package dominio;

import java.util.Iterator;

public class Lista<T> {

    private NodoLista<T> inicio;
    private int largo;

    public Lista() {
        this.inicio = null;
        this.largo = 0;
    }

    public NodoLista<T> getInicio() {
        return inicio;
    }

    public void insertar(T dato) {
        inicio = new NodoLista<T>(dato, inicio);
        largo++;
    }

    public int largo() {
        return largo;
    }
    public boolean existeVueloConCodigo(String codigoDeVuelo) {
        NodoLista<T> aux = inicio;
        while (aux != null) {
            if (aux.dato instanceof Vuelo) {
                Vuelo vuelo = (Vuelo) aux.dato;
                if (vuelo.getCodigoDeVuelo().equals(codigoDeVuelo)) {
                    return true;
                }
            }
            aux = aux.sig;
        }
        return false;
    }

    public boolean existe(T dato) {
        NodoLista<T> aux = inicio;
        while (aux != null) {
            if (aux.dato.equals(dato)) {
                return true;
            }
            aux = aux.sig;
        }
        return false;
    }

    public T recuperar(T dato) {
        NodoLista<T> aux = inicio;
        while (aux != null) {
            if (aux.dato.equals(dato)) {
                return aux.dato;
            }
            aux = aux.sig;
        }
        return null;
    }

    public boolean esVacia() {
        return largo == 0;
    }


    public void imprimirDatos() {
        NodoLista<T> aux = inicio;
        while (aux != null) {
            if (aux.sig != null){
                System.out.print(aux.dato + " -> ");
            }else{
                System.out.print(aux.dato);
            }
            aux = aux.sig;
        }
        System.out.println();
    }

    public String toStringAeropuerto() {

        NodoLista<Aeropuerto> aux = (NodoLista<Aeropuerto>) inicio;

        String string = "";
        while (aux != null) {
            string += aux.getDato().getCodigo() + ";" +
                    aux.getDato().getNombre() + "|";
            aux = aux.getSig();
        }
        return string;
    }
    public int buscarVueloMasCorto(){
        Lista<Vuelo>.NodoLista<Vuelo> vuelo= (Lista<Vuelo>.NodoLista<Vuelo>) inicio;
        int costoMasBajo = Integer.MAX_VALUE;
        while (vuelo != null){
            if(vuelo.getDato().getMinutos() < costoMasBajo){
                costoMasBajo = (int)vuelo.getDato().getMinutos();
            }
            vuelo = vuelo.getSig();
        }
        return costoMasBajo;
    }
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private NodoLista<T> aux = inicio;

            @Override
            public boolean hasNext() {
                return aux != null;
            }

            @Override
            public T next() {
                T dato = aux.dato;
                aux = aux.sig;
                return dato;
            }

            @Override
            public void remove() {
            }

        };
    }



    public class NodoLista<T> {
        private T dato;
        private NodoLista<T> sig;

        public NodoLista(T dato) {
            this.dato = dato;
            this.sig = null;
        }

        public NodoLista<T> getSig() {
            return sig;
        }

        public T getDato() {
            return dato;
        }

        public NodoLista(T dato, NodoLista<T> sig) {
            this.dato = dato;
            this.sig = sig;
        }

        @Override
        public String toString() {
            return dato.toString();
        }
    }
}
