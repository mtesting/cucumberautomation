package decoders;

import other.Constants;

public class MyBetDecoder implements Decoder {

    public Long getCompetitionId(String sportName) throws DecoderConfigException {
        Long competitionId;

        switch (sportName.toUpperCase()) {
            case "FOOTBALL":
                competitionId = 2152170L;
                break;
            case "HR":
                competitionId = 666L;
                break;
            case "BADMINTON":
                competitionId = 666L;
                break;
            case "TENNIS":
                competitionId = 4043641L;
                break;
            default:
                throw new DecoderConfigException("Sport name missing at the getCompetitionId() config");
        }

        return competitionId;
    }

    @Override
    public String decodePunterUrl(String customer) throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "STAGE":
                switch (customer.toUpperCase()) {
                    case "MYBET":
                        return Constants.MYBET_STAGE_PUNTER_URL;
                    case "MYBET_CMS":
                        return Constants.MYBET_STAGE_CMS_URL;
                    case "MYBET_BACKOFFICE":
                        return Constants.MYBET_STAGE_BACKOFFICE_URL;
                }
            case "DEMO":
                switch (customer.toUpperCase()) {
                    case "MYBET":
                        return Constants.MYBET_DEMO_PUNTER_URL;
                    case "MYBET_CMS":
                        return Constants.MYBET_DEMO_CMS_URL;
                    case "MYBET_BACKOFFICE":
                        return Constants.MYBET_DEMO_BACKOFFICE_URL;
                }
            case "MyBetAuto":
                switch (customer.toUpperCase()) {
                    default:
                        return Constants.MYBET_AUTO_PUNTER_URL;
                }
            default:
                throw new DecoderConfigException("Missing config at decodePunterUrl() for env=" + Constants.TESTING_ENV + ", customer=" + customer);
        }
    }

    @Override
    public String decodeTradingUrl(String customer) throws DecoderConfigException {
        return null;
    }

    @Override
    public String decodeCustomerDb(String customer) throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "STAGE":
                switch (customer.toUpperCase()) {
                    case "MYBET_ATS_DB":
                        return Constants.MYBET_STAGE_DB_SPORTSBOOK_URL;
                    case "MYBET_BETS_DB":
                        return Constants.MYBET_STAGE_DB_BETCATCH_URL;
                }
            case "DEMO":
                switch (customer.toUpperCase()) {
                    case "MYBET_ATS_DB":
                        return Constants.MYBET_DEMO_DB_SPORTSBOOK_URL;
                    case "MYBET_BETS_DB":
                        return Constants.MYBET_DEMO_DB_BETCATCH_URL;
                }
            case "MyBetAuto":
                switch (customer.toUpperCase()) {
                    case "MyBetAuto":
                        return Constants.MYBET_AUTO_DB_SPORTSBOOK_URL;
                    case "MYBET_BETS_DB":
                        return Constants.MYBET_AUTO_DB_BETCATCH_URL;
                }
            default:
                throw new DecoderConfigException("Missing config at decodeCustomerDb() for env="
                        + Constants.TESTING_ENV + ", customer=" + customer);
        }
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
