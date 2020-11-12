package org.awais.rest.messenger.resources;


import org.awais.rest.messenger.model.Message;
import org.awais.rest.messenger.resources.beans.MessageFilterBean;
import org.awais.rest.messenger.service.MessageService;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

//@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
// can send either in json or xml depending on the 
// header accept field in the request (CONTENT NEGOTIATION)
public class MessageResource {
	
	MessageService messageService = new MessageService();
	
	@GET
//	QueryParam ---> urlPrefix/messages?year=2020
//	QueryParam ---> urlPrefix/messages?start=120&size=1
	
//	Using Simple Annotations
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getJsonMessages(@QueryParam("year") int year,
									  @QueryParam("start") int start,
									  @QueryParam("size") int size) {
		System.out.println("JSON Method Called");
		if (year > 0) {
			return messageService.getAllMessagesForYear(year);
		}
		if (start >= 0 && size >= 0) {
			return messageService.getAllMessagesPag(start, size);
		}
		return messageService.getAllMessages();
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Message> getXmlMessages(@QueryParam("year") int year,
									  @QueryParam("start") int start,
									  @QueryParam("size") int size) {
		System.out.println("XML Method Called");
		if (year > 0) {
			return messageService.getAllMessagesForYear(year);
		}
		if (start >= 0 && size >= 0) {
			return messageService.getAllMessagesPag(start, size);
		}
		return messageService.getAllMessages();
	}
	
//	Using @BeansParam
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean) {
		if (filterBean.getYear() > 0) {
		return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if (filterBean.getStart() >= 0 && filterBean.getSize() >= 0) {
		return messageService.getAllMessagesPag(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
		Message newMessage = messageService.addMessage(message);
		String newId = String.valueOf((newMessage.getId()));
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri)
						.entity(newMessage)
						.build();
	}
	
	@PUT
	@Path("/{messageId}")
//	Path ---> urlPrefix/messages/123
	public Message updateMessage(@PathParam("messageId") long messageId, Message message) {
		message.setId(messageId);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long messageId) {
		messageService.removeMessage(messageId);
	}
	
	
	@GET
	@Path("/{messageId}")
//	@Produces(MediaType.APPLICATION_XML)
	public Message getMessage(@PathParam("messageId") long messageId,
								@Context UriInfo uriInfo) {
		Message message = messageService.getMessage(messageId);
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComments(uriInfo, message), "comments");
		return message;
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(MessageResource.class, "getCommentResource")
				.path(CommentResource.class)
				.resolveTemplate("messageId", message.getId())
				.build()
				.toString();
		return uri;
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
						.path(ProfileResource.class)
						.path(message.getAuthor())
						.build()
						.toString();
		return uri;
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
						.path(MessageResource.class)
						.path(Long.toString(message.getId()))
						.build()
						.toString();
		return uri;
	}
	
//	Making a subresource for comments to messages
//	This method delegates to other resource 
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
}
