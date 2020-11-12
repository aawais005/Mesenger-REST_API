package org.awais.rest.messenger.resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.MatrixParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

// Some Other Annotation we can use apart from @QueryParam, @Path
// @MatrixParam for query parameters just like @QueryParam but instead of "?" it uses ":"
//		like ---> url/injectdemo/annotations:param=value
// @HeaderParam for sending metadata about the request like authentication token
// Others are @CookieParam and @FormParam, @Context
@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {

//	Need to know before hand the names of params when using annotations 
//	and it becomes tedious if you have to use more Param values in some function
	@GET
	@Path("annotations")
	public String getParamsUsingAnnotations(@MatrixParam("param") String matrixParam,
											 @HeaderParam("authSessionID") String header,
											 @CookieParam("cookieName") String cookie) {
		return "Matrix Param: "+ matrixParam + " Header Param: "+ header + " Cookie: "+ cookie;
	}
	
//	What if we want to get all header params or cookie params
//	and do something bases on that. For this use context
	
//	@Context is a special annotation. It can only be annotated to few special types 
	@GET
	@Path("context")
	public String getParamsUsingContext(@Context UriInfo uriInfo,
										 @Context HttpHeaders headers) {
		String path = uriInfo.getAbsolutePath().toString();
		String cookies = headers.getCookies().toString();
		return "Path: "+ path+ " Cookies: "+ cookies;
	}
	
//	Third way is to use @BeanParam.
//	In this, you make a separate class and add all your annotations in that class as member variables.
}


