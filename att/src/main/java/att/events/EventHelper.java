package att.events;

import org.apache.log4j.Logger;
import org.junit.Assert;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

import ats.betting.model.domain.Market;
import ats.betting.trading.att.ws.scenario.ScenarioDefinitionException;
import ats.betting.trading.att.ws.scenario.SendIncidentException;
import ats.betting.trading.att.ws.scenario.dto.BetResult;
import ats.betting.trading.att.ws.scenario.dto.Betradarliveodds;
import ats.betting.trading.att.ws.scenario.dto.Event;
import ats.betting.trading.att.ws.scenario.dto.EventDefinition;
import ats.betting.trading.att.ws.scenario.dto.Incident;
import ats.betting.trading.att.ws.scenario.dto.Participant;
import ats.betting.trading.att.ws.scenario.dto.PeriodScore;
import ats.betting.trading.att.ws.scenario.dto.ScenarioStatus;
import att.ws.TestScenarioSupport;
import database.DataBaseHelper;
import decoders.DecoderConfigException;
import other.EventDataService;
import util.Utils;

public abstract class EventHelper implements EventHelperTemplate {

    protected static final Logger log = Logger.getLogger(EventHelper.class);

    protected final TestScenarioSupport testScenarioSupport = new TestScenarioSupport();
    protected Event event = new Event();
    protected List<Event> events = new ArrayList<>();
    protected String trackingId;
    private ScenarioStatus scenarioStatus;

    private String participantA;
    private String participantB;

    /**
     * Verifies if the requested event is created
     *
     */
    public void assertEventIsCreated() {
        int eventsPendingTimeout = 35;
        int eventCreationTimeout = 90;
        log.info("-- STEP -- to check if event created");
        scenarioStatus = testScenarioSupport.getScenarioStatus(trackingId);
        int counter = 1;

        while ((scenarioStatus.getEventsPending() > 0) && (counter < eventsPendingTimeout)) {
            counter++;
            Utils.waitSeconds(5);
            scenarioStatus = testScenarioSupport.getScenarioStatus(trackingId);
            log.info("Events Pending :::" + scenarioStatus.getEventsPending());
        }

        boolean gotIt = false;
        while (!gotIt && (counter < eventCreationTimeout)) {
            counter++;

            if (scenarioStatus.getEvents().getEvent().size() > 0) {
                for (Event newEvent : scenarioStatus.getEvents().getEvent()) {
                    List<Participant> participants = newEvent.getParticipants().getParticipant();
                    log.info("Event created with eventId=" + newEvent.getEventRef());

                    if (participants.size() > 1) {
                        gotIt = true;
                        assignParticipantsNames(participants);
                        events.add(newEvent);
                        event = newEvent;
                    } else {
                        Utils.waitSeconds(5);
                    }
                }
            }
        }
        Assert.assertTrue("Event creation failed", gotIt);
    }

    /**
     * Sends incidents to the event with a Xsec delay btw each other
     *
     * @param incidentsList List of Incident objects to be sent
     */
    public void sendIncidents(List<Incident> incidentsList) throws SendIncidentException {
        log.info("-- STEP -- send incidents");
        for (Incident incident : incidentsList) {
            List<Incident> incidentToBeSent = new ArrayList<>();
            incidentToBeSent.add(incident);
            log.info("Incident sent type=" + incident.getType());
            try {
                Assert.assertTrue("Error sending incident=" + incident.getType().name(),
                        testScenarioSupport.sendIncidents(event.getEventRef(), null, null, incidentToBeSent));
            }catch (SendIncidentException e){
                log.error("Error in sending incidents" + e.toString());
                throw e;
            }
            Utils.waitSeconds(incident.getIncidentDelay());

        }

    }

    /**
     * Polls ATT awaiting for the event to be active, authorized and display
     */
    public void waitForEventToActiveDisplayed() {
        checkIfAllEventsAreActiveDisplayedAuthorised();

        for (Event event : scenarioStatus.getEvents().getEvent()){
            if (event.isActivated() && event.isAuthorised() && event.isDisplayed()) {
                log.info("Event is Active, Authorized and Displayed");
            } else {
                Assert.fail("Event=" + event.getEventRef() + " failed to get Active=" + event.isActivated()
                        + "," + " Authorized=" + event.isAuthorised() + " and Displayed=" + event.isDisplayed());
            }
        }
    }

    public boolean waitForEventToBeInplay() {
        int timer = 60;
        while ((!(event.isInplayFlag())) && timer > 0) {
            this.scheduleInplay();
            Utils.waitSeconds(3);
            this.assertEventIsCreated();
            timer--;
        }

        if (event.isInplayFlag()) {
            log.info("Event is set to Inplay");
            return true;
        } else {
            log.warn("Event is not set to Inplay");
            return false;
        }

    }

    /**
     * Sets the event in-play flag to true
     */
    public void scheduleInplay() {
        log.info("-- STEP -- to schedule inplay ");
        testScenarioSupport.setScheduleInplay(event.getEventRef());

    }

    public void setBetradarAlive(boolean active) {
        log.info("betradar alive reply set to " + active);
        testScenarioSupport.setShouldAliveReply("betradarLiveScouting", active);
        testScenarioSupport.setShouldAliveReply("betradarLiveOdds", active);
    }

