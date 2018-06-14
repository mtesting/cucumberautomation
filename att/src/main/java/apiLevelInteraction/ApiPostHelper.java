package apiLevelInteraction;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.IOException;

import decoders.Decoder;
import decoders.DecoderConfigException;
import decoders.DecoderManager;

public abstract class ApiPostHelper {

    private static final Logger log = Logger.getLogger(ApiPostHelper.class);
    protected final Decoder decoder = DecoderManager.getManager().getDecoder();
    protected ObjectMapper mapper;
    private HttpResponse response;
    private ResponseHandler<String> responseHandler;
    private HttpClient httpClient;

    protected ApiPostHelper() throws DecoderConfigException {
    }

    /**
     * Function to perform POST API calls
     *
     * @param request POST request
     * @return json format response
     */
    protected JSONObject executeHttpPost(HttpPost request) {
        JSONObject responseBody = null;
        httpClient = HttpClientBuilder.create().build();
        responseHandler = new BasicResponseHandler();

        try {
            response = httpClient.execute(request);
            assertResponseIsOk(response);

            responseBody = new JSONObject(responseHandler.handleResponse(response));
        } catch (IOException | JSONException e) {
            log.error(e);
        }

        return responseBody;
    }

    /**
     * Function to perform GET API calls
     *
     * @param request GET request
     * @return json format response
     */
    protected JSONObject executeHttpGet(HttpGet request) {
        JSONObject responseBody = null;
        httpClient = HttpClientBuilder.create().build();
        responseHandler = new BasicResponseHandler();


        try {
            response = httpClient.execute(request);
            assertResponseIsOk(response);

            responseBody = new JSONObject(responseHandler.handleResponse(response));
        } catch (IOException | JSONException e) {
            log.error(e);
        }

        return responseBody;
    }

    private void assertResponseIsOk(HttpResponse response) {
        Assert.assertEquals("Error response code=" + response.getStatusLine().getStatusCode(),
                HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }


}
