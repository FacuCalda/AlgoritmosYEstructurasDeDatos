package dominio;

import interfaz.Retorno;

public class ABBGenerico<T extends Comparable<T>> {
    private NodoABB<T> raiz;
    private int cantidad;

    public int getCantidad() {
        return cantidad;
    }



    public void insertar(T dato) {
        if (raiz == null) {
            raiz = new NodoABB<T>(dato);
        } else {
            insertar(raiz, dato);
        }
    }

    private void insertar(NodoABB<T> nodo, T dato) {
        if (dato.compareTo(nodo.dato) < 0) {
            if (nodo.izq == null) {
                nodo.izq = new NodoABB(dato);
            } else {
                insertar(nodo.izq, dato);
            }
        } else if (dato.compareTo(nodo.dato) > 0) {
            if (nodo.der == null) {
                nodo.der = new NodoABB(dato);
            }else {
                insertar(nodo.der,dato);
            }
        }
    }

    public boolean pertenece(T dato){
        return pertenece(raiz,dato);
    }

    private boolean pertenece(NodoABB<T> nodo, T dato) {
        if(nodo!=null){
            if(nodo.dato.equals(dato))
                return true;
            else if(dato.compareTo(nodo.dato)<0){
                return pertenece(nodo.izq,dato);
            }else{
                return pertenece(nodo.der,dato);
            }
        }else{
            return false;
        }
    }

    public int contar() {
        return contar(raiz);
    }

    private int contar(NodoABB<T> nodo) {
        if (nodo == null) {
            return 0;
        }

        int contadorIzquierdo = contar(nodo.izq);
        int contadorDerecho = contar(nodo.der);

        return 1 + contadorIzquierdo + contadorDerecho;
    }

    public T obtener(T dato){
        return obtener(raiz,dato);
    }

    private T obtener(NodoABB<T> nodo, T dato) {
        if(nodo!=null){
            if(nodo.dato.equals(dato))
                return nodo.dato;
            else if(dato.compareTo(nodo.dato)<0){
                return obtener(nodo.izq,dato);
            }else{
                return obtener(nodo.der,dato);
            }
        }else{
            return null;
        }
    }


public NodoABB<T> CantidadNodos(T dato){
    return CantidadNodos(0,raiz,dato);
}
private NodoABB<T> CantidadNodos(int contador, NodoABB<T> nodo, T dato) {
    if(nodo!=null){
        if(nodo.dato.equals(dato)){
            this.cantidad=contador;
            return nodo;
        }
        else if(dato.compareTo(nodo.dato)<0){
            return CantidadNodos(contador+1,nodo.izq,dato);
        }else{
            return CantidadNodos(contador+1,nodo.der,dato);
        }
    }else{
        return null;
    }
}



    public String listarAscendente(){
        return listarAscendente(raiz);
    }

    private String listarAscendente(NodoABB<T> nodo) {
        if(nodo!=null){
            return listarAscendente(nodo.izq)  + nodo.dato + "|" + listarAscendente(nodo.der);
        }else {
            return "";
        }
    }

    public String listarDescendente(){
        return listarDescendente(raiz);
    }

    private String listarDescendente(NodoABB<T> nodo) {
        if(nodo!=null){
            return listarDescendente(nodo.der)  + nodo.dato + "|" + listarDescendente(nodo.izq);
        }else {
            return "";
        }
    }


    public class NodoABB<Q> {
        private Q dato;
        private NodoABB<Q> izq;
        private NodoABB<Q> der;

        public NodoABB(Q dato) {
            this.dato = dato;
        }

        public Q getDato(){
            return dato;
        }
    }
}
