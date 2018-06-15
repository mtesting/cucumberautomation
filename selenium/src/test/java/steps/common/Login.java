package steps.common;

import cucumber.api.java.en.Given;
import decoders.Decoder;
import decoders.DecoderConfigException;
import decoders.DecoderManager;
import other.Constants;
import other.SeleniumTestTemplate;

public class Login extends SeleniumTestTemplate {

    private final Decoder decoder = DecoderManager.getManager().getDecoder();

    public Login() throws DecoderConfigException {
    }

//    @Given("^User is on the customer web$")
//    public void user_is_on_the_customer_web() throws Throwable {
//        driver = initDriver(decoder.decodePunterUrl(Constants.CUSTOMER_IN_TEST));
//    }

}