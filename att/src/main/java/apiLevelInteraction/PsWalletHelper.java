package apiLevelInteraction;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import decoders.DecoderConfigException;
import entities.PsWallet;
import generated.ats.sportsbook.punter.dto.AccountBalance;
import generated.ats.sportsbook.punter.dto.Login;
import other.Constants;

class PsWalletHelper extends ApiPostHelper {

    private static final Logger log = Logger.getLogger(PsWalletHelper.class);
    private String walletHost;
    private PsWallet psWallet;
    private HttpPost request;

    PsWalletHelper() throws DecoderConfigException {
        psWallet = new PsWallet();
        if (Constants.TESTING_ENV.equalsIgnoreCase("BENCHMARK")) {
            walletHost = "iombenampswal01";
        } else if (Constants.TESTING_ENV.equalsIgnoreCase("STAGING")) {
            walletHost = "iomstgampss01";
        }
    }

    /**
     * Method to set the wallet sim balance
     */
    void setExternalBalance(Login login, String amount) throws JSONException {
        request = new HttpPost("http://" + walletHost + ".amelco.lan:8000/PsAmelcoApi/setBalance");

        try {
            StringEntity entity = new StringEntity(psWallet.createSetBalanceDetailsReqParams(login, amount).toString());
            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");

            JSONObject response = executeHttpPost(request);

            log.info("Post to resetExternalBalance response=" + response.toString());
        } catch (UnsupportedEncodingException e) {
            log.error(e);
        }
        log.info("Account balance set to " + amount);
    }

    /**
     * API call to the external wallet that returns a user balanace info
     *
     * @return account balance amount
     */
    AccountBalance getUserInfo(Login login) throws JSONException {
        AccountBalance accountBalance = null;
        request = new HttpPost("http://" + walletHost + ".amelco.lan:8000/PsAmelcoApi/getUserInfo");

        try {
            StringEntity entity = new StringEntity(psWallet.createGetUserDetailsReqParams(login).toString());
            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");

            JSONObject response = executeHttpPost(request);
            log.info("Post to getUserInfo response=" + response.toString());

            accountBalance = psWallet.calculateBalance(response);
        } catch (IOException ex) {
            log.error(ex);
        }

        return accountBalance;
    }

}
