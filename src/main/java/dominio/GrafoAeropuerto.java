package dominio;

public class GrafoAeropuerto {
    private Aeropuerto[] vertices;
    private Conexion[][] aristas;
    private final int maxVertices;

    int cantidad = 0;

    public int getCantidad() {
        return cantidad;
    }

    public GrafoAeropuerto(int maxVertices) {
        this.maxVertices = maxVertices;
        vertices = new Aeropuerto[maxVertices];
        aristas = new Conexion[maxVertices][maxVertices];
    }

    public void agregarVertice(String nombreVertice,String codigo) {
        if (cantidad < maxVertices) {
            int posLibre = obtenerPosLibre();
            vertices[posLibre] = new Aeropuerto(codigo,nombreVertice);
            cantidad++;
        }
    }

    public void borrarVertice(String nombreVertice,String Codigo) {
        int posVaBorrar = buscarPos(new Aeropuerto(Codigo,nombreVertice));

        for (int i = 0; i < aristas.length; i++) {
            aristas[posVaBorrar][i] = null;
            aristas[i][posVaBorrar] = null;
        }
        vertices[posVaBorrar] = null;
        cantidad--;
    }

    public void agregarArista(String vInicial, String vFinal, double kilometros) {
        int posVInicial = buscarPos(new Aeropuerto(vInicial,""));
        int posVFinal = buscarPos(new Aeropuerto(vFinal,""));

        aristas[posVInicial][posVFinal] = new Conexion(vInicial,vFinal,kilometros);
    }

    public void borrarArista(String vInicial, String vFinal) {
        int posVInicial = buscarPos(new Aeropuerto(vInicial,""));
        int posVFinal = buscarPos(new Aeropuerto(vFinal,""));

        aristas[posVInicial][posVFinal] = null;
    }

    public Conexion obtenerArista(String vInicial, String vFinal) {
        int posVInicial = buscarPos(new Aeropuerto(vInicial, ""));
        int posVFinal = buscarPos(new Aeropuerto(vFinal, ""));

        return aristas[posVInicial][posVFinal];
    }



    public boolean existeVertice(String codigo){
        int posABuscar= buscarPos(new Aeropuerto(codigo,""));
        return posABuscar>=0;
    }

    public Lista<Aeropuerto> adyacentes(String codigo){
        Lista<Aeropuerto> adyacentes=new Lista<>();
        int posV = buscarPos(new Aeropuerto(codigo,""));

        for (int i = 0; i < aristas.length; i++) {
            if(aristas[posV][i]!=null){
                adyacentes.insertar(vertices[i]);
            }
        }
        return adyacentes;
    }

    private int buscarPos(Aeropuerto v) {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i] != null && vertices[i].equals(v)) {
                return i;
            }
        }
        return -1;
    }

    private int obtenerPosLibre() {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public ABBGenerico<Aeropuerto> bfs(Aeropuerto vert, int cantidad,String codigoAerolinea){
        ABBGenerico<Aeropuerto> ret = new ABBGenerico<>();
        if(cantidad==0){
            ret.insertar(vertices[obtenerPos(vert)]);
        }else{
            int inicio = obtenerPos(vert);
            boolean[] visitados = new boolean[this.maxVertices];
            Cola<NodoNivel> cola = new Cola<>();
            NodoNivel nodoInicial = new NodoNivel(inicio, 0, vertices[obtenerPos(vert)]);
            visitados[inicio] = true;
            cola.encolar(nodoInicial);

            while(!cola.esVacia()){
                NodoNivel nodoNivel = cola.desencolar();
                ret.insertar(this.vertices[nodoNivel.getPosVertice()]);
                for (int i = 0; i < this.cantidad; i++) {
                    Conexion conexion = this.aristas[nodoNivel.getPosVertice()][i];
                    if (conexion != null && !visitados[i] && nodoNivel.getNivel() < cantidad) {
                        // Verificar si hay vuelos disponibles con la aerolínea especificada
                        boolean existeVueloConAerolinea = conexion.existeVueloConAerolinea(codigoAerolinea);

                        if (existeVueloConAerolinea) {
                            NodoNivel nodoNext = new NodoNivel(i, nodoNivel.getNivel() + 1, this.vertices[i]);
                            visitados[i] = true;
                            cola.encolar(nodoNext);
                        }
                    }
                }
            }
        }


        return ret;
    }

    private int obtenerPos(Aeropuerto v) {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i] != null && vertices[i].equals(v)) {
                return i;
            }
        }
        return -1;
    }
