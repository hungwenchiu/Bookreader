package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.Event;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface of timeline strategy
 */
public interface TimelineStrategy {
    List<Event> generateTimelineEvents(String userid);
}
