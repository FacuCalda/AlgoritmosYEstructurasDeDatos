package dominio;

public class TuplaDijkastraRes {
    int valor;
    Lista resultado;


    public TuplaDijkastraRes(int valor, Lista resultado) {
        this.valor = valor;
        this.resultado = resultado;
    }

    public int getValor() {
        return valor;
    }



    public Lista getResultado() {
        return resultado;
    }

    public void setResultado(Lista resultado) {
        this.resultado = resultado;
    }
}
