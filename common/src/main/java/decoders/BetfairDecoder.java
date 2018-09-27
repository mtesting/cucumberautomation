package decoders;

import other.Constants;
import util.StringEncrypt;

public class BetfairDecoder implements Decoder {

    public Long getCompetitionId(String sportName) throws DecoderConfigException {
        Long competitionId;

        switch (sportName.toUpperCase()) {
            case "BADMINTON":
                competitionId = 4502402L;
                break;
            case "TENNIS":
                competitionId = 4491414L;
                break;
            case "TENNIS DOUBLES":
                competitionId = 4328343L;
                break;
            case "TENNIS FIVESETIMG":
                competitionId = 4619375L;
                break;

            //case "TENNIS FIVESETBR":   /* Added for Betradar 5 set in case it is added in the project later */
             //   competitionId = 4491414L;
             //   break;

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
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "PPBF":
                return Constants.BETFAIR_PPBF_TRADING_URL;
            default:
                throw new DecoderConfigException("Missing config at decodeTradingUrl()");
        }
    }

    @Override
    public String decodeATTURL() throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "PPBF":
                return Constants.BF_PPBF_ATT;
            default:
                throw new DecoderConfigException("Missing config at decodeATTURL() for env=" + Constants.TESTING_ENV);
        }
    }

    @Override
    public String decodeCustomerDb(String customer) throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "PPBF":
                return Constants.BF_PPBF_DB_URL;
            default:
                throw new DecoderConfigException("Missing config at decodeCustomerDb() for env=" + Constants.TESTING_ENV + ", customer=" + customer);
        }

    }

    @Override
    public String decodeCustomerDbPassword () throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "PPBF":
                return StringEncrypt.decryptXOR(Constants.BF_PPBF_DB_PASSWORD);
            default:
                throw new DecoderConfigException("Missing config at decodeCustomerDbPassword() for env=" + Constants.TESTING_ENV);
        }
    }

}
