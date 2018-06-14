package entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ats.core.util.json.JsonUtil;
import generated.ats.betsync.betcatcher.dto.Bet;
import generated.ats.betsync.betcatcher.dto.BetPart;
import generated.ats.betsync.betcatcher.dto.BetType;
import generated.ats.betsync.betcatcher.dto.Bets;
import generated.ats.betsync.betcatcher.dto.Odds;
import generated.ats.betsync.betcatcher.dto.PlaceBetsRequest;
import generated.ats.betsync.betcatcher.dto.PriceType;
import generated.ats.betsync.betcatcher.dto.Stake;
import generated.ats.betsync.betcatcher.dto.WinType;
import generated.ats.sportsbook.punter.dto.Login;
import generated.ats.sportsbook.punter.dto.Selection;
import util.NumberUtil;

public class BetPlacementHelper {

    private List<Selection> selections;
    private String type;
    private String winType;
    private String priceType;

    public BetPlacementHelper(List<Selection> selections, String type, String winType, String priceType){
        this.selections = selections;
        this.type = type;
        this.winType = winType;
        this.priceType = priceType;
    }

    public String buildPlaceBetsRequest(Login login) {
        PlaceBetsRequest placeBetsRequest1 = new PlaceBetsRequest();
        Bets bets = new Bets();
        List<Bet> betsList = new ArrayList<>();
        Bet bet = new Bet();
        Stake stake = new Stake();
        Bet.Parts parts = new Bet.Parts();


        double stakeValue = 1.00; //TODO temp hardcoded variable

        placeBetsRequest1.setAccountId(login.getAccountId());
        placeBetsRequest1.setChannelId(6);
        placeBetsRequest1.setReqId(0L);
        placeBetsRequest1.setAcceptPriceChange(false);

        stake.setAmount(BigDecimal.valueOf(stakeValue));

        try {
            stake.setCurrency(login.getAccountBalance().getCurrency());
        } catch (NullPointerException ignored){
            stake.setCurrency("GBP");
        }

        bet.setStake(stake);

        switch (winType.toUpperCase()) {
            case "WIN":
                bet.setWinType(WinType.WIN);
                break;
            case "EACH_WAY":
                bet.setWinType(WinType.EACH_WAY);
                break;
            case "PLACE":
                bet.setWinType(WinType.PLACE);
                break;
            default:
                bet.setWinType(WinType.WIN);
                break;
        }

        switch (type.toUpperCase()) {
            case "SINGLE":
                bet.setType(BetType.SINGLE);
                break;
            case "DOUBLE":
                bet.setType(BetType.MULTIPLE);
                break;
            case "PATENT":
                bet.setType(BetType.PATENT);
                break;
            case "TREBLE":
                bet.setType(BetType.MULTIPLE);
                break;
            case "TRIXIE":
                bet.setType(BetType.TRIXIE);
                break;
            default:
                break;
        }

        parts.setBetPart(buildBetParts(selections, priceType));

        bet.setParts(parts);
        betsList.add(bet);
        bets.setBet(betsList);
        placeBetsRequest1.setBets(bets);
        return JsonUtil.marshalJson(placeBetsRequest1);
    }

    /**
     * Sets a betPart per selection
     * @param selections betting selections
     * @param priceType selection price type
     * @return betPart ArrayList
     */
    private List<BetPart> buildBetParts(List<Selection> selections, String priceType){
        List<BetPart> betParts = new ArrayList<>();
        BetPart betPart;
        int partNo = 1;
        for (Selection selection : selections) {
            betPart = new BetPart();

            betPart.setPartNo(partNo);
            betPart.setSelectionId(selection.getId());

            Odds odds = new Odds();
            if ("SP".equalsIgnoreCase(priceType)) {
                odds.setPriceType(PriceType.STARTING);
            } else {
                odds.setFractional(selection.getOdds().getFrac());
                odds.setDecimal(NumberUtil.parseToBigDecimal(selection.getOdds().getDec()));
            }
            betPart.setOdds(odds);

            partNo++;
            betParts.add(betPart);
        }
        return betParts;
    }


}
