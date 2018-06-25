package apiLevelInteraction;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.amelco.testscenario.abelson.Match;

import java.util.List;

import ats.core.util.json.JsonUtil;
import decoders.DecoderConfigException;

public class AttRestHelper extends ApiPostHelper {

    private static final Logger log = Logger.getLogger(AttRestHelper.class);

    private final String envAttUrl = decoder.decodeATTURL();

    private HttpPost request;

    public AttRestHelper() throws DecoderConfigException {
    }

    public void prepareAbelsonMatches(List<Match> matches) throws JSONException {
        request = new HttpPost(envAttUrl + "/att/attrest/abelsonmatches/prepare");

        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        request.setEntity(new StringEntity(JsonUtil.marshalJson(matches),  ContentType.APPLICATION_JSON));

        JSONObject response = executeHttpPost(request);
        log.info("prepareAbelsonMatches response=" + response.toString());
    }

    public void updateAbelsonMatch(Match match) throws JSONException {
        request = new HttpPost(envAttUrl + "/att/attrest/abelsonmatches/update");

        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        request.setEntity(new StringEntity(JsonUtil.marshalJson(match),  ContentType.APPLICATION_JSON));

        JSONObject response = executeHttpPost(request);
        log.info("updateAbelsonMatch response=" + response.toString());
    }

}