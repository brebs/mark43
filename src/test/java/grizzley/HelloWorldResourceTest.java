package grizzley;

import java.net.HttpURLConnection;
import java.net.URL;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HelloWorldResourceTest extends JerseyTest {

    @Override
    protected ResourceConfig configure() {
        enable(TestProperties.LOG_TRAFFIC);
        return new ResourceConfig(WordsResource.class);
    }

    @Test
    @Ignore("not compatible with test framework (doesn't use client())")
    public void testHelloWorld() throws Exception {
        URL getUrl = UriBuilder.fromUri(getBaseUri()).build().toURL();
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
        try {
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "text/plain");
            assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());
        } finally {
            connection.disconnect();
        }
    }

    @Test
    public void testConnection() {
        Response response = target().request("text/plain").get();
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testClientStringResponse() {
        String s = target().path("\\words").request().get(String.class);
        assertEquals(HelloWorldResource.CLICHED_MESSAGE, s);
    }
}