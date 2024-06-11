package sistema;

import dominio.*;
import interfaz.*;

public class ImplementacionSistema implements Sistema {
    ABBGenerico<Pasajero> ABBPasajeros;
    ABBGenerico<Pasajero> ABBPasajerosPlatino;
    ABBGenerico<Pasajero> ABBPasajerosFrecuente;
    ABBGenerico<Pasajero> ABBPasajerosEstandar;
    ABBGenerico<Aerolinea> ABBAerolineas;
    GrafoAeropuerto grafoAeropuertos;
    int maxAerolinea;
    int maxAeropuerto;

    @Override
    public Retorno inicializarSistema(int maxAeropuertos, int maxAerolineas) {
        if (maxAeropuertos <= 5) return Retorno.error1("");
        if (maxAerolineas <= 3) return Retorno.error2("");
        ABBPasajeros = new ABBGenerico<Pasajero>();
        ABBPasajerosPlatino = new ABBGenerico<Pasajero>();
        ABBPasajerosFrecuente = new ABBGenerico<Pasajero>();
        ABBPasajerosEstandar = new ABBGenerico<Pasajero>();


        ABBAerolineas = new ABBGenerico<Aerolinea>();
        maxAerolinea = maxAerolineas;
        maxAeropuerto = maxAeropuertos;
        grafoAeropuertos = new GrafoAeropuerto(maxAeropuertos);

        //mas estructuras
        return Retorno.ok();


    }

    @Override
    public Retorno registrarPasajero(String cedula, String nombre, String telefono, Categoria categoria) {
        Pasajero p1 = new Pasajero(cedula, nombre, telefono, categoria);
        if (!p1.validar()) return Retorno.error1("");
        if (!p1.validarCedula()) return Retorno.error2("");
        if (ABBPasajeros.pertenece(p1)) return Retorno.error3("");
        ABBPasajeros.insertar(p1);

        switch (categoria.getTexto()) {
            case "Platino":
                ABBPasajerosPlatino.insertar(p1);
                break;
            case "Frecuente":
                ABBPasajerosFrecuente.insertar(p1);
                break;
            case "Estándar":
                ABBPasajerosEstandar.insertar(p1);
                break;

        }
        return Retorno.ok();
    }

    @Override
    public Retorno buscarPasajero(String cedula) {
        Pasajero p1 = new Pasajero(cedula);
        if (cedula == null || cedula.isEmpty()) return Retorno.error1("");
        if (!p1.validarCedula()) return Retorno.error2("");
        if (!ABBPasajeros.pertenece(p1)) return Retorno.error3("");

        Pasajero p2= ABBPasajeros.CantidadNodos(p1).getDato();
        return Retorno.ok(ABBPasajeros.getCantidad(),p2.toString());
    }

    @Override
    public Retorno listarPasajerosAscendente() {
        String ret = ABBPasajeros.listarAscendente();
        if (ret.length() >= 2) {
            String textoSinUltimosCaracteres = ret.substring(0, ret.length() - 1);
            return Retorno.ok(textoSinUltimosCaracteres);
        } else {

            return Retorno.ok(ret);
        }
    }

    @Override
    public Retorno listarPasajerosPorCategoria(Categoria categoria) {
        String viajerosDelTipo = "";

        if (categoria != null) {
            if (categoria.getTexto().equals("Platino")) {
                viajerosDelTipo = ABBPasajerosPlatino.listarAscendente();
            } else if (categoria.getTexto().equals("Frecuente")) {
                viajerosDelTipo = ABBPasajerosFrecuente.listarAscendente();
            } else if (categoria.getTexto().equals("Estándar")) {
                viajerosDelTipo = ABBPasajerosEstandar.listarAscendente();
            }
            if (!viajerosDelTipo.isEmpty() && viajerosDelTipo.endsWith("|")) {
                viajerosDelTipo = viajerosDelTipo.substring(0, viajerosDelTipo.length() - 1);
            }
            return Retorno.ok(viajerosDelTipo);
        }
        return Retorno.error1("");
    }

