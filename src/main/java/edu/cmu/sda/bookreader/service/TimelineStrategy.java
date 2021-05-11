package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.Event;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TimelineStrategy {
    List<Event> generateTimelineEvents(String userid, int idx);
}
