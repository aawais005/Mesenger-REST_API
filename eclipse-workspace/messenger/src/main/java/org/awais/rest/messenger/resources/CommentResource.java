package org.awais.rest.messenger.resources;

import java.util.List;

import org.awais.rest.messenger.model.Comment;
import org.awais.rest.messenger.service.CommentService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

// @Path Annotation is optional for subresource "/" would be suffice
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {

	private CommentService commentService = new CommentService();
	
//	@GET
//	public String test() {
//		return "new sub resource";
//	}
	
//	@GET
//	@Path("/{commentId}")
//	----> url/messages/{messageId}/comments/{commentId}
//	public String test2(@PathParam("messageId") long messageId,
//									@PathParam("commentId") long commentId) {
//		return "Method to return comment ID: "+ commentId + " for message "+ messageId;
//	}
	
	@GET
	public List<Comment> getAllComments(@PathParam("messageId") long messageId) {
		return commentService.getAllComments(messageId);
	}
	
	@POST
	public Comment addComment(@PathParam("messageId") long messageId, 
								Comment comment) {
		return commentService.addComment(messageId, comment);
	}
	
	@PUT
	@Path("/{commentId}")
	public Comment updateComment(@PathParam("messageId") long messageId, 
								 @PathParam("commentId") long commentId,
								 Comment comment) {
		comment.setId(commentId);
		return commentService.updateComment(messageId, comment);
	}
	
	@DELETE
	@Path("/{commentId}")
	public void deleteComment(@PathParam("messageId") long messageId, 
								 @PathParam("commentId") long commentId,
								 Comment comment) {
		commentService.removeComment(messageId, commentId);
	}

	@GET
	@Path("/{commentId}")
	public Comment addComment(@PathParam("messageId") long messageId, 
											@PathParam("commentId") long commentId) {
		return commentService.getComment(messageId, commentId);
	}
	
}