    @Override
    public Retorno registrarAerolinea(String codigo, String nombre) {
        if (ABBAerolineas.contar() >= maxAerolinea) return Retorno.error1("");
        Aerolinea a1 = new Aerolinea(codigo, nombre);
        if (!a1.validar()) {
            return Retorno.error2("");
        }
        if (ABBAerolineas.pertenece(a1)) return Retorno.error3("");
        ABBAerolineas.insertar(a1);
        return Retorno.ok();
    }

    @Override
    public Retorno listarAerolineasDescendente() {
        String ret = ABBAerolineas.listarDescendente();
        if (ret.length() >= 2) {
            String textoSinUltimosCaracteres = ret.substring(0, ret.length() - 1);
            return Retorno.ok(textoSinUltimosCaracteres);
        } else {
            return Retorno.ok(ret);
        }
    }

    @Override
    public Retorno registrarAeropuerto(String codigo, String nombre) {

        if (maxAeropuerto == grafoAeropuertos.getCantidad()) return Retorno.error1("");
        if (codigo == null || codigo.isEmpty() || nombre == null || nombre.isEmpty()) return Retorno.error2("");
        Aeropuerto a1 = new Aeropuerto(codigo, nombre);
        if (grafoAeropuertos.existeVertice(a1.getCodigo())) {
            return Retorno.error3("");
        }
        grafoAeropuertos.agregarVertice(nombre, codigo);
        return Retorno.ok();
    }

    @Override
    public Retorno registrarConexion(String codigoAeropuertoOrigen, String codigoAeropuertoDestino, double kilometros) {
        if (kilometros <= 0) return Retorno.error1("");
        if (codigoAeropuertoDestino == null || codigoAeropuertoDestino.isEmpty() || codigoAeropuertoOrigen == null || codigoAeropuertoOrigen.isEmpty())
            return Retorno.error2("");
        Aeropuerto aero = new Aeropuerto(codigoAeropuertoOrigen, "");
        if (!grafoAeropuertos.existeVertice(aero.getCodigo())) return Retorno.error3("");
        Aeropuerto aero2 = new Aeropuerto(codigoAeropuertoDestino, "");
        if (!grafoAeropuertos.existeVertice(aero2.getCodigo())) return Retorno.error4("");
        if (grafoAeropuertos.obtenerArista(codigoAeropuertoOrigen, codigoAeropuertoDestino) != null)
            return Retorno.error5("");
        grafoAeropuertos.agregarArista(codigoAeropuertoOrigen, codigoAeropuertoDestino, kilometros);
        return Retorno.ok();
    }

    @Override
    public Retorno registrarVuelo(String codigoAeropuertoOrigen, String codigoAeropuertoDestino,
                                  String codigoDeVuelo, double combustible, double minutos,
                                  double costoEnDolares, String codigoAerolinea) {
        if (combustible <= 0 || minutos <= 0 || costoEnDolares <= 0) return Retorno.error1("");
        if (codigoAeropuertoOrigen == null || codigoAeropuertoOrigen.isEmpty() || codigoAeropuertoDestino == null || codigoAeropuertoDestino.isEmpty() || codigoDeVuelo == null || codigoDeVuelo.isEmpty() || codigoAerolinea == null || codigoAerolinea.isEmpty()) {
            return Retorno.error2("");
        }
        Aeropuerto aero = new Aeropuerto(codigoAeropuertoOrigen, "");
        if (!grafoAeropuertos.existeVertice(aero.getCodigo())) return Retorno.error3("");
        Aeropuerto aero2 = new Aeropuerto(codigoAeropuertoDestino, "");
        if (!grafoAeropuertos.existeVertice(aero2.getCodigo())) return Retorno.error4("");
        Aerolinea aerolinea = new Aerolinea(codigoAerolinea, "");
        if (ABBAerolineas.obtener(aerolinea) == null) return Retorno.error5("");
        Conexion conexion = grafoAeropuertos.obtenerArista(codigoAeropuertoOrigen, codigoAeropuertoDestino);
        if (conexion == null) return Retorno.error6("");
        Vuelo vuelo = new Vuelo(codigoAeropuertoOrigen, codigoAeropuertoDestino, codigoDeVuelo, combustible, minutos,
                costoEnDolares, codigoAerolinea);
        if (conexion.getVuelos().existe(vuelo)) {
            return Retorno.error7("");
        }
        conexion.getVuelos().insertar(vuelo);
        return Retorno.ok();
    }

