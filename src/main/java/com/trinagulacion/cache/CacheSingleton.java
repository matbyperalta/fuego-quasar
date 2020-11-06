/**
 * Archivo del proyecto fuego-quasar
 * Esta clase implementa el patron singleto para mantener una instancia de un mapa el cual sirve para mantener datos en cache 
 *  
 * --------------------------------------
 * Nombre del archivo: CacheSingleton.java
 * Paquete del archivo: com.trinagulacion.cache
 * @author Matby Peralta Davila
 * @version 1.0
 */
package com.trinagulacion.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheSingleton implements Cache {

	/**
	 * Registra los datos en cache
	 */
	private Map<Object, Object> map;
	/**
	 * Registra a instancia de la clase
	 */
	private static CacheSingleton cacheSingleton = new CacheSingleton();

	/**
	 * Obtiene la instania de la clase
	 * 
	 * @return retorna la instancia unica de la clase
	 */
	public static CacheSingleton getInstance() {
		return cacheSingleton;
	}

	/**
	 * Constructor de la clase
	 */
	private CacheSingleton() {
		map = new ConcurrentHashMap<Object, Object>();
	}

	/**
	 * Pone un nuevo elemento en el cache
	 * 
	 * @param key,   llave del elemento
	 * @param value, valor del elemento
	 */
	@Override
	public void put(Object key, Object value) {
		map.put(key, value);
	}

	/**
	 * Obtiene un elemento del cache
	 * 
	 * @param llave del elemento a buscar
	 * @return elemento encontrado
	 */
	@Override
	public Object get(Object key) {
		return map.get(key);
	}

	/**
	 * Metodo para limpar el mapa
	 */
	@Override
	public void clear() {
		map.clear();

	}

	/**
	 * Metodo encargado de limpiar el mapa cada 5 minutos, este meotodo es iniciado
	 * por la clase com.triangulacion.listener.ListenerManager al iniciar la apliación
	 */
	@Override
	public void cleanUp() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(300000);
					} catch (InterruptedException ex) {
					}
					clear();
				}
			}
		});

		t.setDaemon(true);
		t.start();

	}

}
