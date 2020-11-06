/**
 * Archivo del proyecto fuego-quasar
 * Esta clase ecapsula las rutinas encaragdas de realizar los movimientos de los satelites en el sistema de coordenadas (X,Y) 
 *  
 * --------------------------------------
 * Nombre del archivo: Util.java
 * Paquete del archivo: com.triangulacion.servicios
 * @author Matby Peralta Davila
 * @version 1.0
 */
package com.triangulacionapi.utilidades;

public class Util {

	/**
	 * Variable pra registrar la cantidad de unidaes que fue trasladado los
	 * satelites en el eje x en el plano cartesiano
	 */
	private static double unidadesXTraslacionEjes;
	/**
	 * Variable pra registrar la cantidad de unidaes que fue trasladado los
	 * satelites en el eje x en el plano cartesiano
	 */
	private static double unidadesYTraslacionEjes;
	/**
	 * Variable pra registrar la cantidad de unidaes que fue rotado en el eje Y el
	 * satelite Kenobi y Sato
	 */
	/**
	 * Variable que guaarda los grados de rotación
	 */
	private static double gradosRotacion;

	/**
	 * Constante para el satelite kenobi
	 */
	public static final String KENOBI = "kenobi";
	/**
	 * Constante para el satelite skywalker
	 */
	public static final String SKYWALKER = "skywalker";
	/**
	 * Constante para el satelite sato
	 */
	public static final String SATO = "sato";

	/**
	 * Metodo encargado de calcular la distancia entre dos puntos conocidos
	 * 
	 * @param x1, posición X del punto inicial
	 * @param y1, posición Y del punto inicial
	 * @param x2, posición X del punto final
	 * @param y2, posición Y del punto final
	 * @return retorna la distancia entre los dos puntos
	 * @throws Exception
	 */
	public static double distanciaEntrePuntos(double x1, double y1, double x2, double y2) throws Exception {
		return (double) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}

	/**
	 * Se encarga de trasladar las coordenadas de los satelites moviendo los
	 * satelites así: Skywalker: al origen de las coordenadas (0,0) Sato: se restan
	 * 100 unidades a X y Y teniendo en cuenta el movimiento de skywalker, y se
	 * lleva la ordenada a 0 Kenobi: se restan 100 unidades a X y Y teniendo en
	 * cuenta el movimiento de skywalker, y se gira la ordenada 200 unidades
	 * 
	 * @param coordenadas de los tres satelites, la primera fila es el satelite que
	 *                    se llevara al origen de las coordenadas, la segunda fila
	 *                    es el satelite que se llevara a la abscisa positiva, la
	 *                    tercera fila es el tercer satelite restante
	 * @return nuevas coordenadas de los satelites segun la traslación realizada
	 */
	public static double[][] trasladarEjes(double[][] coordenadas) throws Exception {

		double[] s1 = coordenadas[0];
		double pisx1 = s1[0];// posición inicial x satelite 1
		double pisy1 = s1[1];// posición inical y satelite 1

		double[] s2 = coordenadas[1];
		double pisx2 = s2[0];// posición inicial x satelite 2
		double pisy2 = s2[1];// posición inicial y satelite 2

		double[] s3 = coordenadas[2];
		double pisx3 = s3[0];// posición inicial x satelite 3
		double pisy3 = s3[1];// posición inicial y satelite 3

		double ptsx1 = pisx1 - pisx1;// posición trasladada x satelite 1 hacia 0
		double ptsy1 = pisy1 - pisy1;// posición trasladada y satelite 1 hacia 0

		double ptsx2 = pisx2 - pisx1;// posición trasladada x satelite 2 pisx1 unidades
		double ptsy2 = pisy2 - pisy1;// posición trasladada y satelite 2 pisy1 unidades

		double ptsx3 = pisx3 - pisx1;// posición trasladada x satelite 3 pisx1 unidades
		double ptsy3 = pisy3 - pisy1;// posición trasladada y satelite 3 pisy1 unidades

		unidadesXTraslacionEjes = pisx1;
		if (unidadesXTraslacionEjes < 0) {
			unidadesXTraslacionEjes = unidadesXTraslacionEjes * (-1);
		}
		unidadesYTraslacionEjes = pisy1;
		if (unidadesYTraslacionEjes < 0) {
			unidadesYTraslacionEjes = unidadesYTraslacionEjes * (-1);
		}

		double[][] nuevasCoordenadas = { { ptsx1, ptsy1 }, { ptsx2, ptsy2 }, { ptsx3, ptsy3 } };

		return nuevasCoordenadas;
	}

