package com.triangulacion.servicios;

public interface Mensaje {

	public double [] getLocation(double ...distances) throws Exception;
	
	public String getMessage(String []... msg) throws Exception;
	
}
