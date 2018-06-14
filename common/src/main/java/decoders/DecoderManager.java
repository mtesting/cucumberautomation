package decoders;

import other.Constants;

public class DecoderManager {

    private static DecoderManager instance = null;
    private Decoder decoder;

    private DecoderManager() throws DecoderConfigException {
        decoderCustomer();
    }

    public static DecoderManager getManager() throws DecoderConfigException {
        if (instance == null) {
            instance = new DecoderManager();
        }
        return instance;
    }

    private void decoderCustomer() throws DecoderConfigException {
        switch (Constants.CUSTOMER_IN_TEST.toUpperCase()) {
            case "BETFAIR":
                decoder = new BetfairDecoder();
                break;
            case "BETSTARS":
                decoder = new PsDecoder();
                break;
            case "BOB":
                decoder = new BobDecoder();
                break;
            case "LADBROKES":
                decoder = new LadbrokesDecoder();
                break;
            case "MYBET":
                decoder = new MyBetDecoder();
                break;
            case "CORAL":
                decoder = new CoralDecoder();
                break;
            case "ALGO":
                decoder = new AlgoDecoder();
                break;
            case "INTRALOT":
                decoder = new IntralotDecoder();
                break;
            default:
                throw new DecoderConfigException("Customer config missing at decoderCustomer()");
        }
    }

    public Decoder getDecoder() {
        return decoder;
    }

}
