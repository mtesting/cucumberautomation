package decoders;

import other.Constants;

public class AlgoDecoder implements Decoder {

    public Long getCompetitionId(String sportName) throws DecoderConfigException {
        Long competitionId;

        switch (sportName.toUpperCase()) {
            case "FOOTBALL":
                competitionId = 2152196L;
                break;
            case "TENNIS":
                competitionId = 4919645L;
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
            case "TESTALGO":
                return Constants.ALGO_DB_URL;
            default:
                throw new DecoderConfigException("Missing config at decodeCustomerDb() for env=" + Constants.TESTING_ENV);
        }
    }

    @Override
    public String decodeATTURL() throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "TESTALGO":
                return Constants.ALGO_ATT_URL;
            default:
                throw new DecoderConfigException("Missing config at decodeATTURL() for env=" + Constants.TESTING_ENV);
        }


    }

    @Override
    public String decodeCustomerDbPassword () throws DecoderConfigException {
        return "";
    }

}
