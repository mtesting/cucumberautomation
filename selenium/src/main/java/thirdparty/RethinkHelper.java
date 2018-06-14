package thirdparty;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

/**
 * Rethink DataBase interaction helper
 */
public class RethinkHelper {

    private static final Logger log = Logger.getLogger(RethinkHelper.class);

    private static final RethinkDB r = RethinkDB.r;
    private final String host;
    private final int port;
    private Connection conn;

    public RethinkHelper(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private void connect() {
        try {
            conn = r.connection().hostname(host).port(port).connect();
        } catch (Exception e) {
            log.error(e);
        }
    }

    private void disConnect() {
        try {
            conn.close();
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Gets the activeEnd time of the latest promo created and deletes all the previous ones
     */
    public void mybetPromotionsCleanup() {
        connect();
        //long epoch = System.currentTimeMillis() - 1000000;
        try {
            List<HashMap> lastActive = r.db("mybet").table("Promotion")
                    .filter(row -> row.g("name").match("^promo-"))
                    .pluck("activeEnd")
                    .orderBy(r.desc("activeEnd"))
                    .limit(1).run(conn);
            final Long epoch = (Long) lastActive.get(0).get("activeEnd");
            long records = r.db("mybet").table("Promotion")
                    .filter(row -> row.g("name").match("^promo-"))
                    .filter(row -> row.g("activeEnd").lt(epoch))
                    .count().run(conn);
            log.info("Promotions clean up will delete: " + records + " records");
            r.db("mybet").table("Promotion").filter(row -> row.g("name").match("^promo-")).filter(row -> row.g("activeEnd").lt(epoch)).delete().run(conn);
        } catch (Exception e) {
            log.error(e);
        }
        disConnect();
    }

}
