/**
 * Archivo del proyecto fuego-quasar
 * Esta clase ecapsula la funcionalidad de obtener localizacón de un punto a partir de su distancia a tres puntos 
 * conocidos, utilizando el metodo matematico de Trilateración (https://es.wikipedia.org/wiki/Trilateraci%C3%B3n).
 * Esta clase encapsula la funcionalidad de obtener y decifrar un mensaje originado por un emisor desconocido y 
 * recibido por tres receptores conocidos  
 *  
 * --------------------------------------
 * Nombre del archivo: MansajeApi.java
 * Paquete del archivo: com.triangulacion.servicios
 * @author Matby Peralta Davila
 * @version 1.0
 */

package com.triangulacion.servicios;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.triangulacionapi.utilidades.Util;

public class MensajeApi implements Mensaje {

	/**
	 * Metodo encargado de localizar un punto desconocido en el sistema de
	 * coordenadas (X,Y), a partir de la distancia entre el punto desconocido y tres
	 * puntos conocidos
	 * 
	 * @param distances, arreglo con las distancias entre el punto desconocido y los
	 *                   tres puntos conocidos @return, retorna la ubicación en las
	 *                   coordendas (X,Y) del punto desconocido @exception, Lanza
	 *                   excepción si las distancias ingresadas no son reales o no
	 *                   concuerdan con un mismo punto
	 * 
	 */
	@Override
	public double[] getLocation(double... distances) throws Exception {

		// coordenadas satelites
		double[][] coordenadas = new double[3][2];
		coordenadas[0][0] = 100;// x skywalker
		coordenadas[0][1] = -100;// y skywalker
		coordenadas[1][0] = 500;// x sato
		coordenadas[1][1] = 100;// y sato
		coordenadas[2][0] = -500;// x kenobi
		coordenadas[2][1] = -200;// y kenobi

		// se trasladan las coordenadas en X y Y en funcion del satelite skywalker
		// ubicandolo en el origen de coordenadas.
		// Todos los satelites se mueven -100 en X y 100 en Y
		double[][] coordenadasTrasladadas = Util.trasladarEjes(coordenadas);
		// se rota las coordenadas en funcion del satelite sato, el cual se lleva al eje
		// X rotandolo el angulo conformado entre el eje X y la posición de sato
		double[][] coordenadasRotadas = Util.rotarEjes(coordenadasTrasladadas);

		double d1 = distances[0];// distancia a kenobi
		double d2 = distances[1];// distancia a skywalker
		double d3 = distances[2];// distancia a sato

		// distancia entre skywalker y sato
		double dss = Util.distanciaEntrePuntos(coordenadasRotadas[0][0], coordenadasRotadas[0][1],
				coordenadasRotadas[1][0], coordenadasRotadas[1][1]);

		// coordenada X con la formula que resulta de de la intersección entre skywalker
		// y sato
		// x = (d2² - d3² + dss²) / (2 * dss)
		double x = (Math.pow(d2, 2) - Math.pow(d3, 2) + Math.pow(dss, 2)) / (2 * dss);
		// coordenda Y con la formula
		// y = ((d2² - d1² + i² + j²) / 2 * j) - ((i/j) * X), siendo i la distancia
		// entre kenobi y el eje Y y j la distancia entre kenobi y el eje X
		double y = (((Math.pow(d2, 2)) - (Math.pow(d1, 2)) + Math.pow(coordenadasRotadas[2][0], 2)
				+ Math.pow(coordenadasRotadas[2][1], 2)) / (2 * coordenadasRotadas[2][1]))
				- ((coordenadasRotadas[2][0] / coordenadasRotadas[2][1]) * x);

		double[] ubicacion = { x, y };
		// La ubicación se da en el nuevo eje de coordenadas, por tal razon se denbe
		// invertir rotación de ejes y traslación de puntos
		ubicacion = Util.invertirRotacionEjes(ubicacion);
		ubicacion = Util.invertirTraslacionEjes(ubicacion);

		double distanceKenobi = Util.distanciaEntrePuntos(coordenadas[2][0], coordenadas[2][1], ubicacion[0],
				ubicacion[1]);
		double distanceSkywalker = Util.distanciaEntrePuntos(coordenadas[0][0], coordenadas[0][1], ubicacion[0],
				ubicacion[1]);
		double distanceSato = Util.distanciaEntrePuntos(coordenadas[1][0], coordenadas[1][1], ubicacion[0],
				ubicacion[1]);

		// se comprueb que las distancias ingresadas en el servicio sean reales
		if ((new BigDecimal(d1)).setScale(0, RoundingMode.UP).longValue() != (new BigDecimal(distanceKenobi)).setScale(0, RoundingMode.UP).longValue()
				|| (new BigDecimal(d2)).setScale(0, RoundingMode.UP).longValue() != (new BigDecimal(distanceSkywalker)).setScale(0, RoundingMode.UP).longValue()
				|| (new BigDecimal(d3)).setScale(0, RoundingMode.UP).longValue() != (new BigDecimal(distanceSato)).setScale(0, RoundingMode.UP).longValue()) {
			throw new Exception("Las distancias ingresadas no son reales");
		}

		return ubicacion;
	}

	/**
	 * Metodo encargado de constuir un mensaje recibido en tres receptores con
	 * desfase entre sus palabras
	 * 
	 * @param arreglo en dos dimensiones [i][j], siendo i el arreglo de cada mensaje
	 *                y j la palabra de cada mensaje
	 * @return retorna un string con el mensaje reconstruido
	 */
	@Override
	public String getMessage(String[]... msg) {

		String[] msgKenobi = msg[0];
		String[] msgSkywalker = msg[1];
		String[] msgSato = msg[2];

		String respuesta = "RESPONSE CODE: 404";
		StringBuilder msgRespuesta = null;

		if (msgKenobi.length == msgSkywalker.length && msgKenobi.length == msgSato.length) {

			String[] arregloRespuesta = new String[msgKenobi.length];

			for (int i = 0; i < msgKenobi.length; i++) {

				if (!msgKenobi[i].isBlank() && !msgKenobi[i].isEmpty()) {
					arregloRespuesta[i] = msgKenobi[i];
					continue;
				}
				if (!msgSkywalker[i].isBlank() && !msgSkywalker[i].isEmpty()) {
					arregloRespuesta[i] = msgSkywalker[i];
					continue;
				}
				if (!msgSato[i].isBlank() && !msgSato[i].isEmpty()) {
					arregloRespuesta[i] = msgSato[i];
				}
			}

			msgRespuesta = new StringBuilder();
			for (int i = 0; i < arregloRespuesta.length; i++) {
				if (arregloRespuesta[i] != null) {
					msgRespuesta.append(arregloRespuesta[i] + " ");
				} else {
					return respuesta;
				}
			}

			respuesta = msgRespuesta.toString();

		}

		return respuesta;
	}

}
