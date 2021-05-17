package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.Event;
import edu.cmu.sda.bookreader.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

import java.util.List;

@Service
@Component(value = "personalTimelineStrategy")
public class PersonalTimelineStrategy implements TimelineStrategy {

    @Autowired
    private EventRepository repository;

    @Override
    public List<Event> generateTimelineEvents(String userid) {
        return repository.findByUserid(userid);
    }
}