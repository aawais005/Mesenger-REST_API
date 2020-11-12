package org.awais.rest.messenger.exception;

import org.awais.rest.messenger.model.ErrorMessage;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

// @Provider registers this class in jax-rs. So, jax-rs knows this class is there. 
@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException>{

	@Override
	public Response toResponse(DataNotFoundException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 404, "https://www.google.com/");
		return Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
	}

}
