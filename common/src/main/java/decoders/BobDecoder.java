package decoders;

import other.Constants;

public class BobDecoder implements Decoder {

    public Long getCompetitionId(String sportName) throws DecoderConfigException {
        Long competitionId;

        switch (sportName.toUpperCase()) {
            case "FOOTBALL":
                competitionId = 2152196L;
                break;
            default:
                throw new DecoderConfigException("Sport name missing at the getCompetitionId() config");
        }
        return competitionId;
    }

    @Override
    public String decodePunterUrl(String customer) throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "UAT":
                return Constants.BOB_UAT_URL;
            case "DEV":
            default:
                return Constants.BOB_DEV_URL;
        }
    }

    @Override
    public String decodeTradingUrl(String customer) throws DecoderConfigException {
        return null;
    }

    @Override
    public String decodeCustomerDb(String customer) throws DecoderConfigException {
        return null;
    }

    @Override
    public String decodeATTURL() throws DecoderConfigException {
        return null;
    }

    @Override
    public String decodeCustomerDbPassword () throws DecoderConfigException {
        return "";
    }

}
