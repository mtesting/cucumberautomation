package util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;

/**
 * Utils methods/functions class
 */
public class Utils {

    private static final Logger log = Logger.getLogger(Utils.class);

    private Utils(){

    }

    /**
     * Returns the file path for the given file name
     *
     * @param fileName folder/fileName
     * @return file absolute path
     * @throws URISyntaxException wrong file name
     */
    public static String getSourceAsAbsolutePath(String fileName) throws URISyntaxException {
        URL idUrl = Utils.class.getClassLoader().getResource(fileName);
        if (idUrl != null) {
            log.info(new File(idUrl.toURI()).getAbsolutePath());
            return new File(idUrl.toURI()).getAbsolutePath();
        } else {
            log.warn(new FileNotFoundException());
            return "";
        }
    }

    /**
     * Method to wait X seconds
     *
     * @param seconds waiting time
     */
    public static void waitSeconds(int seconds) {
        int millis = seconds * 1000;
        try {
            Thread.sleep(millis);
            log.info("Waited " + millis + " ms.");
        } catch (InterruptedException ignored) {
        }
    }

    public static String randomAlphanumeric(){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 25) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    public static String randomAlphabet(int lenght){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < lenght) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    public static String randomNumbers(int lenght){
        String SALTCHARS = "0123456789";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < lenght) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

}
