package entities;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;


public class TestUserDataService {

    /**
     * Data set generated for MyBet sportsbook
     *
     * @return user data
     */
    public TestUserData generateForMtLicense() {
        Long timestamp = System.currentTimeMillis();
        TestUserData userData = new TestUserData();
        userData.setLicenseCode(TestUserData.LicenseCode.MT);
        userData.setUserName("player-" + timestamp);
        userData.seteMailAddress(userData.getUserName() + "@anybet.de");
        userData.setPassword("Test1234");
        userData.setTitle(TestUserData.Title.Mr);
        userData.setFirstName("firstname-" + timestamp);
        userData.setLastName("lastname-" + timestamp);
        userData.setHouseNumber(String.valueOf(timestamp % 100 + 1));
        userData.setStreet("street-" + timestamp);
        userData.setCity("city-" + timestamp);
        userData.setZipCode(String.format("%05d", (timestamp % 100000)));
        userData.setCountry("Germany");
        userData.setBirthDate(getDefaultBirthDate());
        userData.setTelephoneNumber(String.valueOf(timestamp % 1000000000 + 1));
        return userData;
    }

    /**
     * Data set generated for Argyll sportsbooks
     *
     * @return user data
     */
    public TestUserData generateForBem() {
        Long timestamp = System.currentTimeMillis();
        TestUserData userData = new TestUserData();
        userData.setLicenseCode(TestUserData.LicenseCode.MT);
        userData.setUserName("player-" + timestamp);
        userData.seteMailAddress(userData.getUserName() + "@anybet.de");
        userData.setPassword("Test1234");
        userData.setTitle(TestUserData.Title.Mr);
        userData.setFirstName("firstname" + timestamp);
        userData.setLastName("lastname" + timestamp);
        userData.setHouseNumber(String.valueOf(timestamp % 100 + 1));
        userData.setStreet("street-" + timestamp);
        userData.setCity("city-" + timestamp);
        userData.setZipCode(String.format("%05d", (timestamp % 100000)));
        userData.setCountry("Germany");
        userData.setBirthDate(getDefaultBirthDate());
        userData.setTelephoneNumber(String.valueOf(timestamp % 1000000000 + 1));
        return userData;
    }

    private Date getDefaultBirthDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(1969, Calendar.NOVEMBER, 1);
        return cal.getTime();
    }

    /**
     * Generates a random string without numbers
     *
     * @return alphabetic string
     */
    private String getRandomString() {
        char[] symbols;
        char[] buf = new char[6];
        StringBuilder tmp = new StringBuilder();
        Random random = new Random();
        for (char ch = 'a'; ch <= 'z'; ++ch) {
            tmp.append(ch);
        }
        symbols = tmp.toString().toCharArray();
        for (int idx = 0; idx < buf.length; ++idx) {
            buf[idx] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);
    }

}
