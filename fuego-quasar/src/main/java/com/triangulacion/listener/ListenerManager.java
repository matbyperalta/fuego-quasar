/**
 * Archivo del proyecto fuego-quasar
 * Esta clase implementa un ServletContextListener 
 *  
 * --------------------------------------
 * Nombre del archivo: ListenerManager.java
 * Paquete del archivo: com.triangulacion.listener
 * @author Matby Peralta Davila
 * @version 1.0
 */
package com.triangulacion.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.trinagulacion.cache.CacheSingleton;

public class ListenerManager implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		CacheSingleton cacheSingleton = CacheSingleton.getInstance();
		cacheSingleton.cleanUp();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
