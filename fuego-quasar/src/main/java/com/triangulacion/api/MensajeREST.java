package com.triangulacion.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triangulacion.common.MensajeNivel2InDTO;
import com.triangulacion.common.MensajeNivel2OutDTO;
import com.triangulacion.common.MensajeNivel3InDTO;
import com.triangulacion.common.PositionDTO;
import com.triangulacion.common.SateliteDTO;
import com.triangulacion.servicios.Mensaje;
import com.triangulacion.servicios.MensajeApi;
import com.triangulacionapi.utilidades.Util;
import com.trinagulacion.cache.CacheSingleton;

@Path("/service")
public class MensajeREST {

	private float distanciaKenobi;
	private float distanciaSkywalker;
	private float distanciaSato;
	private String[] mensajeKenobi;
	private String[] mensajeSkywalker;
	private String[] mensajeSato;

	@POST
	@Path("/topsecret")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response validarOrigenSatelites(MensajeNivel2InDTO in) {
		try {
			for (SateliteDTO dat : in.getSatellites()) {
				if (dat.getName().equals(Util.KENOBI)) {
					distanciaKenobi = dat.getDistance();
					mensajeKenobi = dat.getMessage();
				} else if (dat.getName().equals(Util.SKYWALKER)) {
					distanciaSkywalker = dat.getDistance();
					mensajeSkywalker = dat.getMessage();
				} else if (dat.getName().equals(Util.SATO)) {
					distanciaSato = dat.getDistance();
					mensajeSato = dat.getMessage();
				}
			}

			Mensaje mensaje = new MensajeApi();

			double[] ubicacion = mensaje.getLocation(distanciaKenobi, distanciaSkywalker, distanciaSato);

			MensajeNivel2OutDTO mensajeNivel2OutDTO = new MensajeNivel2OutDTO();
			PositionDTO positionDTO = new PositionDTO();
			positionDTO.setX(ubicacion[0]);
			positionDTO.setY(ubicacion[1]);

			mensajeNivel2OutDTO.setPosition(positionDTO);

			String[][] mensajes = { mensajeKenobi, mensajeSkywalker, mensajeSato };
			String mensajeEmisor = mensaje.getMessage(mensajes);

			if (mensajeEmisor.equals("RESPONSE CODE: 404")) {
				return Response.ok(mensajeEmisor, MediaType.APPLICATION_JSON).build();
			}

			mensajeNivel2OutDTO.setMessage(mensajeEmisor);

			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(mensajeNivel2OutDTO);

			return Response.ok("RESPONSE CODE: 200 " + jsonString, MediaType.APPLICATION_JSON).build();

		} catch (Exception e) {
			return Response.ok("RESPONSE CODE: 404", MediaType.APPLICATION_JSON).build();
		}

	}

	@POST
	@Path("/topsecret_split/kenobi")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarOrigenKenobi(MensajeNivel3InDTO in) {

		try {
			CacheSingleton cacheSingleton = CacheSingleton.getInstance();
			cacheSingleton.put(Util.KENOBI, in);
		} catch (Exception e) {
			return Response.ok("RESPONSE CODE: 404", MediaType.APPLICATION_JSON).build();
		}
		return Response.ok("RESPONSE CODE: 200").build();

	}

	@POST
	@Path("/topsecret_split/skywalker")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarOrigenSkywalker(MensajeNivel3InDTO in) {

		try {
			CacheSingleton cacheSingleton = CacheSingleton.getInstance();
			cacheSingleton.put(Util.SKYWALKER, in);
		} catch (Exception e) {
			return Response.ok("RESPONSE CODE: 404", MediaType.APPLICATION_JSON).build();
		}
		return Response.ok("RESPONSE CODE: 200").build();

	}

	@POST
	@Path("/topsecret_split/sato")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarOrigenSato(MensajeNivel3InDTO in) {

		try {
			CacheSingleton cacheSingleton = CacheSingleton.getInstance();
			cacheSingleton.put(Util.SATO, in);
		} catch (Exception e) {
			return Response.ok("RESPONSE CODE: 404", MediaType.APPLICATION_JSON).build();
		}
		return Response.ok("RESPONSE CODE: 200").build();

	}

	@GET
	@Path("/topsecret_split")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response recuperarOrigenSatelite(MensajeNivel3InDTO in) {

		try {
			
			CacheSingleton cacheSingleton = CacheSingleton.getInstance();

			Mensaje mensaje = new MensajeApi();
			MensajeNivel3InDTO kenobiDTO = (MensajeNivel3InDTO)cacheSingleton.get(Util.KENOBI);
			MensajeNivel3InDTO skywalkerDTO = (MensajeNivel3InDTO)cacheSingleton.get(Util.SKYWALKER);
			MensajeNivel3InDTO satoDTO = (MensajeNivel3InDTO)cacheSingleton.get(Util.SATO);
			
			if(kenobiDTO == null || skywalkerDTO == null || satoDTO == null) {
				return Response.ok("NO HAY SUFICIENTE INFORMACION", MediaType.APPLICATION_JSON).build();
			}
			
			double[] ubicacion = mensaje.getLocation(kenobiDTO.getDistance(), skywalkerDTO.getDistance(), satoDTO.getDistance());

			MensajeNivel2OutDTO mensajeNivel2OutDTO = new MensajeNivel2OutDTO();
			PositionDTO positionDTO = new PositionDTO();
			positionDTO.setX(ubicacion[0]);
			positionDTO.setY(ubicacion[1]);

			mensajeNivel2OutDTO.setPosition(positionDTO);

			String[][] mensajes = { kenobiDTO.getMessage(), skywalkerDTO.getMessage(), satoDTO.getMessage() };
			String mensajeEmisor = mensaje.getMessage(mensajes);

			if (mensajeEmisor.equals("RESPONSE CODE: 404")) {
				return Response.ok(mensajeEmisor, MediaType.APPLICATION_JSON).build();
			}

			mensajeNivel2OutDTO.setMessage(mensajeEmisor);

			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(mensajeNivel2OutDTO);

			return Response.ok("RESPONSE CODE: 200 " + jsonString, MediaType.APPLICATION_JSON).build();

		} catch (Exception e) {
			return Response.ok("RESPONSE CODE: 404", MediaType.APPLICATION_JSON).build();
		}

	}

}
