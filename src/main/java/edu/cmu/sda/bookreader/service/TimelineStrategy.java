package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.Event;

import java.util.List;

/**
 * Interface of timeline strategy
 */
public interface TimelineStrategy {
    List<Event> generateTimelineEvents(String userid);
}
