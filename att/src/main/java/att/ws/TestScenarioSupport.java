package att.ws;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.log4j.Logger;

import uk.co.amelco.testscenario.abelson.Match;

import java.util.List;

import ats.betting.trading.att.ws.scenario.ScenarioDefinitionException;
import ats.betting.trading.att.ws.scenario.SendIncidentException;
import ats.betting.trading.att.ws.scenario.TestScenarioService;
import ats.betting.trading.att.ws.scenario.TestScenarioService_Service;
import ats.betting.trading.att.ws.scenario.dto.BetResult;
import ats.betting.trading.att.ws.scenario.dto.Betradarliveodds;
import ats.betting.trading.att.ws.scenario.dto.EventDefinition;
import ats.betting.trading.att.ws.scenario.dto.EventMarket;
import ats.betting.trading.att.ws.scenario.dto.Incident;
import ats.betting.trading.att.ws.scenario.dto.LsportsIncident;
import ats.betting.trading.att.ws.scenario.dto.Outcome;
import ats.betting.trading.att.ws.scenario.dto.PeriodScore;
import ats.betting.trading.att.ws.scenario.dto.RaceMeetingDefinition_0020;
import ats.betting.trading.att.ws.scenario.dto.RaceUpdate;
import ats.betting.trading.att.ws.scenario.dto.ScenarioDefinition;
import ats.betting.trading.att.ws.scenario.dto.ScenarioStatus;

public class TestScenarioSupport {

    private static final Logger log = Logger.getLogger(TestScenarioSupport.class);

    private final TestScenarioService service;

    public TestScenarioSupport() {

        TestScenarioService_Service s = new TestScenarioService_Service();
        service = s.getTestScenarioServicePort();
        Client client = ClientProxy.getClient(service);
        Endpoint endpoint = client.getEndpoint();

        // Enable Logging
        // from http://cwiki.apache.org/CXF20DOC/debugging.html
        client.getInInterceptors().add(new LoggingInInterceptor());
        client.getOutInterceptors().add(new LoggingOutInterceptor());

    }

    public ScenarioStatus getScenarioStatus(String trackingId) {
        return service.pollScenarioPreparation(trackingId);
    }

    /**
     * sends a request to create an event on-demand and returns the tracking id
     */
    public String getTrackingIdForPrepareScenario(ScenarioDefinition scenarioDefinition) throws ScenarioDefinitionException {
        try{
            return service.prepareScenario(scenarioDefinition);
        }
        catch (ScenarioDefinitionException e){
            log.error(e.getFaultInfo().getDetails().getDetail().get(0).getMessage(), e);
            throw e;
        }
    }

    /**
     * Creates an event on replay mode
     */
    public String getTrackingIdForScheduleScenario(ScenarioDefinition scenarioDefinition) throws ScenarioDefinitionException {
        try{
            return service.scheduleScenario(scenarioDefinition);
        }
        catch (ScenarioDefinitionException e){
            log.error(e.getFaultInfo().getDetails().getDetail().get(0).getMessage(), e);
            throw e;
        }
    }

    public EventDefinition prepareEvents(EventMarket market) {
        EventDefinition event = new EventDefinition();
        EventDefinition.Markets markets = new EventDefinition.Markets();
        markets.getMarket().add(market);

        return event;
    }

    public void setScheduleInplay(String eventRef) {
        service.scheduleInplay(eventRef);
    }

    public String prepareRaceMeeting(RaceMeetingDefinition_0020 raceMeeting) throws ScenarioDefinitionException {
        try{
            return service.prepareRaceMeeting(raceMeeting);
        }
        catch (ScenarioDefinitionException e){
            log.error(e.getFaultInfo().getDetails().getDetail().get(0).getMessage(), e);
            throw e;
        }
    }

    public boolean getScheduleInplayStatus(String eventRef) {
        return service.checkScheduledInplay(eventRef);
    }

    /**
     * Sets a Horse Racing event as authorised, displayed, active and in-play
     *
     * @param eventRef event ats id
     */
    public void launchEvent(String eventRef) {
        service.launchEvent(eventRef, true, true, true, true);
    }

    public boolean setRaceUpdates(RaceUpdate raceUpdates, String eventRef) {
        return service.setRaceUpdates(eventRef, raceUpdates);
    }

    public boolean saveRacingResult(String eventid, List<Outcome> outcomeList) {
        log.info("-- STEP -- save race results");
        return service.saveRacingResults(eventid, outcomeList);
    }

    public boolean settleRaceEvent(String eventid) {
        log.info("-- STEP -- settle race event");
        return service.settleRacingEvent(eventid);
    }

    public void BookIncident(String sport) throws ats.betting.trading.att.ws.scenario.JAXBException {
        service.bookIncidentBetsync(sport);
    }

    public boolean sendIncidents(String eventRef, String eventFeedId, String feedId, List<Incident> incidents) throws SendIncidentException {
        return service.sendIncidents(eventRef, eventFeedId, feedId, incidents);
    }

    public void setShouldAliveReply(String feed, boolean isActive){
        service.setShouldAliveReply(feed, isActive);
    }

    public Boolean sendMirroringResults(String eventRef, List<PeriodScore> periodScores, List<BetResult> betResults){
        return service.sendMirroringResults(eventRef, periodScores, betResults);
    }

    public Boolean updateMirroredEvent(EventDefinition eventDefinition, String eventRef) throws ScenarioDefinitionException {
        return service.updateMirroredEvent(eventDefinition, eventRef);
    }

    public Boolean sendLiveOddsUpdates(String eventRef, Betradarliveodds betradarliveodds){
        return service.sendLiveOddsUpdates(eventRef, betradarliveodds);
    }

    public List<Match> prepareAbelsonMatches(List<Match> abelsonEvent) {
        return service.prepareAbelsonMatches(abelsonEvent);
    }

    public boolean sendLsportsIncidents(String eventRef, LsportsIncident incident) {
        return service.sendLsportsIncidents(eventRef, incident);
    }

    public boolean updateEachway(long marketId, String numPlaces) {
        return service.updateEachway(marketId, numPlaces);
    }

    public void setMarketFlags(String marketId, boolean displayed, String state, boolean forecast, boolean tricast, boolean livePrice) {
        service.setMarketFlags(marketId, displayed, state, forecast, tricast, livePrice);
    }

}