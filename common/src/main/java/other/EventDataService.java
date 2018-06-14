package other;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DataBaseHelper;
import decoders.Decoder;
import decoders.DecoderConfigException;
import decoders.DecoderManager;
import generated.ats.sportsbook.punter.dto.Selection;

public class EventDataService {

    private static final Logger log = Logger.getLogger(EventDataService.class);
    private DataBaseHelper dbHelper;


    public EventDataService() throws DecoderConfigException {

    }


    public EventDataService(String dbUrl) throws DecoderConfigException {
        Decoder decoder = DecoderManager.getManager().getDecoder();
        dbHelper = new DataBaseHelper(dbUrl, Constants.ATS_DB_USER, decoder.decodeCustomerDbPassword());
    }

    public DataBaseHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(DataBaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    private List<Selection> loadSelectionsFromDb(Integer marketId) {
        List<Selection> selections = new ArrayList<>();
        Selection selection;

        //dbHelper.connect();
        ResultSet selectionsRs = dbHelper.executeQueryMap("" +
                "SELECT ID, NAME, VISIBLE " +
                "FROM INSTRUMENTS " +
                "WHERE MARK_ID = " + marketId);
        try {
            do {
                selection = new Selection();
                selection.setId(selectionsRs.getInt("id"));
                selection.setName(selectionsRs.getString("name"));
                selection.setDisplayed(selectionsRs.getBoolean("visible"));
                selections.add(selection);
            } while (selectionsRs.next());
        } catch (SQLException e) {
            log.error(e);
        }
        //dbHelper.disconnect();

        return selections;
    }

    /**
     * Gets the DB result for an specific selection
     * @param EventId ATS event id
     * @param MarketName ATS market name
     * @param SelectionName ATS instrument name
     */
    public String getResult(Integer EventId, String MarketName, String SelectionName) throws SQLException {
        //TODO Implement and test the MarketName variable in the search query
        String result = "";
        dbHelper.connect();
        ResultSet selectionsRs = dbHelper.executeQueryMap("" +
                "SELECT RESULT " +
                "FROM INSTRUMENTS " +
                "WHERE NAME ILIKE '%" + SelectionName +
                "%' AND MARK_ID in (SELECT ID FROM MARKETS WHERE NODE_ID = " + EventId
                + ")");
        try {
            result = selectionsRs.getString("result");
        } catch (SQLException e) {
            log.error(e);
        }
        dbHelper.disconnect();
        return result;
    }

}
