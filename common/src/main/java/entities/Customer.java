package entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import generated.ats.betsync.betcatcher.dto.PlaceBetsResponse;
import util.NumberUtil;

public class Customer {

    public List<Bet> bets;
    public PlaceBetsResponse placeBetsResponse;
    private String username;
    private BigDecimal balance;

    public Customer() {
        bets = new ArrayList<>();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = NumberUtil.parseToBigDecimal(balance);
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Decrease the balance value
     *
     * @param balance subtrahend
     */
    public void subtract(String balance) {
        this.balance = this.balance.subtract(NumberUtil.parseToBigDecimal(balance));
    }

    /**
     * Increase the balance value
     *
     * @param balance addend
     */
    public void deposit(String balance) {
        this.balance = this.balance.add(NumberUtil.parseToBigDecimal(balance));
    }

    public void addNewBet(String betId) {
        bets.add(new Bet(Long.valueOf(betId)));
    }

}
