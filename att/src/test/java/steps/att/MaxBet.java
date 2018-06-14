package steps.att;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import apiLevelInteraction.SportsbookHelper;
import att.events.RaceHelper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import generated.ats.sportsbook.punter.dto.Selection;

public class MaxBet {

    private final SportsbookHelper sportsbook;
    private List<Selection> selections;
    private ArrayList<RaceHelper> events;
    private Double maxBet;
    private String betType;

    public MaxBet(SportsbookHelper sportsbook, ArrayList<RaceHelper> events) {
        this.sportsbook = sportsbook;
        this.events = events;
    }

    @When("^user clicks on the max button$")
    public void userClicksOnTheMaxButton() throws Throwable {
        maxBet = sportsbook.getMaxAllowedBetStake(selections, "SINGLE","WIN", betType);
    }

    @Then("^the stake must be correctly calculated$")
    public void theStakeMustBeCorrectlyCalculated() throws Throwable {
        Double expectedMaxBet;
        if(betType.equalsIgnoreCase("SP")){
            expectedMaxBet = new Double("500.0");
        } else if (betType.equalsIgnoreCase("LP")) {
            expectedMaxBet = new Double("428.56");
        } else {
            expectedMaxBet = new Double("00.0");
        }
        Assert.assertEquals("Max allowed bet stake wrong", expectedMaxBet, maxBet);
    }

    @And("^user selects a random selection for HR$")
    public void userSelectsARandomSelectionForHR(Map<String, String> betsData) throws Throwable {
        selections = sportsbook.selectRandomSelections(betsData.get("mktType"),
                sportsbook.getEvent(Integer.valueOf(events.get(0).getEvent().getEventRef())));
        betType = betsData.get("priceType");
    }
}
