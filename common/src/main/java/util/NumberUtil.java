package util;

import org.junit.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.annotation.Nullable;

import static java.math.RoundingMode.HALF_EVEN;
import static java.math.RoundingMode.HALF_UP;

public class NumberUtil {
    public static final BigDecimal MINUS_ONE = new BigDecimal("-1");
    public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
    public static final BigDecimal MINUS_ONE_HUNDRED = new BigDecimal("-100");
    public static final BigDecimal TWO = new BigDecimal("2");
    public static final int DEFAULT_SCALE = 20; // should be sufficiently large to handle most scenarios
    public static final double EPS = 1e-12;
    private static final int M = 0x5bd1e995;
    private static final int R = 24;
    private static final int H = -1;

    /**
     * Hashes a byte array efficiently.
     *
     * @param payload a byte array to hash
     * @return a hash code for the byte array
     */
    public static final int hash(byte[] payload) {
        int h = H;
        int len = payload.length;
        int offset = 0;
        while (len >= 4) {
            int k = payload[offset];
            k |= payload[offset + 1] << 8;
            k |= payload[offset + 2] << 16;
            k |= payload[offset + 3] << 24;

            k *= M;
            k ^= k >> R;
            k *= M;
            h *= M;
            h ^= k;

            len -= 4;
            offset += 4;
        }

        switch (len) {
            case 3:
                h ^= payload[offset + 2] << 16;
            case 2:
                h ^= payload[offset + 1] << 8;
            case 1:
                h ^= payload[offset];
                h *= M;
        }

        h ^= h >> 13;
        h *= M;
        h ^= h >> 15;

        return h;
    }

    /**
     * An incremental version of the hash function, that spreads a pre-calculated hash code, such as one derived from
     * {@link Object#hashCode()}.
     *
     * @param hashcode an object's hashcode
     * @return a spread and hashed version of the hashcode
     */
    public static final int hash(int hashcode) {
        byte[] b = new byte[4];
        b[0] = (byte) hashcode;
        b[1] = (byte) (hashcode >> 8);
        b[2] = (byte) (hashcode >> 16);
        b[3] = (byte) (hashcode >> 24);
        return hash(b);
    }

    public static String toString(BigDecimal[] bdArray) {
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        for (int i = 0; i < bdArray.length; i++) {
            if (i != 0) {
                buf.append(",");
            }
            buf.append(bdArray[i]);
        }
        buf.append("]");
        return buf.toString();
    }

    public static String toString(long[] bdArray) {
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        for (int i = 0; i < bdArray.length; i++) {
            if (i != 0) {
                buf.append(",");
            }
            buf.append(bdArray[i]);
        }
        buf.append("]");
        return buf.toString();
    }

    public static BigDecimal big(double d) {
        return new BigDecimal(String.valueOf(d));
    }

