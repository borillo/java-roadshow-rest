package com.decharlas.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Test;

import com.decharlas.model.User;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.grizzly.web.GrizzlyWebTestContainerFactory;

public class UsersResourceTest extends JerseyTest
{
    private WebResource resource;

    public UsersResourceTest()
    {
        super(new WebAppDescriptor.Builder("com.decharlas.services")
                .contextParam("log4jConfigLocation", "src/main/webapp/WEB-INF/log4j.properties")
                .contextParam("webAppRootKey", "jersey-testing.root")
                .servletClass(ServletContainer.class).build());

        this.client().addFilter(new LoggingFilter());
        this.resource = resource();
    }

    @Override
    protected TestContainerFactory getTestContainerFactory()
    {
        return new GrizzlyWebTestContainerFactory();
    }

    @Test
    public void userListShouldBeThree() throws FileNotFoundException, IOException
    {
        ClientResponse response = resource.path("users").accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);
        List<User> users = response.getEntity(new GenericType<List<User>>()
        {
        });

        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
        Assert.assertEquals(2, users.size());
    }

    @Test
    public void deleteUser() throws FileNotFoundException, IOException
    {
        ClientResponse response = resource.path("users/1").accept(MediaType.APPLICATION_JSON_TYPE)
                .delete(ClientResponse.class);

        Assert.assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void addUser() throws FileNotFoundException, IOException
    {
        User newUser = new User(3, "Alberto Rodriguez Gomez");

        ClientResponse response = resource.path("users").accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, newUser);

        Assert.assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

        String location = response.getHeaders().getFirst("Location");
        Assert.assertNotNull(location);
        Assert.assertTrue(location.endsWith("3"));
    }
}