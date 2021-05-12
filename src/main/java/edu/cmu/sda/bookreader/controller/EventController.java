package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.entity.Event;
import edu.cmu.sda.bookreader.service.EventService;
import edu.cmu.sda.bookreader.service.SocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Scope(value = "session")
@Component(value = "eventController")
@Slf4j
public class EventController {

    @Qualifier("eventService")
    @Autowired
    private EventService service;

    @PostMapping("/event") // add timeline event
    public Event addEvent(Event event) { return service.saveEvent(event); }
}


