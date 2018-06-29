package feeds;

import uk.co.amelco.testscenario.abelson.Market;
import uk.co.amelco.testscenario.abelson.Match;
import uk.co.amelco.testscenario.abelson.Team;
import uk.co.amelco.testscenario.selection.Selection;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import ats.betting.trading.att.ws.scenario.dto.Event;
import ats.betting.trading.att.ws.scenario.dto.GoalscorerMarketType;
import ats.betting.trading.att.ws.scenario.dto.Result;
import ats.betting.trading.att.ws.scenario.dto.SelectionSide;
import util.Utils;

public class AbelsonHelper {

    private List<Match> matchList;

    public AbelsonHelper() {
    }

    /**
     * Generates an Abelson match
     *
     * @param event ATS event
     * @return ArrayList
     */
    public List<Match> createAbelsonEvent(Event event) {
        matchList = new ArrayList<>();
        Match match = new Match();

        match.setEventRef(Long.parseLong(event.getEventRef()));
        match.setCompetitionId(event.getCompetitionId()); //Abelson competition node sys_ref
        match.setStatus(AbelsonEventStatus.PreMatch.value);
        match.setInplayCoverage(true);

        Team homeTeam = new Team();
        homeTeam.setTeamId(8825); //It has to be a valid sys_ref from entity_sys_refs

//        MatchLineup matchLineupHome = new MatchLineup();
//        matchLineupHome.setPlayerId(12L);
//        matchLineupHome.setPlayerName("David");
//        matchLineupHome.setSubstitute(false);
//        homeTeam.getLineup().add(matchLineupHome);
        match.setHome(homeTeam);

        Team awayTeam = new Team();
        awayTeam.setTeamId(8826); //It has to be a valid sys_ref from entity_sys_refs

//        MatchLineup matchLineupAway = new MatchLineup();
//        matchLineupAway.setPlayerId(13L);
//        matchLineupAway.setPlayerName("Franco");
//        matchLineupAway.setSubstitute(false);
//        awayTeam.getLineup().add(matchLineupAway);
        match.setAway(awayTeam);

        Match.Markets markets = new Match.Markets();

        match.setMarkets(markets);

        matchList.add(match);

        return matchList;
    }

    public Market resultAbelsonMarket(GoalscorerMarketType goalscorerMarketType, String winner) {
        Market market = returnAbelsonMarket(goalscorerMarketType);
        switch (winner) {
            case "home":
                for (Selection selection1 : market.getSelection()) {
                    selection1.setResult(Result.LOSE);
                }
                market.getSelection().get(4).setResult(Result.WIN);
                break;
            case "away":
                for (Selection selection1 : market.getSelection()) {
                    selection1.setResult(Result.LOSE);
                }
                market.getSelection().get(15).setResult(Result.WIN);
                break;
            case "draw":
                for (Selection selection1 : market.getSelection()) {
                    selection1.setResult(Result.LOSE);
                }
                break;
            default:
                for (Selection selection1 : market.getSelection()) {
                    selection1.setResult(Result.VOID);
                }
                break;
        }
        return market;
    }

    private Market returnAbelsonMarket(GoalscorerMarketType goalscorerMarketType) throws NoSuchElementException {
        for (Market market : matchList.get(0).getMarkets().getMarket()){
            if (goalscorerMarketType.equals(market.getType())){
                return market;
            }
        }
        throw new NoSuchElementException("Abelson market type=" + goalscorerMarketType.toString() + " not found");
    }

    private enum AbelsonEventStatus {

        Fixture("Fixture"),
        PreMatch("Pre-Match"),
        FirstHalf("1st Half"),
        HalfTime("Half Time"),
        SecondHalf("2nd Half"),
        FullTime("Full Time");

        private final String value;

        AbelsonEventStatus(String v) {
            value = v;
        }

        public String value() {
            return value;
        }

        public static AbelsonEventStatus fromValue(String v) {
            for (AbelsonEventStatus c : AbelsonEventStatus.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(v);
        }

    }

    /**
     * Generates the full squad for each team including price and name
     *
     * @return ArrayList
     */
    private List<Selection> generatePlayers() {
        List<Selection> selections = new ArrayList<>();
        Selection selection;
        for (int x = 0; x < 22; x++) {
            selection = new Selection();
            selection.setSsId(Utils.randomNumbers(5));
            selection.setName(Utils.randomAlphabet(10));
            selection.setPrice("11/2");
            if (x < 11) {
                selection.setSide(SelectionSide.A);
            } else {
                selection.setSide(SelectionSide.H);
            }
            selections.add(selection);
        }
        return selections;
    }

    /**
     * Generates an abelson market with priced selections
     * @param marketType Abelson market type
     * @return market object
     */
    public Market generateMarket(GoalscorerMarketType marketType){
        Market market = new Market();
        market.setAbelsonMarketId(Long.parseLong(Utils.randomNumbers(9)));
        market.setType(marketType);
        market.getSelection().addAll(generatePlayers());
        return market;
    }

}