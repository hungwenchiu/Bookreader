package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.entity.Event;
import edu.cmu.sda.bookreader.service.PersonalTimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@Scope(value = "session")
@Component(value = "timelineController")
public class TimelineController {

        @Qualifier("personalTimelineService")
        @Autowired
        PersonalTimelineService service;
        @GetMapping("/personalTimeline")
        public List<Event> getTimelineEvents(@RequestParam(value="name") String name){
            return service.getAllTimelineByName(name);
        }


}
