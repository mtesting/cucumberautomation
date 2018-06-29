package feeds;

import java.util.ArrayList;
import java.util.List;

import ats.betting.trading.att.ws.scenario.dto.Event;
import io.gsi.ats.isd.domain.data.Command;
import io.gsi.ats.isd.domain.data.CommandKey;
import io.gsi.ats.isd.domain.data.Schedule;
import io.gsi.ats.isd.domain.data.Sport;
import io.gsi.ats.isd.domain.data.StatusType;
import io.gsi.ats.isd.domain.message.CommandMessage;
import util.Utils;

public class IsdHelper {

    private Schedule schedule;

    public IsdHelper() {
        schedule = new Schedule();
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Schedule createIsdSchedule(Event event) {
        schedule.setMatchId((Utils.randomNumbers(6)));
        schedule.setStatusId(1);
        schedule.setStatusType(StatusType.notstarted);
        schedule.setStatusName("notstarted");
        schedule.setSis(0);
        //schedule.setUtcStartDate(OffsetDateTime.now().toString()); //TODO ATT should populate it
        schedule.setName(
                event.getParticipants().getParticipant().get(1).getName()
                        + " vs "
                        + event.getParticipants().getParticipant().get(0).getName()
        );
        schedule.setSport(new Sport(1, "soccer"));

        schedule.setTournament("England - Championship");
        schedule.setTournamentId("840450");
        schedule.setTeam1Id("568875");
        schedule.setTeam2Id("568876");

        return schedule;
    }

    private CommandMessage createIncident(CommandKey commandKey, int statusId, int team, int confirm) {
        CommandMessage commandMessage = new CommandMessage();
        Command data = new Command();
        commandMessage.setType(CommandMessage.MessageType.command);
        commandMessage.setMatchId(schedule.getMatchId());
        commandMessage.setSport(new Sport(1, "soccer"));
        //commandMessage.setTimestamp(140000000);
        //data.setId(5);
        data.setStatusId(statusId);
        data.setKey(commandKey);
        data.setTeam(team);
        data.setConfirmed(confirm);
        /* data.setTimestamp(140000000); */
        commandMessage.setData(data);

        commandMessage.getData().getKey();

        return commandMessage;
    }

    public List<CommandMessage> teamGameIncidents() {
        List<CommandMessage> cm = new ArrayList<>();
        final int NotStarted = 1;
        final int FirstHalf = 2;
        final int SecondHalf = 3;
        final int HalfTime = 10;
        final int Abandoned = 17;

        cm.add(createIncident(CommandKey.status, NotStarted,0,1));
        cm.add(createIncident(CommandKey.attendance, NotStarted,0,1));
        cm.add(createIncident(CommandKey.status,FirstHalf,0,1));
        cm.add(createIncident(CommandKey.kickoff, FirstHalf, 0,1));
        cm.add(createIncident(CommandKey.attack, FirstHalf, 1,1));
        cm.add(createIncident(CommandKey.safe, FirstHalf, 1,1));
        cm.add(createIncident(CommandKey.attack, FirstHalf, 1,1));
        cm.add(createIncident(CommandKey.goal, FirstHalf, 1,1));
        cm.add(createIncident(CommandKey.status,HalfTime,0,1));
        cm.add(createIncident(CommandKey.attack, SecondHalf, 2,1));
        cm.add(createIncident(CommandKey.danger, SecondHalf, 2,1));
        cm.add(createIncident(CommandKey.goal_kick, SecondHalf,2,1));
        cm.add(createIncident(CommandKey.penalty, SecondHalf,1,1));
        cm.add(createIncident(CommandKey.attack, SecondHalf, 1,1));
        cm.add(createIncident(CommandKey.goal, SecondHalf, 1,1));
        cm.add(createIncident(CommandKey.status, Abandoned,0,1));
        return cm;
    }

}

