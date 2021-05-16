package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.BookreaderApplication;
import edu.cmu.sda.bookreader.entity.Comment;
import edu.cmu.sda.bookreader.entity.Event;
import edu.cmu.sda.bookreader.repository.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookreaderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:clear_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TimelineControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;


    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    // a simple sanity check test that will fail if the application context cannot start
    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetPersonalTimeline() {

        // set test data to DB
        Event event = new Event();
        event.setUserid("1");
        event.setName("Bob");
        event.setBookName("Java in a Time of Revolution");
        event.setAction("Review");
        event.setContent("Nice Book.");
        event.setGooglebookid("87totx4p3ZcC");
        event.setRating("4");
        event.setProgress(null);
        restTemplate.postForEntity(getRootUrl() + "/api/event", event, Event.class);

        ResponseEntity<List> responseEntity = this.restTemplate.getForEntity(getRootUrl() + "/api/personalTimeline?userid=1", List.class);
        LinkedHashMap myEvent = (LinkedHashMap)responseEntity.getBody().get(0);
        assertEquals("Bob", myEvent.get("name"));
    }

    @Test
    public void testGetPublicTimeline() {

        // set test data to DB
        Event event = new Event();
        event.setUserid("1");
        event.setName("Bob");
        event.setBookName("Java in a Time of Revolution");
        event.setAction("Review");
        event.setContent("Nice Book.");
        event.setGooglebookid("87totx4p3ZcC");
        event.setRating("4");
        event.setProgress(null);
        restTemplate.postForEntity(getRootUrl() + "/api/event", event, Event.class);

        ResponseEntity<List> responseEntity = this.restTemplate.getForEntity(getRootUrl() + "/api/publicTimeline?userids=1", List.class);
        LinkedHashMap myEvent = (LinkedHashMap)responseEntity.getBody().get(0);
        assertEquals("Bob", myEvent.get("name"));
    }

    @Test
    public void testUserReplyComment() {
        Event event = new Event();
        event.setUserid("1");
        event.setName("Bob");
        event.setBookName("Java in a Time of Revolution");
        event.setAction("Review");
        event.setContent("Nice Book.");
        event.setGooglebookid("87totx4p3ZcC");
        event.setRating("4");
        event.setProgress(null);
        restTemplate.postForEntity(getRootUrl() + "/api/event", event, Event.class);

        ResponseEntity<List> responseEvent = this.restTemplate.getForEntity(getRootUrl() + "/api/publicTimeline?userids=1", List.class);
        LinkedHashMap myEvent = (LinkedHashMap)responseEvent.getBody().get(0);

        Comment comment = new Comment();
        comment.setEventid(myEvent.get("id").toString());
        comment.setBookname((String) myEvent.get("bookname"));
        comment.setReply("Test");
        comment.setReceiver("Bob");
        comment.setSender("Bob");
        restTemplate.postForEntity(getRootUrl() + "/api/reply", comment, Comment.class);


        ResponseEntity<List> responseReply = this.restTemplate.getForEntity(getRootUrl() + "/api/reply?eventid=" + myEvent.get("id").toString(), List.class);
        LinkedHashMap myReply = (LinkedHashMap)responseReply.getBody().get(0);
        assertEquals("Bob", myReply.get("sender"));
    }

}
