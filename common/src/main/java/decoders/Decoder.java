package decoders;


public interface Decoder {

    Long getCompetitionId(String sportName) throws DecoderConfigException;

    String decodePunterUrl(String customer) throws DecoderConfigException;

    String decodeTradingUrl(String customer) throws DecoderConfigException;

    default String decodeNatsUrl(String customer) throws DecoderConfigException {
        return null;
    }

    String decodeCustomerDb(String customer) throws DecoderConfigException;

    String decodeATTURL() throws DecoderConfigException;

    String decodeCustomerDbPassword() throws DecoderConfigException;

}
