package dominio;


public class NodoNivel {

    private int posVertice;
    private int nivel;


    public NodoNivel(int posVertice, int nivel, Aeropuerto verticeAnterior) {
        this.posVertice = posVertice;
        this.nivel = nivel;
    }

    public int getPosVertice() {
        return posVertice;
    }

    public void setPosVertice(int posVertice) {
        this.posVertice = posVertice;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
