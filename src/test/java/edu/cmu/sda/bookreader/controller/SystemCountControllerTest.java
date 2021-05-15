package edu.cmu.sda.bookreader.controller;


import edu.cmu.sda.bookreader.BookreaderApplication;
import edu.cmu.sda.bookreader.entity.SystemCount;
import edu.cmu.sda.bookreader.repository.SystemCountRepository;
import edu.cmu.sda.bookreader.service.SystemCountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookreaderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SystemCountControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SystemCountRepository repository;

//    @Qualifier("systemCountService")
//    @Autowired
//    SystemCountService systemCountService;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }


    // a simple sanity check test that will fail if the application context cannot start
    @Test
    public void contextLoads() {
    }

//    @Test
//    public void testFindAllBooks() {
//        List<Book> allBooks = this.restTemplate.getForObject(getRootUrl() + "/api/books", List.class);
//        assertTrue(allBooks.isEmpty());
//    }

    @Test
    public void testCountsBeforeIncrement() {
        repository.save(new SystemCount("bcd"));
        SystemCount mySysCount = this.restTemplate.getForObject(getRootUrl() + "/api/systemCount/bcd", SystemCount.class);
        assertEquals(0, mySysCount.getWantToReadCount());
        assertEquals(0, mySysCount.getReadingCount());
        assertEquals(0, mySysCount.getReadCount());
        assertEquals(0, mySysCount.getFavoriteCount());
    }

    @Test
    public void testIncrementReadingCount() {
        this.restTemplate.put(getRootUrl() + "/api/systemCount/abc/Reading", SystemCount.class);
        SystemCount mySysCount = this.restTemplate.getForObject(getRootUrl() + "/api/systemCount/abc", SystemCount.class);
        assertEquals(1, mySysCount.getReadingCount());
    }

    @Test
    public void testIncrementReadCount() {
        this.restTemplate.put(getRootUrl() + "/api/systemCount/abc/Read", SystemCount.class);
        SystemCount mySysCount = this.restTemplate.getForObject(getRootUrl() + "/api/systemCount/abc", SystemCount.class);
        assertEquals(1, mySysCount.getReadCount());
    }

    @Test
    public void testIncrementFavoriteCount() {
        this.restTemplate.put(getRootUrl() + "/api/systemCount/abc/Favorite", SystemCount.class);
        SystemCount mySysCount = this.restTemplate.getForObject(getRootUrl() + "/api/systemCount/abc", SystemCount.class);
        assertEquals(1, mySysCount.getFavoriteCount());
    }

    @Test
    public void testIncrementWantToReadCount() {
        this.restTemplate.put(getRootUrl() + "/api/systemCount/abc/WantToRead", SystemCount.class);
        SystemCount mySysCount = this.restTemplate.getForObject(getRootUrl() + "/api/systemCount/abc", SystemCount.class);
        assertEquals(1, mySysCount.getWantToReadCount());
    }

//    @Test
//    public void gettingTop10ReadReturnExpectedCounts() {
//        systemCountservice.updateSystemCount("10", "Read", 10);
//        systemCountservice.updateSystemCount("9", "Read", 9);
//        systemCountservice.updateSystemCount("8", "Read", 8);
//        List<SystemCount> top10Read = this.restTemplate.getForObject(getRootUrl() + "/api/systemCount/top10/Read", List.class);
//        assertEquals(10, top10Read.get(0).getReadCount());
//    }
}