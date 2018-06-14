package entities;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import generated.ats.sportsbook.punter.dto.AccountBalance;
import generated.ats.sportsbook.punter.dto.Login;

import static ats.betting.util.DtoUtil.createPunterAccountBalance;

public class PsWallet {

    public JSONObject createGetUserDetailsReqParams(Login login) throws JSONException {
        JSONObject parameters = new JSONObject();
        parameters.putOnce("webId", login.getUsername().toUpperCase());
        parameters.putOnce("signature", "cpVCwjrv%2FgHmSpX2uI8JGOKA%3D%3D");
        parameters.putOnce("ipAddress", "123.123.123.123");
        parameters.putOnce("token", login.getSessionToken());
        return parameters;
    }

    public JSONObject createSetBalanceDetailsReqParams(Login login, String amount) throws JSONException {
        JSONObject parameters = new JSONObject();
        parameters.putOnce("webId", login.getUsername().toUpperCase());
        parameters.putOnce("newBalance", amount);
        return parameters;
    }

    public AccountBalance calculateBalance(JSONObject userInfo) throws JSONException {
        String defaultCurrency = userInfo.getString("defaultCurrency");
        JSONArray jsonArray = userInfo.getJSONArray("balances");
        double totalBalance = 0;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject balanceObject = jsonArray.getJSONObject(i);
            double amount = balanceObject.getDouble("amount");
            if (amount > 0) {
                // NB - they return cents
                amount /= 100;
                totalBalance += amount;
            }
        }
        return createPunterAccountBalance(totalBalance, defaultCurrency);
    }

}
