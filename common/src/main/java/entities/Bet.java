package entities;

import java.math.BigDecimal;

import generated.ats.betting.dto.BetType;
import util.NumberUtil;

public class Bet {

    private BigDecimal potentialWinnings;
    private BigDecimal totalStake;
    private Long betId;
    private Status status;
    private BetType betType;

    public Bet() {

    }

    public Bet(Long betId) {
        this.betId = betId;
    }

    public String getStatus() {
        return status.value();
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
    }

    public String getBetType() {
        return betType.value();
    }

    public void setBetType(String betType) {
        this.betType = BetType.valueOf(betType);
    }

    public Long getBetId() {
        return betId;
    }

    public void setBetId(String betId) {
        this.betId = Long.valueOf(betId);
    }

    public void setBetId(Long betId) {
        this.betId = betId;
    }

    public BigDecimal getTotalStake() {
        return totalStake;
    }

    public void setTotalStake(String totalStake) {
        this.totalStake = NumberUtil.parseToBigDecimal(totalStake);
    }

    public void setTotalStake(BigDecimal totalStake) {
        this.totalStake = totalStake;
    }

    public BigDecimal getPotentialWinnings() {
        return potentialWinnings;
    }

    public void setPotentialWinnings(String potentialWinnings) {
        this.potentialWinnings = NumberUtil.parseToBigDecimal(potentialWinnings);
    }

    public void setPotentialWinnings(BigDecimal potentialWinnings) {
        this.potentialWinnings = potentialWinnings;
    }

    private enum Status {
        REJECTED("rejected"),
        SETTLED("settled"),
        PENDING("pending"),
        VOID("void"),
        ACCEPTED("accepted");
        private final String value;

        Status(String v) {
            value = v;
        }

        public static Status fromValue(String v) {
            for (Status c : Status.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(v);
        }

        public String value() {
            return value;
        }

    }

}
