package decoders;

import other.Constants;
import util.StringEncrypt;

public class LadbrokesDecoder implements Decoder {

    public Long getCompetitionId(String sportName) throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "TEST1":
                switch (sportName.toUpperCase()) {
                    case "FOOTBALL":
                        return 166998L;
                    case "TENNIS":
                        return 2166593L;
                    default:
                        throw new DecoderConfigException("Sport name missing at the getCompetitionId() config");
                }
            case "TEST2":
                switch (sportName.toUpperCase()) {
                    case "FOOTBALL":
                        return 12166701L;
                    case "TENNIS":
                        return 791075L;
                    default:
                        throw new DecoderConfigException("Sport name missing at the getCompetitionId() config");
                }
            case "AWS":
            case "STAGING":
                switch (sportName.toUpperCase()) {
                    case "FOOTBALL":
                        return 8765000L;
                    case "TENNIS":
                        return 791075L;
                    default:
                        throw new DecoderConfigException("Sport name missing at the getCompetitionId() config");
                }
            default:
                throw new DecoderConfigException("Missing config at getCompetitionId() for env=" + Constants.TESTING_ENV);
        }
    }

    @Override
    public String decodePunterUrl(String customer) throws DecoderConfigException {
        return null;
    }

    @Override
    public String decodeTradingUrl(String customer) throws DecoderConfigException {
        throw new DecoderConfigException("Testing env missing at the decodeCustomerDb() config");
    }

    @Override
    public String decodeNatsUrl(String customer) throws DecoderConfigException {
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