package com.decharlas.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.grizzly.web.GrizzlyWebTestContainerFactory;

public class DocumentResourceTest extends JerseyTest {
	private WebResource resource;

	public DocumentResourceTest() {
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
	public void uploadFile() throws FileNotFoundException, IOException {
		FormDataMultiPart form = new FormDataMultiPart();
		File file = new File("src/test/resources/cat.jpg");
		form.bodyPart(new FileDataBodyPart("file", file,
				MediaType.MULTIPART_FORM_DATA_TYPE));

		ClientResponse response = resource.path("documents")
				.type(MediaType.MULTIPART_FORM_DATA)
				.post(ClientResponse.class, form);

		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
		assertTrue(new File("/tmp/cat.jpg").exists());
	}
}