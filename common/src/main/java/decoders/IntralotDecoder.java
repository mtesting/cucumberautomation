package decoders;

import other.Constants;

public class IntralotDecoder implements Decoder {

    public Long getCompetitionId(String sportName) throws DecoderConfigException {
        Long competitionId;

        switch (sportName.toUpperCase()) {
            case "FOOTBALL":
                competitionId = 2152170L;
                break;
            case "HR":
                competitionId = 4195660L;
                break;
            case "TENNIS":
                competitionId = 4809703L;
                break;
            default:
                throw new DecoderConfigException("Sport name missing at the getCompetitionId() config");
        }
        return competitionId;
    }

    @Override
    public String decodePunterUrl(String customer) throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "TEST1":
                return Constants.INTRALOT_TEST1_URL;
            case "INTRALOTATT":
                return Constants.INTRALOT_AUTO_URL;
            default:
                throw new DecoderConfigException("Missing config at decodePunterUrl()");
        }
    }

    @Override
    public String decodeTradingUrl(String customer) throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "TEST1":
                return Constants.INTRALOT_TEST1_TRADING_URL;
            case "INTRALOTATT":
                return Constants.INTRALOT_AUTO_TRADING_URL;
            default:
                throw new DecoderConfigException("Missing config at decodeTradingUrl()");
        }
    }

    @Override
    public String decodeNatsUrl(String customer) throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "INTRALOTATT":
                return Constants.INTRALOT_AUTO_NATS_URL;
            default:
                throw new DecoderConfigException("Missing config at decodeNatsUrl()");
        }
    }

    @Override
    public String decodeCustomerDb(String customer) throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "INTRALOTATT":
                return Constants.INTRALOT_AUTO_DB_URL;
            case "TEST1":
                return Constants.INTRALOT_TEST1_DB_URL;
            default:
                throw new DecoderConfigException("Missing config at decodeCustomerDb()");
        }
    }

    @Override
    public String decodeATTURL() throws DecoderConfigException {
        return "";
    }

    @Override
    public String decodeCustomerDbPassword () throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "INTRALOTATT":
                return "B32h3$2215%234d!2234mwvd";
            case "TEST1":
                return null;
            default:
                throw new DecoderConfigException("Missing config at decodeCustomerDbPassword()");
        }
    }

}
