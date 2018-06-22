package att.events;

import static att.events.EventManager.SportType.*;

public class EventManager {

    private static EventManager instance;
    private EventHelperTemplate eventHelper;

    private EventManager() {
        decodeEventSport();
    }

    public static EventManager getEventManager() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    private void decodeEventSport() throws RuntimeException {
        switch (valueOf(System.getProperty("sport"))) {
            case FOOTBALL:
                eventHelper = new FootballHelper();
                break;
            case TENNIS:
                eventHelper = new TennisHelper();
                break;
            case BADMINTON:
                eventHelper = new BadmintonHelper();
                break;
            case HR:
                eventHelper = new RaceHelper();
                break;
            default:
                throw new RuntimeException("No valid sport at feature tag name value=" + System.getProperty("sport"));
        }
    }

    public EventHelperTemplate getEventHelper() {
        return eventHelper;
    }

    public enum SportType {

        FOOTBALL("FOOTBALL"),
        TENNIS("TENNIS"),
        BADMINTON("BADMINTON"),
        HR("HR");

        private final String value;

        SportType(String v) {
            value = v;
        }

        public String value() {
            return value;
        }

        public static SportType fromValue(String v) {
            for (SportType c: values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(v);
        }

    }

}