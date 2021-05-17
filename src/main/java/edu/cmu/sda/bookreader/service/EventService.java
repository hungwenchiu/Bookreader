package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.Event;
import edu.cmu.sda.bookreader.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "eventService")
public class EventService {

    @Autowired
    private EventRepository repository;

    /**
     * add new Event object and call this function
     * @param event
     * @return
     */
    public Event saveEvent(Event event) {
        return repository.save(event);
    }

}
