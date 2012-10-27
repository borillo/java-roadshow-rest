package com.decharlas.services;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.decharlas.dao.UserDAO;
import com.decharlas.model.User;

@Path("users")
public class UserResource {
	private UserDAO userDAO;

	public UserResource() {
		this.userDAO = new UserDAO();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAll() {
		return this.userDAO.getUsers();
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response remove(@PathParam("id") String id) {
		this.userDAO.removeUser(Integer.parseInt(id));

		return Response.noContent().build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(User user, @Context UriInfo uriInfo) {
		User newUser = this.userDAO.addUser(user);

		UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
		URI newUri = uriBuilder.path(String.valueOf(newUser.getId())).build();

		return Response.created(newUri).entity(newUser).build();
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User update(User user) {
		return this.userDAO.updateUser(user);
	}
}