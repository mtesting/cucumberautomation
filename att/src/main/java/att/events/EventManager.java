package att.events;

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
        switch (System.getProperty("sport")) {
            case "@Football":
                eventHelper = new FootballHelper();
                break;
            case "@Tennis":
                eventHelper = new TennisHelper();
                break;
            case "@Badminton":
                eventHelper = new BadmintonHelper();
                break;
            case "@HR":
                eventHelper = new RaceHelper();
                break;
            default:
                throw new RuntimeException("No valid sport at feature tag name value=" + System.getProperty("sport"));
        }
    }

    public EventHelperTemplate getEventHelper() {
        return eventHelper;
    }

}