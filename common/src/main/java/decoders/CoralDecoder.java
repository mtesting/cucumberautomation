package decoders;

import other.Constants;

public class CoralDecoder implements Decoder {

    public Long getCompetitionId(String sportName) throws DecoderConfigException {
        Long competitionId;

        switch (sportName.toUpperCase()) {
            case "FOOTBALL":
                competitionId = 166992L;
                break;
            case "TENNIS":
                competitionId = 3010344L;
                break;
            case "HR":
                competitionId = 666L;
                break;
            case "BADMINTON":
                competitionId = 666L;
                break;
            default:
                throw new DecoderConfigException("Sport name missing at the getCompetitionId() config");
        }

        return competitionId;
    }

    @Override
    public String decodePunterUrl(String customer) throws DecoderConfigException {
        switch (Constants.TESTING_ENV.toUpperCase()) {
            case "DEV2":
                switch (customer.toUpperCase()) {
                    case "CORAL":
                        return Constants.CORAL_DEV2_URL;
                    case "CORAL_DB":
                        return Constants.CORAL_DEV2_DB;

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
