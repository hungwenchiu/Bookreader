package edu.cmu.sda.bookreader.controller;


import edu.cmu.sda.bookreader.BookreaderApplication;
import edu.cmu.sda.bookreader.entity.SystemCount;
import edu.cmu.sda.bookreader.repository.SystemCountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BookreaderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:clear_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class SystemCountControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SystemCountRepository repository;

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

    @Test
    public void gettingTop10ReadReturnCorrectSize() {
        repository.save(new SystemCount("10"));
        repository.save(new SystemCount("9"));
        repository.save(new SystemCount("8"));
        List<SystemCount> top10Read = this.restTemplate.getForObject(getRootUrl() + "/api/systemCount/top10/read", List.class);
        assertEquals(3, top10Read.size());
    }

    @Test
    public void gettingTop10ReadingReturnCorrectSize() {
        repository.save(new SystemCount("10"));
        repository.save(new SystemCount("9"));
        repository.save(new SystemCount("8"));
        List<SystemCount> top10Read = this.restTemplate.getForObject(getRootUrl() + "/api/systemCount/top10/reading", List.class);
        assertEquals(3, top10Read.size());
    }

    @Test
    public void gettingTop10FavoriteReturnCorrectSize() {
        repository.save(new SystemCount("10"));
        repository.save(new SystemCount("9"));
        repository.save(new SystemCount("8"));
        List<SystemCount> top10Read = this.restTemplate.getForObject(getRootUrl() + "/api/systemCount/top10/favorite", List.class);
        assertEquals(3, top10Read.size());
    }

    @Test
    public void gettingTop10WantToReadReturnCorrectSize() {
        repository.save(new SystemCount("10"));
        repository.save(new SystemCount("9"));
        repository.save(new SystemCount("8"));
        List<SystemCount> top10Read = this.restTemplate.getForObject(getRootUrl() + "/api/systemCount/top10/wantToRead", List.class);
        assertEquals(3, top10Read.size());
    }

    @Test
    public void getSystemCountWithNonExistingGoogleBookIdReturnNotFound() {
        SystemCount mySysCount = this.restTemplate.getForObject(getRootUrl() + "/api/systemCount/abc", SystemCount.class);
        assertEquals(null, mySysCount);
    }

    @Test
    public void gettingTop10ForInvalidTypeReturnNullObject() {
        List<SystemCount> top10Read = this.restTemplate.getForObject(getRootUrl() + "/api/systemCount/top10/invalid", List.class);
        assertEquals(null, top10Read);
    }

    @Test
    public void deleteSystemCountRemovesItFromDB() {
        repository.save(new SystemCount("bcd"));
        SystemCount mySysCount = this.restTemplate.getForObject(getRootUrl() + "/api/systemCount/bcd", SystemCount.class);
        this.restTemplate.delete("/api/systemCount/" + mySysCount.getId());
        SystemCount nonExist= this.restTemplate.getForObject(getRootUrl() + "/api/systemCount/bcd", SystemCount.class);
        assertEquals(null, nonExist);
    }
}