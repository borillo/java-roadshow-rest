package com.decharlas.services;

import junit.framework.Assert;

import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;

public class TwitterTest {
	@Test
	public void twitterJSONObject() throws Exception {
		Client client = Client.create();
		client.addFilter(new LoggingFilter());

		WebResource resource = client.resource("http://search.twitter.com/");

		ClientResponse response = resource.path("search.json")
				.queryParam("q", "oracle+roadshow").get(ClientResponse.class);
		JSONObject data = response.getEntity(JSONObject.class);

		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
		Assert.assertEquals(1, data.getInt("page"));
	}
}