package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.entity.SystemCount;
import edu.cmu.sda.bookreader.service.SystemCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@Scope(value = "session")
@Component(value = "systemCountController")
@Slf4j
public class SystemCountController {

    @Qualifier("systemCountService")
    @Autowired
    private SystemCountService service;

    /**
     * update SystemCount's counting by type
     * @param googleBookId: googleBookId of the book count to be updated
     * @param type: the type of count to be incremented: reading, read, favorite or wantToRead
     * @return updated SystemCount
     */
    @PutMapping("/systemCount/{googleBookId}/{type}")
    public SystemCount updateSystemCount(@PathVariable String googleBookId, @PathVariable String type) {
        return service.updateSystemCount(googleBookId, type, 1);
    }

    /**
     * Get the systemCount for a specific book
     * @param googleBookId: the google book Id of the book to get the systemCount
     * @return the SystemCount object of the book
     */
    @GetMapping("/systemCount/{googleBookId}")
    public ResponseEntity<SystemCount> findSystemCountsByGoogleBookID(@PathVariable String googleBookId) {
        SystemCount systemCount = service.getSystemCountsByGoogleBookId(googleBookId);

        if (systemCount == null) {
            log.error("No SystemCounts are available with google book id " + googleBookId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(systemCount);
    }

    /**
     * Get the top 10 systemCount of a specific type
     * @param type: the type of count to get: reading, read, favorite or wantToRead
     * @return A list of SystemCount objects
     */
    @GetMapping("/systemCount/top10/{type}")
    private ResponseEntity<List<SystemCount>> findTopSystemCountByType(@PathVariable String type) {
        List<SystemCount> res;
        switch (type) {
            case "reading":
                res = service.getTop10Reading();
                break;
            case "read":
                res = service.getTop10Read();
                break;
            case "favorite":
                res = service.getTop10Favorite();
                break;
            case "wantToRead":
                res = service.getTop10WantToRead();
                break;
            default:
                log.error("No counting for type" + type);
                return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(res);
    }

    /**
     * delete SystemCount by id
     * @param id: id of the SystemCount to be deleted
     * @return A string indicating SystemCount is deleted
     */
    @DeleteMapping("/systemCount/{id}")
    public String deleteSystemCount(@PathVariable long id) {
        return service.deleteSystemCount(id);
    }
}
