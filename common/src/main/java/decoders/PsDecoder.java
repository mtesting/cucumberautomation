package decoders;

import other.Constants;

public class PsDecoder implements Decoder {

    public Long getCompetitionId(String sportName) throws DecoderConfigException {
        Long competitionId;

        switch (sportName.toUpperCase()) {
            case "FOOTBALL":
                competitionId = 2152170L;
                break;
            case "HR":
                competitionId = 4653303L;
                break;
            case "BADMINTON":
                competitionId = 4502402L;
                break;
            case "TENNIS":
                competitionId = 4977377L;
                break;
            default:
                throw new DecoderConfigException("Sport name missing at the getCompetitionId() config");
        }
        return competitionId;
    }

    @Override
    public String decodePunterUrl(String customer) throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "PsNewBench":
                return Constants.POKERSTARS_BENCHMARK_PUNTER_URL;
            case "PsStaging":
                return Constants.POKERSTARS_STAGING_PUNTER_URL;
            case "UAT":
                return Constants.POKERSTARS_UAT_PUNTER_URL;
            case "QACORE":
                return Constants.POKERSTARS_QACORE_PUNTER_URL;
            default:
                throw new DecoderConfigException("Missing config at decodePunterUrl()");
        }
    }

    @Override
    public String decodeTradingUrl(String customer) throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "PsNewBench":
                return Constants.POKERSTARS_BENCHMARK_TRADING_URL;
            case "PsStaging":
                return Constants.POKERSTARS_STAGING_TRADING_URL;
            default:
                throw new DecoderConfigException("Missing config at decodeTradingUrl()");
        }
    }

    @Override
    public String decodeCustomerDb(String customer) throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "PsNewBench":
                return Constants.POKERSTARS_BENCHMARK_DB_URL;
            case "PsStaging":
                return Constants.POKERSTARS_STAGING_DB_URL;
            case "UAT":
                return Constants.POKERSTARS_DB_URL;
            case "QACORE":
                return Constants.POKERSTARS_DB_URL;
            default:
                throw new DecoderConfigException("Missing config at decodeCustomerDb()");
        }
    }

    @Override
    public String decodeATTURL() throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "PsNewBench":
                return Constants.POKERSTARS_BENCHMARK_ATT;
            case "PsStaging":
                return Constants.POKERSTARS_STAGING_ATT;
            default:
                throw new DecoderConfigException("Missing config at decodeATTURL() for env=" + Constants.TESTING_ENV);
        }
    }

    @Override
    public String decodeCustomerDbPassword () throws DecoderConfigException {
        return "";
    }

}
