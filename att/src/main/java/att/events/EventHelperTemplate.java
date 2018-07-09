package att.events;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import ats.betting.trading.att.ws.scenario.ScenarioDefinitionException;
import ats.betting.trading.att.ws.scenario.SendIncidentException;
import ats.betting.trading.att.ws.scenario.dto.BetResult;
import ats.betting.trading.att.ws.scenario.dto.Betradarliveodds;
import ats.betting.trading.att.ws.scenario.dto.Event;
import ats.betting.trading.att.ws.scenario.dto.EventDefinition;
import ats.betting.trading.att.ws.scenario.dto.Incident;
import ats.betting.trading.att.ws.scenario.dto.PeriodScore;
import decoders.DecoderConfigException;

public interface EventHelperTemplate {

    /**
     * Creates events for on-demand mode
     * @param competitionId event competition id
     * @param incidentsFeedProvider incidents feed
     * @param matchesTotal number of events to be created
     * @param matchesInplay events in-play
     * @param pricingFeedProvider pricing feed
     * @param markets event markets (for mirror feed)
     * @throws ScenarioDefinitionException wrong scenario definition sent to ATT
     */
    void createEvent(long competitionId, String incidentsFeedProvider, int matchesTotal, int matchesInplay,
            String pricingFeedProvider, String startDateTime, EventDefinition.Markets markets) throws Throwable;

    /**
     * Creates events for re-play mode calling to ATT scheduleScenario
     * @param competitionId event competition id
     * @param incidentsFeedProvider incidents feed
     * @param matchesTotal number of events to be created
     * @param matchesInplay events in-play
     * @param pricingFeedProvider pricing feed
     * @param dataset data-set to replay
     * @param markets event markets (for mirror feed)
     * @throws ScenarioDefinitionException wrong scenario definition sent to ATT
     */
    void createEventSchedule(long competitionId, String incidentsFeedProvider, int matchesTotal, int matchesInplay,
            String pricingFeedProvider, String dataset, EventDefinition.Markets markets, String eventStartDateTime) throws Throwable;

    void assertEventIsCreated();

    boolean waitForEventToBeInplay();

    void waitForEventToActiveDisplayed();

    void scheduleInplay();

    void sendIncidents(List<Incident> incidentsList) throws SendIncidentException;

    /**
     * Starts/Stops the Betradar alive messages
     * The client should expect a message from the server at least every 8
     * seconds. In return, the client system should send the server a message every 30 seconds.
     *
     * @param active false stops
     */
    void setBetradarAlive(boolean active);

    /**
     * Method for pre-match BR Mirror resulting
     * only the winning selections should be sent
     */
    void sendBetradarMirrorResults(List<PeriodScore> periodScores, List<BetResult> betResults);

    void updateMirror(EventDefinition.Markets markets) throws ScenarioDefinitionException;

    void sendLiveOddsUpdate(List<Betradarliveodds> betradarliveoddsList);

    void processIncidents(String incidentsFile, String sheetName) throws SendIncidentException, IOException;

    void processLSportsIncidents(String incidentsFile);

    default void addAbelsonFeed() {

    }

    Boolean assertMarketsSettled(String dbUrl) throws DecoderConfigException, SQLException;

    Event getEvent();

    List<Event> getEvents();

    String getPartA();

    String getPartB();

}