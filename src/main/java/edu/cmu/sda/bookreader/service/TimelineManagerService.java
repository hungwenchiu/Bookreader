package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.Event;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(value = "session")
@Component(value = "timelineManagerService")
public class TimelineManagerService {

    private TimelineStrategy timelineStrategy;

    public void setTimelineStrategy(TimelineStrategy timelineStrategy) {
        this.timelineStrategy = timelineStrategy;
    }

    /**
     * Generate public or personal timeline event
     * @param userid
     * @return
     */
    public List<Event> getTimelineEvents(String userid) {
        return this.timelineStrategy.generateTimelineEvents(userid);
    }
}
