package com.decharlas.services;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CommonExceptionMapper implements ExceptionMapper<Exception> {
	@Override
	public Response toResponse(Exception exception) {
		return Response.serverError().type(MediaType.TEXT_PLAIN)
				.entity("ERROR: " + exception.getMessage()).build();
	}
}