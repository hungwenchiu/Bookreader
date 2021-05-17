package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.entity.Comment;
import edu.cmu.sda.bookreader.entity.Event;
import edu.cmu.sda.bookreader.service.CommentReplyService;
import edu.cmu.sda.bookreader.service.PersonalTimelineStrategy;
//import edu.cmu.sda.bookreader.service.Socketio;
import edu.cmu.sda.bookreader.service.PublicTimelineStrategy;
import edu.cmu.sda.bookreader.service.TimelineManagerService;
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

    @Qualifier("timelineManagerService")
    @Autowired
    TimelineManagerService timelineManagerService; // create timeline manager

    @Qualifier("personalTimelineStrategy")
    @Autowired
    PersonalTimelineStrategy timeline;

    @Qualifier("publicTimelineStrategy")
    @Autowired
    PublicTimelineStrategy publicTimeline;

    @Qualifier("commentReplyService")
    @Autowired
    CommentReplyService commentReplyService;

    /**
     * get personal timeline event by userid
     * @param userid
     * @return
     */
    @GetMapping("/personalTimeline")
    public List<Event> getPersonalTimeline(@RequestParam(value = "userid") String userid) {
        timelineManagerService.setTimelineStrategy(timeline); // set personal timeline behavior
        return timelineManagerService.getTimelineEvents(userid); // generate personal timeline
    }

    /**
     * get public timeline by user id
     * @param userids
     * @return
     */
    @GetMapping("/publicTimeline")
    public List<Event> getPublicTimeline(@RequestParam(value = "userids") String userids) {
        timelineManagerService.setTimelineStrategy(publicTimeline); // set personal timeline behavior
        return timelineManagerService.getTimelineEvents(userids); // generate personal timeline
    }

    /**
     * create a comment
     * @param comment
     */
    @PostMapping("/reply")
    public void userReplyComment(@RequestBody Comment comment) {
        commentReplyService.saveReply(comment);
    }

    /**
     * create a replyy
     * @param eventid
     * @return
     */
    @GetMapping("/reply")
    public List<Comment> userReplyComment(@RequestParam(value = "eventid") String eventid) {
        return commentReplyService.getAllReplyById(eventid);
    }
}