    @Override
    public Retorno listadoAeropuertosCantDeEscalas(String codigoAeropuertoOrigen, int cantidad, String codigoAerolinea) {
        if (cantidad < 0) return Retorno.error1("");
        Aeropuerto aero = new Aeropuerto(codigoAeropuertoOrigen, "");
        if (!grafoAeropuertos.existeVertice(aero.getCodigo())) return Retorno.error2("");
        Aerolinea aerolinea = new Aerolinea(codigoAerolinea, "");
        if (ABBAerolineas.obtener(aerolinea) == null) return Retorno.error3("");

        String ret = grafoAeropuertos.bfs(aero, cantidad, codigoAerolinea).listarAscendente();
        if (ret.length() >= 2) {

            String textoSinUltimosCaracteres = ret.substring(0, ret.length() - 1);
            return Retorno.ok(textoSinUltimosCaracteres);
        } else {
            return Retorno.ok(ret);
        }

    }

    @Override
    public Retorno viajeCostoMinimoKilometros(String codigoAeropuertoOrigen, String codigoAeropuertoDestino) {

        if (codigoAeropuertoOrigen == null || codigoAeropuertoOrigen.isEmpty() || codigoAeropuertoDestino == null || codigoAeropuertoDestino.isEmpty())
            return Retorno.error1("");


        Aeropuerto aero = new Aeropuerto(codigoAeropuertoOrigen, "");
        boolean eAeropuertoOrig = grafoAeropuertos.existeVertice(codigoAeropuertoOrigen);
        if (!eAeropuertoOrig) return Retorno.error3("");
        Aeropuerto aero2 = new Aeropuerto(codigoAeropuertoDestino, "");
        boolean eAeropuertoDest = grafoAeropuertos.existeVertice(codigoAeropuertoDestino);
        if (!eAeropuertoDest) return Retorno.error4("");
        int costominimo=grafoAeropuertos.dijkstra(aero,aero2).getValor();
        if(costominimo==Integer.MAX_VALUE)return Retorno.error2("");

        String ret = grafoAeropuertos.dijkstra(aero,aero2).getResultado().toStringAeropuerto();
        ret = ret.substring(0,ret.length()-1);

        return Retorno.ok(costominimo,ret);
    }

    @Override
    public Retorno viajeCostoMinimoEnMinutos(String codigoAeropuertoOrigen, String codigoAeropuertoDestino) {
        if (codigoAeropuertoOrigen == null || codigoAeropuertoOrigen.isEmpty() || codigoAeropuertoDestino == null || codigoAeropuertoDestino.isEmpty())
            return Retorno.error1("");


        Aeropuerto aero = new Aeropuerto(codigoAeropuertoOrigen, "");
        boolean eAeropuertoOrig = grafoAeropuertos.existeVertice(codigoAeropuertoOrigen);
        if (!eAeropuertoOrig) return Retorno.error3("");
        Aeropuerto aero2 = new Aeropuerto(codigoAeropuertoDestino, "");
        boolean eAeropuertoDest = grafoAeropuertos.existeVertice(codigoAeropuertoDestino);
        if (!eAeropuertoDest) return Retorno.error4("");
        int costominimo=grafoAeropuertos.ViajeMinimoMinutos(aero,aero2).getValor();
        if(costominimo==Integer.MAX_VALUE)return Retorno.error2("no hay camino");

        String ret = grafoAeropuertos.ViajeMinimoMinutos(aero,aero2).getResultado().toStringAeropuerto();
        ret = ret.substring(0,ret.length()-1);

        return Retorno.ok(costominimo,ret);
    }


}
