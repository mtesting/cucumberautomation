package steps.common;

import cucumber.api.java.en.Given;
import decoders.Decoder;
import decoders.DecoderConfigException;
import decoders.DecoderManager;
import other.Constants;
import other.SeleniumTestTemplate;

public class Registration extends SeleniumTestTemplate {

    private final Decoder decoder = DecoderManager.getManager().getDecoder();

    public Registration() throws DecoderConfigException {
    }

    @Given("^user is already registered with the email \"([^\"]*)\"$")
    public void user_is_already_registered_with_the_email(String arg1) throws Throwable {
        driver = initDriver(decoder.decodePunterUrl(Constants.CUSTOMER_IN_TEST));
    }

    @Given("^user is already registered with the username \"([^\"]*)\"$")
    public void user_is_already_registered_with_the_username(String arg1) throws Throwable {
        driver = initDriver(decoder.decodePunterUrl(Constants.CUSTOMER_IN_TEST));
    }

}
