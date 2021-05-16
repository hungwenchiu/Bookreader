package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.BookreaderApplication;
import edu.cmu.sda.bookreader.entity.Book;
import edu.cmu.sda.bookreader.entity.Event;
import edu.cmu.sda.bookreader.repository.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookreaderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:clear_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class EventControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private EventRepository repository;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    // a simple sanity check test that will fail if the application context cannot start
    @Test
    public void contextLoads() {
    }

    @Test
    public void testAddEvent() {
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
        List<Event> responseEvents = repository.findByUserid("1");
        assertEquals("Nice Book.", responseEvents.get(0).getContent());
    }
}
