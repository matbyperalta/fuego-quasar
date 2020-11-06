package com.triangulacion.test;

import java.math.BigDecimal;

import com.triangulacion.servicios.Mensaje;
import com.triangulacion.servicios.MensajeApi;
import com.triangulacionapi.utilidades.Util;

import junit.framework.TestCase;

public class TestMensaje extends TestCase {

	private Mensaje mensaje = null;
	private double distanciaKenobi;
	private double distanciaSkywalker;
	private double distanciaSato;
	String[] mensajeKenobi = new String[5];
	String[] mensajeSkywalker = new String[5];
	String[] mensajeSato = new String[5];

	protected void setUp() throws Exception {
		mensaje = new MensajeApi();
		try {
			// poner en las siguientes tres variables las distancias a los tres satelites
			distanciaKenobi = Util.distanciaEntrePuntos(-500, -200, -400, -600);
			distanciaSkywalker = Util.distanciaEntrePuntos(100, -100, -400, -600);
			distanciaSato = Util.distanciaEntrePuntos(500, 100, -400, -600);

			// poner en los siguientes arreglos el mensaje enviado a cada satelite, puede
			// modifcar el tamaño de los arreglos
			mensajeKenobi[0] = "";
			mensajeKenobi[1] = "";
			mensajeKenobi[2] = "un";
			mensajeKenobi[3] = "";
			mensajeKenobi[4] = "secreto";

			mensajeSkywalker[0] = "este";
			mensajeSkywalker[1] = "";
			mensajeSkywalker[2] = "un";
			mensajeSkywalker[3] = "";
			mensajeSkywalker[4] = "secreto";

			mensajeSato[0] = "este";
			mensajeSato[1] = "es";
			mensajeSato[2] = "";
			mensajeSato[3] = "mensaje";
			mensajeSato[4] = " ";

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void tearDown() throws Exception {
	}

	public void testGetLocation() {

		try {

			System.out.println("testGetLocation:> Distacia a Kenobi= " + distanciaKenobi);
			System.out.println("testGetLocation:> Distacia a Skywalker= " + distanciaSkywalker);
			System.out.println("testGetLocation:> Distacia a Sato= " + distanciaSato);

			double[] posicion = mensaje.getLocation(distanciaKenobi, distanciaSkywalker, distanciaSato);
			System.out.println("("+posicion[0]+","+posicion[1]);

			double testDistanceKenobi = Util.distanciaEntrePuntos(posicion[0], posicion[1], -500, -200);
			double testDistanceSkywalker = Util.distanciaEntrePuntos(posicion[0], posicion[1], 100, -100);
			double testDistanceSato = Util.distanciaEntrePuntos(posicion[0], posicion[1], 500, 100);

			// se comprueb que las distancias ingresadas en el servicio sean reales
			if ((new BigDecimal(testDistanceKenobi)).longValue() != (new BigDecimal(distanciaKenobi)).longValue()
					|| (new BigDecimal(testDistanceSkywalker)).longValue() != (new BigDecimal(distanciaSkywalker))
							.longValue()
					|| (new BigDecimal(testDistanceSato)).longValue() != (new BigDecimal(distanciaSato)).longValue()) {
				assertTrue("testGetMessage:> Las distancias no son reales", false);
			}

		} catch (Exception e) {
			fail("Not yet implemented");
		}
	}

	public void testGetMessage() {
		try {

			String[][] mensajes = { mensajeKenobi, mensajeSkywalker, mensajeSato };

			String mensajeEmisor = mensaje.getMessage(mensajes);

			System.out.println("testGetMessage:> " + mensajeEmisor);

			boolean asercion = false;
			if (mensajeEmisor.equals("RESPONSE CODE: 404")) {
				asercion = false;
			} else {
				asercion = true;
			}
			assertTrue("testGetMessage:> El mensaje no se puede descifar", asercion);

		} catch (Exception e) {
			fail("Not yet implemented");
		}
	}

}
