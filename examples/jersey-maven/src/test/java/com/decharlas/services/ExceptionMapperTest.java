package com.decharlas.services;

import junit.framework.Assert;

import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.grizzly.web.GrizzlyWebTestContainerFactory;

public class ExceptionMapperTest extends JerseyTest {
	private WebResource resource;

	public ExceptionMapperTest() {
		super(new WebAppDescriptor.Builder("com.decharlas.services")
				.contextParam("log4jConfigLocation",
						"src/main/webapp/WEB-INF/log4j.properties")
				.contextParam("webAppRootKey", "jersey-maven.root")
				.servletClass(ServletContainer.class).build());

		this.client().addFilter(new LoggingFilter());
		this.resource = resource();
	}

	@Override
	protected TestContainerFactory getTestContainerFactory() {
		return new GrizzlyWebTestContainerFactory();
	}

	@Test
	public void handleExceptions() {
		ClientResponse response = resource.path("documents/A").get(
				ClientResponse.class);
		Assert.assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(),
				response.getStatus());
		Assert.assertTrue(response.getEntity(String.class).startsWith("ERROR: "));
	}
}
