package database;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ats.betting.model.domain.Instrument;
import ats.betting.model.domain.Market;
import ats.betting.model.domain.MarketState;

public class DataBaseHelper {

    private static final Logger log = Logger.getLogger(DataBaseHelper.class);

    private Connection con;
    private Statement st;
    private ResultSet rs;

    //DB settings
    private String url;
    private String user;
    private String password;

    private DataBaseHelper() {
    }

    public DataBaseHelper(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * Opens the DB connection
     */
    public void connect() throws SQLException {
        try {
            con = DriverManager.getConnection(url, user, password);
            log.info("Connection open to url=" + url);
        } catch (SQLException ex) {
            log.error("Failed connecting to " + url, ex);
            throw ex;
        }
    }

    /**
     * Closes the DB connection
     */
    public void disconnect() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
                log.info("Connection closed");
            }
        } catch (SQLException ex) {
            log.error("Error closing connection from " + url, ex);
        }
    }

    /**
     * Executes a query retrieving the result's first line
     *
     * @param query query to execute
     * @return first line result
     */
    public String executeQuery(String query) {
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                log.debug(rs.getString(1));
            }
            log.info("Executed query=" + query);
            return rs.getString(1);
        } catch (SQLException ex) {
            log.error("Failed to execute query " + query, ex);
            return null;
        }
    }

    /**
     * Executes a query retrieving all the results
     *
     * @param query query to execute
     * @return results set
     */
    public ResultSet executeQueryMap(String query) {
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                log.debug(rs.getString(1));
            }
            return rs;
        } catch (SQLException ex) {
            log.error("Failed to execute query " + query, ex);
            return null;
        }
    }

    /**
     * Searches the account id for an specific username
     *
     * @param userName account username
     * @return account id
     */
    public long getUserIdFor(String userName) {
        String userId = executeQuery("" +
                " SELECT id" +
                " FROM   accounts" +
                " WHERE  username = '" + userName + "'");
        if (userId != null) {
            return Long.parseLong(userId);
        } else {
            log.warn("User ID not found for userName=" + userName);
            return -1;
        }
    }

    /**
     * Searches for the uploaded documents on an specific account
     *
     * @param userid user account id
     * @return file size
     */
    public Map<String, Integer> getUploadedDocumentsInfoForUser(long userid) {
        Map<String, Integer> fileSizeMap = new HashMap<>(2);
        String sql = "" +
                " SELECT d.file_name AS filename, octet_length(dd.raw_data) AS filesize" +
                " FROM   account_documents d, account_documents_data dd" +
                " WHERE  dd.id = CAST(d.doc_link AS numeric) AND d.acco_id = " + userid;
        List<Map<String, Object>> rows = (List<Map<String, Object>>) executeQueryMap(sql);
        if (rows != null) {
            for (Map<String, Object> row : rows) {
                fileSizeMap.put(row.get("filename").toString(), Integer.valueOf(row.get("filesize").toString()));
            }
        } else {
            log.warn("Query returned a null value");
        }
        return fileSizeMap;
    }

    public void documentsCleanup(String user) {
        String sql = "" +
                " DELETE FROM account_documents" +
                " WHERE acco_id = " + getUserIdFor(user);
        executeQuery(sql);
    }

    //TODO ideally use Market class from generated.ats.sportsbook.punter.dto (?)
    public ArrayList<Market> getMarketsFromDB(String eventId) throws SQLException {
        ArrayList<Market> marketslist = new ArrayList<>();
        String qryMarkets = "" +
                "SELECT id, name, state, bir, mrkt_type, settled, visible, ref1, sys_ref " +
                "FROM MARKETS " +
                "WHERE NODE_ID = ";
        connect();
        try {
            // Create a Statement class to execute the SQL statement
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(qryMarkets + eventId);
            while (rs.next()) {
                Market market = new Market();
                String markId = rs.getString("id");
                market.setId(rs.getLong("id"));
                market.setName(rs.getString("name"));
                market.setState(MarketState.valueOf(rs.getString("state")));
                //market.setRef(1, rs.getString("ref1")); //TODO should the ref1 be read?
                market.setType(rs.getString("mrkt_type"));
                market.setSettled(rs.getBoolean("settled"));
                market.setVisible(rs.getBoolean("visible"));
                Set<Instrument> selections = getInstrumentsFromDB(markId);
                market.setInstruments(selections);
                log.debug(market.getName());
                marketslist.add(market);
                log.info("Market created for the eventId=" + eventId + " from ATS DB");
            }
        } catch (SQLException e) {
            log.error(e);
        }
        disconnect();
        return marketslist;
    }

    private Set<Instrument> getInstrumentsFromDB(String markId) throws SQLException {
        Set<Instrument> instruments = new HashSet<>();
        String instrumentQry = "" +
                "SELECT id, name, settled, result, selection_id, visible, ref1, place, sys_ref " +
                "FROM INSTRUMENTS " +
                "WHERE MARK_ID = ";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(instrumentQry + markId);
        while (rs.next()) {
            Instrument selection = new Instrument();
            selection.setId(rs.getLong("id"));
            selection.setName((rs.getString("name")));
            selection.setResult((rs.getString("result")));
            log.debug(selection.getName());
            instruments.add(selection);
        }
        log.info("Instruments created for the marketId=" + markId + " from ATS DB");
        return instruments;
    }

}