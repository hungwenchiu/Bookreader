package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.Event;
import edu.cmu.sda.bookreader.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Component(value = "abstractTimelineService")
public class AbstractTimelineService {

//    @Autowired
//    private PublicTimelineRepository publicTimelineRepository; TODO
    @Autowired
    private EventRepository repository;

    public List<Event> getAllTimelineByName(String name){
        List<Event> events = repository.findByName(name);
        return events;
    }


}
