package com.triangulacion.api;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class RestApplication extends Application{

	@Override
	   public Set<Class<?>> getClasses() {
	      Set<Class<?>> classes = new HashSet<>();
	      classes.add(MensajeREST.class);
	      return classes;
	   }
}