    public static BigDecimal big(double d, int scale) {
        return new BigDecimal(String.valueOf(d)).setScale(scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal mult(BigDecimal a, int b) {
        return a.multiply(new BigDecimal(b));
    }

    public static BigDecimal add(BigDecimal a, int amount) {
        return a.add(new BigDecimal(amount));
    }

    public static BigDecimal mult(BigDecimal a, long b) {
        return a.multiply(new BigDecimal(b));
    }

    public static BigDecimal min(BigDecimal bd1, BigDecimal bd2) {
        return bd1.compareTo(bd2) < 0 ? bd1 : bd2;
    }

    public static BigDecimal max(BigDecimal bd1, BigDecimal bd2) {
        return bd1.compareTo(bd2) > 0 ? bd1 : bd2;
    }

    public static boolean isNegative(BigDecimal val) {
        return val.compareTo(BigDecimal.ZERO) < 0;
    }

    public static boolean isPositive(BigDecimal val) {
        return val.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isWithinTargetPercentageChange(BigDecimal oldVal, BigDecimal newVal, BigDecimal allowedPercentage) {
        return isLessThanOrEquals(percentageDiff(oldVal, newVal), allowedPercentage);
    }

    public static boolean isWithinTargetPercentageChange(double oldVal, double newVal, double allowedPercentage) {
        return isLessThanOrEquals(percentageDiff(oldVal, newVal), allowedPercentage);
    }

    public static boolean isGreaterThan(BigDecimal val1, BigDecimal val2) {
        return val1.compareTo(val2) > 0;
    }

    public static double scale(double val, int scale) {
        return scaleToBd(val, scale).doubleValue();
    }

    public static BigDecimal scaleToBd(double val, int scale) {
        return new BigDecimal(String.valueOf(val)).setScale(scale, RoundingMode.HALF_UP);
    }

    public static boolean isGreaterThanOrEquals(BigDecimal val1, BigDecimal val2) {
        return val1.compareTo(val2) >= 0;
    }

    public static boolean isLessThan(BigDecimal val1, BigDecimal val2) {
        return val1.compareTo(val2) < 0;
    }

    public static boolean isLessThanOrEquals(BigDecimal val1, BigDecimal val2) {
        return val1.compareTo(val2) <= 0;
    }

    public static boolean isLessThanOrEquals(double val1, double val2) {
        return val1 <= val2;
    }

    public static BigDecimal diff(BigDecimal val1, BigDecimal val2) {
        return val1.subtract(val2).abs();
    }

    public static boolean isZero(BigDecimal val) {
        return val.compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean isOne(BigDecimal val) {
        return val.compareTo(BigDecimal.ONE) == 0;
    }

    public static boolean equals(BigDecimal bd1, BigDecimal bd2) {
        if (bd1 == null && bd2 != null) {
            return false;
        } else if (bd1 != null && bd2 == null) {
            return false;
        } else if (bd1 == null && bd2 == null) {
            return true;
        }
        return bd1.compareTo(bd2) == 0;
    }

    public static boolean equals(double expected, double actual, double delta) {
        if (Double.compare(expected, actual) == 0) {
            return true;
        }
        return Math.abs(expected - actual) <= delta;
    }

    public static boolean equals(BigDecimal bd1, BigDecimal bd2, int scale) {
        if (bd1 == null && bd2 != null) {
            return false;
        } else if (bd1 != null && bd2 == null) {
            return false;
        } else if (bd1 == null && bd2 == null) {
            return true;
        }
        return bd1.setScale(scale, HALF_EVEN).compareTo(bd2.setScale(scale, HALF_EVEN)) == 0;
    }

    public static boolean equals(BigDecimal bd1, String bd2) {
        return equals(bd1, new BigDecimal(bd2));
    }

    public static BigDecimal div(BigDecimal val1, BigDecimal val2, int scale) {
        return val1.divide(val2, scale, HALF_EVEN);
    }

    public static BigDecimal div(BigDecimal val1, BigDecimal val2) {
        return val1.divide(val2, DEFAULT_SCALE, HALF_EVEN);
    }

    public static BigDecimal div(BigDecimal val1, int val2, int scale) {
        return div(val1, new BigDecimal(val2), scale);
    }

    public static BigDecimal div(BigDecimal val1, long val2, int scale) {
        return div(val1, new BigDecimal(val2), scale);
    }

    public static BigDecimal div(long val1, long val2, int scale) {
        return div(new BigDecimal(val1), new BigDecimal(val2), scale);
    }

    public static BigDecimal div(BigDecimal val1, int val2) {
        return div(val1, new BigDecimal(val2));
    }

    public static BigDecimal scale(BigDecimal bd, int scale) {
        return bd.setScale(scale, HALF_EVEN);
    }

    public static BigDecimal scaleHalfUp(BigDecimal bd, int scale) {
        return bd.setScale(scale, HALF_UP);
    }

    public static String toString(BigDecimal bd, int scale) {
        return toString(bd, scale, true);
    }

    public static String toString(String number, int scale) {
        return toString(new BigDecimal(number), scale, true);
    }

    public static double scale(Double d, int scale) {
        return new BigDecimal(d.toString()).setScale(scale, HALF_UP).doubleValue();
    }

    public static String toString(Double d, int scale) {
        return stripTrailingZeros(new BigDecimal(d.toString()).setScale(scale, HALF_UP).toPlainString());
    }

    public static String toString(BigDecimal bd, int scale, boolean stripTrailingZeros) {
        String s = bd.setScale(scale, HALF_UP).toPlainString();
        if (stripTrailingZeros) {
            s = stripTrailingZeros(s);
        }
        return s;
    }

    public static BigDecimal scaleOddsForString(BigDecimal bd) {
        int scale = bd.scale() >= 3 ? 3 : 2;
        return scale(bd, scale);
    }

    public static String toDecOddsString(BigDecimal bd) {
        int scale = bd.scale() >= 3 ? 3 : 2;
        return toString(bd, scale, true);
    }

    public static String toStringPZ2(BigDecimal bd) {
        return bd != null ? bd.setScale(2, HALF_UP).toPlainString() : null;
    }

    public static String toStringPZ(BigDecimal bd, int scale) {
        return bd.setScale(scale, HALF_UP).toPlainString();
    }

    public static String toString(double d, int scale, boolean stripTrailingZeros) {
        return toString(new BigDecimal(String.valueOf(d)), scale, stripTrailingZeros);
    }

    public static String toString(BigDecimal bd, boolean stripTrailingZeros) {
        String s = bd.toPlainString();
        if (stripTrailingZeros) {
            s = stripTrailingZeros(s);
        }
        return s;
    }

    public static String removeLeadingZeros(String s) {
        int idx = -1;

        while (++idx < s.length()) {
            if (s.charAt(idx) == '.') {
                if (idx > 0) {
                    idx -= 1;
                }
                break;
            }
            if (s.charAt(idx) != '0') {
                break;
            }
        }
        if (idx == 0) {
            return s;
        }
        if (idx == s.length()) {
            return "0";
        }
        return s.substring(idx);
    }

    public static String stripTrailingZeros(String s) {
        // doing this here rather than using BigDecimal.stripTrailingZeros()
        // as you can end up with scientific notation when you use than.
        int len = s.length();
        int idx = len;

        int decimalPlaceIdx = -1;
        int nonZeroCharIdx = -1;

        while (--idx > 0) {
            char c = s.charAt(idx);
            if (c == '0') {
                continue;
            } else if (c == '.') {
                decimalPlaceIdx = idx;
                break;
            } else {
                if (nonZeroCharIdx == -1) {
                    nonZeroCharIdx = idx;
                }
                continue;
            }
        }
        if (decimalPlaceIdx != -1) {
            if (nonZeroCharIdx == -1) {
                s = s.substring(0, decimalPlaceIdx);
            } else if (nonZeroCharIdx != len - 1) {
                s = s.substring(0, nonZeroCharIdx + 1);
            }
        }
        return s;
    }

    public static BigDecimal percentageMult(BigDecimal amount, BigDecimal percentage) {
        return div(amount.multiply(percentage), ONE_HUNDRED);
    }

    public static BigDecimal percentageDiv(BigDecimal amount, BigDecimal percentage) {
        return div(amount, percentage).multiply(ONE_HUNDRED);
    }

    public static BigDecimal percentageVal(BigDecimal amount, BigDecimal percentage) {
        return div(percentage, ONE_HUNDRED).multiply(amount);
    }

    public static boolean isBetween(BigDecimal sample, BigDecimal lowerBound, BigDecimal upperBound) {
        return isGreaterThanOrEquals(sample, lowerBound) && isLessThanOrEquals(sample, upperBound);
    }

    public static boolean isDiffMore(BigDecimal first, BigDecimal second, BigDecimal limit) {
        return isGreaterThanOrEquals(diff(first, second), limit);
    }

    public static boolean isDiffLess(BigDecimal first, BigDecimal second, BigDecimal limit) {
        return isLessThanOrEquals(diff(first, second), limit);
    }

    public static BigDecimal percentageDiff(BigDecimal newVal, BigDecimal oldVal) {
        BigDecimal delta = diff(newVal, oldVal);
        return div(delta, oldVal).multiply(ONE_HUNDRED);
    }

    public static BigDecimal signedPercentageDiff(BigDecimal newVal, BigDecimal oldVal) {
        BigDecimal delta = newVal.subtract(oldVal);
        return div(delta, oldVal).multiply(ONE_HUNDRED);
    }

    public static double signedPercentageDiff(double newVal, double oldVal) {
        double delta = newVal - oldVal;
        return (delta / oldVal) * 100;
    }

    public static double percentageDiff(double newVal, double oldVal) {
        double delta = Math.abs(newVal - oldVal);
        return (delta / oldVal) * 100;
    }

    public static BigDecimal percentageDiff(String newVal, String oldVal) {
        return percentageDiff(new BigDecimal(newVal), new BigDecimal(oldVal));
    }

    public static boolean doublesEqual(@Nullable Double a, @Nullable Double b) {
        if (a != null && b == null) {
            return false;
        }
        return a == b || (a != null && a.compareTo(b) == 0);
    }

    public static int compare(int val1, int val2) {
        if (val1 < val2) {
            return -1;
        } else if (val2 < val1) {
            return 1;
        } else {
            return 0;
        }
    }

    public static double interpolate(double x, double yA, double xA, double yB, double xB) {
        return (x * ((yA - yB) / (xA - xB))) + (((xA * yB) - (xB * yA)) / (xA - xB));
    }

    public static double randomDouble(double min, double max) {
        return (Math.random() * (max - min + 1)) + min;
    }

    public static String getDoubleAsString(Double d) {
        return (Math.floor(d) == d) ? ((Integer) d.intValue()).toString() : d.toString();
    }

    public static double round(double val, int decPlaces) {
        double ddp = Math.pow(10, decPlaces);
        return Math.round(val * ddp) / ddp;
    }

    public static float round(float val, int decPlaces) {
        float ddp = (float) Math.pow(10, decPlaces);
        return Math.round(val * ddp) / ddp;
    }

    public static String getDoubleAsFraction(Double d) {
        String s = getDoubleAsString(d);
        String[] parts = s.split("\\.");
        if (parts.length == 1) {
            return s;
        }
        int decimalPart = Integer.parseInt(parts[1]);
        return parts[0] + " " + getFractionString(decimalPart, (int) Math.pow(10, parts[1].length()));
    }

    public static int countMaxConsecutive(List<Integer> list) {
        int maxRun = 0;
        int currRun = 0;
        for (int i = 0; i < list.size(); i++) {
            int frameNum = list.get(i);
            if (i == 0 || frameNum == list.get(i - 1) + 1) {
                currRun++;
                if (currRun > maxRun) {
                    maxRun = currRun;
                }
            } else {
                currRun = 1;
            }
        }
        return maxRun;
    }

    public static int getLastDigit(long l) {
        return (int) (l % 10);
    }

    public static Double getFractionAsDouble(String s) {
        String[] parts = s.split("/");
        if (parts.length == 1) {
            return Double.parseDouble(s);
        }
        return new BigDecimal(parts[0]).divide(new BigDecimal(parts[1]), BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private static String getFractionString(int numerator, int denominator) {
        int gcd = gcd(numerator, denominator);
        return numerator / gcd + "/" + denominator / gcd;
    }

    private static int gcd(int x, int y) {
        if (x > y) {
            return gcd(y, x);
        } else if (y % x == 0) {
            return x;
        } else {
            return gcd(x, y - x);
        }
    }

    /**
     * Parse an string into BigDecimal with an specific format
     *
     * @param numberToParse money string value
     * @return money BigDecimal value
     */
    public static BigDecimal parseToBigDecimal(String numberToParse) {
        return new BigDecimal(numberToParse.replace(",", "")).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * Returns true if the two BigDecimals are mathematically equal
     *
     * @param bd1 A BigDecimal value
     * @param bd2 A BigDecimal value
     * @return If the two values are equal
     */
    public static boolean areEqual(BigDecimal bd1, BigDecimal bd2) {
        return (bd1 == null && bd2 == null) ||
                (bd1 != null && bd2 != null && bd1.compareTo(bd2) == 0);
    }

    /**
     * Asserts the two given BigDecimals using JUnit
     * The values will be appended to the given message similar to Assert.assertEquals
     *
     * @param message Error message
     * @param expected The expected BigDecimal value
     * @param actual The actual BigDecimal value
     */
    public static void assertAreEqual(String message, BigDecimal expected, BigDecimal actual) {
        Assert.assertTrue(message + ": \nExpected: " + expected.toString() + "\nActual: " + actual.toString(), areEqual(expected, actual));
    }
}