    public void sendBetradarMirrorResults(List<PeriodScore> periodScores, List<BetResult> betResults) {
        log.info("-- STEP -- send Betradar Mirror results");
        Assert.assertTrue("Failed sending BR Mirror resulting",
                testScenarioSupport.sendMirroringResults(event.getEventRef(), periodScores, betResults));
    }

    public void updateMirror(EventDefinition.Markets markets) throws ScenarioDefinitionException {
        log.info("-- STEP -- send Betradar Mirror update");
        EventDefinition eventDefinition = new EventDefinition();
        eventDefinition.setMarkets(markets);
        Assert.assertTrue("BetradarMirror update failed",
                testScenarioSupport.updateMirroredEvent(eventDefinition , event.getEventRef()));
    }

    public void sendLiveOddsUpdate(List<Betradarliveodds> betradarliveoddsList){
        log.info("-- STEP -- send LiveOdds feed update");
        for (Betradarliveodds betradarliveodds : betradarliveoddsList){
            Assert.assertTrue("LiveOdds feed update failed",
                    testScenarioSupport.sendLiveOddsUpdates(event.getEventRef(), betradarliveodds));
            Utils.waitSeconds(5);
        }
    }

    @Override
    public void processIncidents(String incidentsFile, String sheetName) throws SendIncidentException, IOException {

    }

    @Override
    public void processLSportsIncidents(String incidentsFile) {

    }

    /**
     * Validates if all the Markets in the event are settled
     * @param url DB url
     * @throws DecoderConfigException missing config error
     */
    public Boolean assertMarketsSettled(String url) throws DecoderConfigException, SQLException {
        log.info("--STEP-- check if event markets are settled");
        EventDataService eds = new EventDataService(url);
        DataBaseHelper db = eds.getDbHelper();
        List<Market> marketList = db.getMarketsFromDB(event.getEventRef());
        for (Market market : marketList) {
            Assert.assertTrue("Failed to settle market=" + market.getName(), market.isSettled());
        }
        return true;
    }

    @Override
    public void createEvent(long competitionId, String incidentsFeedProvider, int matchesTotal, int matchesInplay,
                            String pricingFeedProvider, String startDateTime, EventDefinition.Markets markets) throws ScenarioDefinitionException {
        log.info("-- STEP -- to create an event");
        setEvent(new Event());
        try {

            trackingId = testScenarioSupport.getTrackingIdforPrepareScenario(competitionId, incidentsFeedProvider,
                    matchesTotal, matchesInplay, pricingFeedProvider, startDateTime, "", markets);

            log.info("Tracking id :::" + trackingId);

            synchronized (this) {
                Utils.waitSeconds(20);
            }

        } catch (DatatypeConfigurationException | ParseException e) {
            log.error(e);
        }
    }

    public void createEventSchedule(long competitionId, String incidentsFeedProvider, int matchesTotal, int matchesInplay,
                                    String pricingFeedProvider, String dataset, EventDefinition.Markets markets, String eventStartDateTime) throws ScenarioDefinitionException {
        log.info("-- STEP -- to create an schedule event");
        setEvent(new Event());
        try {

            trackingId = testScenarioSupport.getTrackingIdforScheduleScenario(competitionId, incidentsFeedProvider,
                    matchesTotal, matchesInplay, pricingFeedProvider, eventStartDateTime, dataset, markets);

            log.info("Tracking id :::" + trackingId);

            synchronized (this) {
                Utils.waitSeconds(20);
            }

        } catch (DatatypeConfigurationException | ParseException e) {
            log.error(e);
        }
    }

    private void checkIfAllEventsAreActiveDisplayedAuthorised(){
        int timeOut = 120;
        int allEventsActive = 0;

        while (timeOut > 0) {
            log.info("Waiting for events to get Active, Authorised and Displayed");
            Utils.waitSeconds(10);
            scenarioStatus = testScenarioSupport.getScenarioStatus(trackingId);
            for (Event event : scenarioStatus.getEvents().getEvent()){
                if ((!(event.isActivated() && event.isAuthorised() && event.isDisplayed()))){
                    break;
                } else {
                    allEventsActive++;
                }
            }
            if (scenarioStatus.getEvents().getEvent().size() == allEventsActive){
                break;
            } else {
                allEventsActive = 0;
            }
            timeOut--;
        }
    }

    private void assignParticipantsNames(List<Participant> participants){
        if (participants.get(0).getKey().equals("HOME")) {
            participantA = participants.get(0).getName();
            participantB = participants.get(1).getName();
        } else if (participants.get(1).getKey().equals("HOME")) {
            participantA = participants.get(1).getName();
            participantB = participants.get(0).getName();
        } else if (participants.get(1).getKey().equals("PLAYERA")) {
            participantA = participants.get(1).getName();
            participantB = participants.get(0).getName();
        } else if (participants.get(1).getKey().equals("PLAYERB")) {
            participantA = participants.get(0).getName();
            participantB = participants.get(1).getName();
        } else {
            participantA = "";
            participantB = "";
        }
        log.info("Home Team : " + participantA);
        log.info("Away Team : " + participantB);
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getPartA() {
        return participantA;
    }

    public String getPartB() {
        return participantB;
    }

    public Event getEvent() {
        return event;
    }

    public List<Event> getEvents() {
        return events;
    }

}