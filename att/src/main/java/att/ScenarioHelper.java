package att;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import ats.betting.trading.att.ws.scenario.dto.Competition;
import ats.betting.trading.att.ws.scenario.dto.EventDefinition;
import ats.betting.trading.att.ws.scenario.dto.ScenarioDefinition;
import util.DateUtil;

public class ScenarioHelper {

    private long competitionId;
    private int matchesTotal;
    private int matchesInplay;
    private String incidentsFeedProvider;
    private String pricingFeedProvider;
    private String startDateTime;
    private String dataset;
    private EventDefinition.Markets markets;

    public ScenarioHelper(long competitionId, String incidentsFeedProvider, int matchesTotal,
            int matchesInplay, String pricingFeedProvider, String startDateTime, String dataset, EventDefinition.Markets markets){
        this.competitionId = competitionId;
        this.incidentsFeedProvider = incidentsFeedProvider;
        this.matchesTotal = matchesTotal;
        this.matchesInplay = matchesInplay;
        this.pricingFeedProvider = pricingFeedProvider;
        this.startDateTime = startDateTime;
        this.dataset = dataset;
        this.markets = markets;
    }

    public ScenarioDefinition prepareScenarioDefinition() throws DatatypeConfigurationException, ParseException {
        ScenarioDefinition.Competitions competitions = prepareCompetitions(competitionId, incidentsFeedProvider, matchesTotal,
                matchesInplay, pricingFeedProvider);
        competitions = addEventsToCompetitions(competitions, dataset, markets, matchesTotal);

        ScenarioDefinition scenarioDefinition = new ScenarioDefinition();

        scenarioDefinition.setCompetitions(competitions);
        scenarioDefinition.setEndpointMonitoring(false);

        XMLGregorianCalendar startTime = getStartTime(startDateTime);
        scenarioDefinition.setStart(startTime);

        return scenarioDefinition;
    }

    private ScenarioDefinition.Competitions prepareCompetitions(long competitionId, String incidentsFeedProvider, int matchesTotal,
            int matchesInplay, String pricingFeedProvider) {
        Competition competition = new Competition();
        competition.setId(competitionId);
        competition.setIncidents(incidentsFeedProvider);
        competition.setMatchesTotal(matchesTotal);
        competition.setMatchesInplay(matchesInplay);
        if (!pricingFeedProvider.trim().equals(""))
            competition.setPricing(pricingFeedProvider.trim());

        ScenarioDefinition.Competitions competitions = new ScenarioDefinition.Competitions();
        competitions.getCompetition().add(competition);

        return competitions;
    }

    private ScenarioDefinition.Competitions addEventsToCompetitions(ScenarioDefinition.Competitions competitions, String dataset, EventDefinition.Markets markets, int matchesTotal) {
        if (!Objects.equals(dataset, "") || markets != null) {
            for (Competition comp : competitions.getCompetition()) {
                for(int x=0; x < matchesTotal; x++){
                    EventDefinition event = prepareEvent(markets, dataset);
                    if (event != null) {
                        comp.getEvent().add(event);
                    }
                }
            }
        }
        return competitions;
    }

    private EventDefinition prepareEvent(EventDefinition.Markets markets, String dataset) {
        EventDefinition event = new EventDefinition();
        if (markets != null) {
            event.setMarkets(markets);
        }
        if (!Objects.equals(dataset, "") && !dataset.contains("SCENARIO_NAME")) {
            event.setDataset(dataset);

        }
        return event;
    }

    private XMLGregorianCalendar getStartTime(String startDateTime) throws DatatypeConfigurationException, ParseException {
        Date date;
        GregorianCalendar c = new GregorianCalendar();
        if (startDateTime.trim().equalsIgnoreCase("currenttime")) {
            date = DateUtil.getCurrentTimeAsDateInFormat("dd/MM/yyyy H:m", Calendar.MINUTE, 30);
        } else if (startDateTime.trim().equalsIgnoreCase("tomorrow")) {
            date = DateUtil.getCurrentTimeAsDateInFormat("dd/MM/yyyy H:m", Calendar.DAY_OF_MONTH, 1);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy H:m");
            date = formatter.parse(startDateTime);
        }
        c.setTime(date);

        return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
    }

}