public TuplaDijkastraRes dijkstra(Aeropuerto aOrigen, Aeropuerto aDestino){
    int posOrigen = this.obtenerPos(aOrigen);
    int posDest = this.obtenerPos(aDestino);


    boolean[] visitados = new boolean[maxVertices];
    Aeropuerto[] vengo = new Aeropuerto[maxVertices];
    double[] costos = new double[maxVertices] ;

    for (int i = 0; i < maxVertices; i++) {
        costos[i]=  Double.MAX_VALUE;
        vengo[i]=null;
        visitados[i]=false;
    }
    costos[posOrigen]=0;

    for (int v = 0; v < this.cantidad; v++) {
        int pos = this.obtenerPosMenorNoVisitado(costos,visitados);
        if(pos!=-1){
            visitados[pos]=true;

            for (int i = 0; i < aristas.length; i++) {
                if (aristas[pos][i] != null) {
                    double distanciaNueva = costos[pos] + aristas[pos][i].getKilometros();
                    if (distanciaNueva < costos[i]) {
                        costos[i] = distanciaNueva;
                        vengo[i] = vertices[pos];
                    }
                }
            }

        }
    }
    Lista l = new Lista();
    listarAeropuertosSegunAeropuerto(l, vengo, posDest);
    return new TuplaDijkastraRes((int) costos[posDest],l);

}
    public void listarAeropuertosSegunAeropuerto(Lista listaAeropuerto, Aeropuerto[] anterior, int posDestino) {
        Aeropuerto aeropuerto = vertices[posDestino];
        listarAeropuertosSegunAeropuertoRec(listaAeropuerto, anterior, aeropuerto);
    }

    public void listarAeropuertosSegunAeropuertoRec(Lista listaAeropuerto, Aeropuerto[] anterior, Aeropuerto aeropuerto) {
        if (aeropuerto != null) {
            listaAeropuerto.insertar(aeropuerto);
            int aeropuertoPosicion = obtenerPosSegunCodigo(aeropuerto.getCodigo());
            Aeropuerto aeropuerto1 = anterior[aeropuertoPosicion];
            listarAeropuertosSegunAeropuertoRec(listaAeropuerto, anterior, aeropuerto1);

        }
    }
    public TuplaDijkastraRes ViajeMinimoMinutos(Aeropuerto aOrigen, Aeropuerto aDestino){
        int posOrigen = this.obtenerPos(aOrigen);
        int posDest = this.obtenerPos(aDestino);


        boolean[] visitados = new boolean[maxVertices];
        Aeropuerto[] vengo = new Aeropuerto[maxVertices];
        double[] costos = new double[maxVertices] ;

        for (int i = 0; i < maxVertices; i++) {
            costos[i]=  Double.MAX_VALUE;
            vengo[i]=null;
            visitados[i]=false;
        }
        costos[posOrigen]=0;

        for (int v = 0; v < this.cantidad; v++) {
            int pos = this.obtenerPosMenorNoVisitado(costos,visitados);
            if(pos!=-1){
                visitados[pos]=true;

                for (int i = 0; i < aristas.length; i++) {
                    if (aristas[pos][i] != null) {
                        double distanciaNueva = costos[pos] + aristas[pos][i].getVuelos().buscarVueloMasCorto();
                        if (distanciaNueva < costos[i]) {
                            costos[i] = distanciaNueva;
                            vengo[i] = vertices[pos];
                        }
                    }
                }

            }
        }
        Lista l = new Lista();
        listarAeropuertosSegunAeropuerto(l, vengo, posDest);

        return new TuplaDijkastraRes((int) costos[posDest],l);

    }

    //Obtener la posicion del aeropuerto por su codigo
    public int obtenerPosSegunCodigo(String codigoAeropuerto) {
        for (int i = 0; i < maxVertices; i++) {
            if (vertices[i] != null) {
                if (vertices[i].getCodigo().equals(codigoAeropuerto)) {
                    return i;
                }
            }
        }
        return -1;
    }


    private int obtenerPosMenorNoVisitado(double[] costos, boolean[] visitados) {
        int posMin = -1;
        double min = Integer.MAX_VALUE;
        for (int i = 0; i < this.maxVertices; i++) {
            if(!visitados[i] && costos[i]< min){
                min = costos[i];
                posMin = i;
            }
        }
        return posMin;
    }
}