	/**
	 * Metodo encargado de trasladar en X y Y el punto encontrado del emisor, las
	 * mismas unidades que fueron trasladadas en la rotación inicial
	 * 
	 * @param coordenadas, arreglo con las coordenadas que ubican el originador del
	 *                     mensaje
	 * @return retorna las coordenadas con la traslación de 100 unidades en X y -100
	 *         unidades en Y
	 * @throws Exception
	 */
	public static double[] invertirTraslacionEjes(double[] coordenadas) throws Exception {
		double x = coordenadas[0];
		double y = coordenadas[1];

		// se suma puesto que en la traslación inicial el eje x se movio en sentido
		// negativo
		x = x + unidadesXTraslacionEjes;
		// se resta puesto que en la traslación inicial el eje y se movio en sentido
		// positivo
		y = y - unidadesYTraslacionEjes;
		double[] coordnadas = { x, y };
		return coordnadas;
	}

	/**
	 * Se ejecuta despues de trasladar los satelites. Se encarga de rotar el sistema
	 * de coordenada un angulo tal el cual es conformado entre sato y el eje X,
	 * dejando a sato en el eje X y moviendo a kenobi el mismo angulo
	 * 
	 * @param coordenadas, coordenadas de los satelites
	 * @return retorna la nueva ubicación de los satelites
	 * @throws Exception
	 */
	public static double[][] rotarEjes(double[][] coordenadas) throws Exception {

		double[] s1 = coordenadas[0];
		double pisXSkywalker = s1[0];// posición inicial x satelite skywalker
		double pisYSkywalker = s1[1];// posición inical y satelite skywalker

		double[] s2 = coordenadas[1];
		double pisXSato = s2[0];// posición inicial x satelite sato
		double pisYSato = s2[1];// posición inicial y satelite sato

		double[] s3 = coordenadas[2];
		double pisXKenobi = s3[0];// posición inicial x satelite kenobi
		double pisYKenobi = s3[1];// posición inicial y satelite kenobi

		// calcular grados de sato con el eje x, la formula es atan | y/x |
		double teta = Math.toDegrees(Math.atan(Math.abs(pisYSato / pisXSato)));
		gradosRotacion = teta;

		// debido a que skywalker esta en el origen, no se rota

		// se calcula la nueva posicion de X de sato girandolo teta grados
		// la formula es x' = x Cos(teta) + y Sen(teta), siendo x' la nueva posicion, x
		// la posocion actual, y la posicion actual
		double prsXSato = (pisXSato * (Math.cos(Math.toRadians(teta)))) + (pisYSato * (Math.sin(Math.toRadians(teta))));

		// se calcula la nueva posicion de y de sato girandolo teta grados
		// la formula es y' = y Cos(teta) - x Sen(teta), siendo y' la nueva posicion, x
		// la posocion actual, y la posicion actual
		double prsYSato = (pisYSato * (Math.cos(Math.toRadians(teta)))) - (pisXSato * (Math.sin(Math.toRadians(teta))));

		// se calcula la nueva posicion de X de kenobi girandolo teta grados
		// la formula es x' = x Cos(teta) + y Sen(teta), siendo x' la nueva posicion, x
		// la posocion actual, y la posicion actual
		double prsXKenobi = (pisXKenobi * (Math.cos(Math.toRadians(teta))))
				+ (pisYKenobi * (Math.sin(Math.toRadians(teta))));

		// se calcula la nueva posicion de y de kenobi girandolo teta grados
		// la formula es y' = y Cos(teta) - x Sen(teta), siendo y' la nueva posicion, x
		// la posocion actual, y la posicion actual
		double prsYKenobi = (pisYKenobi * (Math.cos(Math.toRadians(teta))))
				- (pisXKenobi * (Math.sin(Math.toRadians(teta))));

		double[][] nuevasCoordenadas = { { pisXSkywalker, pisYSkywalker }, { prsXSato, prsYSato },
				{ prsXKenobi, prsYKenobi } };

		return nuevasCoordenadas;
	}

	/**
	 * Invierte la rotación del punto encontrado en el mismo angulo de rotación
	 * encontrado en el metodo rotarEjes, la rotación es contraria a la rotación
	 * inicial
	 * 
	 * @param coordenadas a rotar
	 * @return retorna la posición final de la nave que origina el mensaje
	 * @throws Exception
	 */
	public static double[] invertirRotacionEjes(double[] coordenadas) throws Exception {
		double piex = coordenadas[0];// posicion inicial en x del emisor
		double piey = coordenadas[1];// posicion inicial en y del emisor

		// se calcula la nueva posicion de X del satelite origen girandolo teta grados
		// la formula es x' = x Cos(teta) + y Sen(teta), siendo x' la nueva posicion, x
		// la posocion actual, y la posicion actual
		double prsXOrigen = (piex * (Math.cos(Math.toRadians(-gradosRotacion))))
				+ (piey * (Math.sin(Math.toRadians(-gradosRotacion))));

		// se calcula la nueva posicion de y de satelite origen girandolo teta grados
		// la formula es y' = y Cos(teta) - x Sen(teta), siendo y' la nueva posicion, x
		// la posocion actual, y la posicion actual
		double prsYOrigen = (piey * (Math.cos(Math.toRadians(-gradosRotacion))))
				- (piex * (Math.sin(Math.toRadians(-gradosRotacion))));

		double[] coordnadas = { prsXOrigen, prsYOrigen };
		return coordnadas;
	}

}
