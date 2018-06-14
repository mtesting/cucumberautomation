package decoders;

import other.Constants;
import util.StringEncrypt;

public class LadbrokesDecoder implements Decoder {

    public Long getCompetitionId(String sportName) throws DecoderConfigException {
        Long competitionId;

        switch (sportName.toUpperCase()) {
            case "FOOTBALL":
                competitionId = 166992L;
                break;
            case "TENNIS":
                competitionId = 2166593L;
                break;
            default:
                throw new DecoderConfigException("Sport name missing at the getCompetitionId() config");
        }

        return competitionId;
    }

    @Override
    public String decodePunterUrl(String customer) throws DecoderConfigException {
        return null;
    }

    @Override
    public String decodeTradingUrl(String customer) throws DecoderConfigException {
        return null;
    }

    @Override
    public String decodeCustomerDb(String customer) throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "TEST1":
                return Constants.LADBROKES_TST1_DB;
            default:
                throw new DecoderConfigException("Testing env missing at the decodeCustomerDb() config");
        }
    }

    @Override
    public String decodeATTURL() throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "TEST1":
                return Constants.LADBROKES_TST1_ATT;
            default:
                throw new DecoderConfigException("Missing config at decodeATTURL() for env=" + Constants.TESTING_ENV);
        }


    }

    @Override
    public String decodeCustomerDbPassword() throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "TEST1":
                return StringEncrypt.decryptXOR(Constants.LADBROKES_TST1_ATS_DB_PWD);
            default:
                throw new DecoderConfigException("Missing config at decodeCustomerDbPassword() for env=" + Constants.TESTING_ENV);
        }
    }

}