package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.Event;
import edu.cmu.sda.bookreader.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Service
@Component(value = "personalTimelineStrategy")
public class PersonalTimelineStrategy implements TimelineStrategy{

    @Autowired
    private EventRepository repository;

    @Override
    public List<Event> generateTimelineEvents(String name, int idx) {
        List<Event> events = repository.findByName(name);
        List<Event> res = new ArrayList<>();
        for(int i = idx; i < idx + 3 && i < events.size(); i++)
        {
            res.add(events.get(i));
        }

        return res;
    }
}