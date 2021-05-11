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
public class PublicTimelineStrategy implements TimelineStrategy{

    @Autowired
    private EventRepository repository;

    @Override
    public List<Event> generateTimelineEvents(String userids, int idx) {
        List<String> useridlist = new ArrayList<>();

        for(String uid: userids.split(",")) {
            useridlist.add(uid);
        }

        List<Event> events = repository.findByMultipleUsers(useridlist);
        List<Event> res = new ArrayList<>();
        for(int i = idx; i < idx + 3 && i < events.size(); i++) {
            res.add(events.get(i));
        }
        return res;
    }


}
