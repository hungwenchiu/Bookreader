package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.Event;
import edu.cmu.sda.bookreader.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Service
@Component(value = "publicTimelineStrategy")
public class PublicTimelineStrategy implements TimelineStrategy {

    @Autowired
    private EventRepository repository;

    @Override
    public List<Event> generateTimelineEvents(String userids) {
        List<String> useridlist = new ArrayList<>();

        for (String uid : userids.split(",")) {
            useridlist.add(uid);
        }

        List<Event> events = repository.findByMultipleUsers(useridlist);

        /*
           remove the duplicate friendship when using publictimeline,
           EX: if A and B are friends, we will have 2 events, 1 for A, 1 for B
         */
        for (int i = 0; i < events.size(); i++) {
            String t = events.get(i).getAction();
            if (i > 0 && (events.get(i).getAction().equals("Friendship"))) {
                if ((events.get(i - 1).getAction().equals("Friendship")) && (events.get(i - 1).getContent().equals(events.get(i).getContent()))) {
                    events.remove(i);
                    i--;
                }
            }
        }
        return events;
    }
}
