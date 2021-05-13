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
import java.util.Optional;

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
     * @param type: the type of count to be incremented: reading, read, favourite or wantToRead
     * @return updated SystemCount
     */
    @PutMapping("/systemCount/{googleBookId}/{type}")
    public SystemCount updateSystemCount(@PathVariable String googleBookId, @PathVariable String type) {
        return service.updateSystemCount(googleBookId, type, 1);
    }

    @GetMapping("/systemCount/{googleBookId}")
    public ResponseEntity<SystemCount> findSystemCountsByGoogleBookID(@PathVariable String googleBookId) {
        SystemCount systemCount = service.getSystemCountsByGoogleBookId(googleBookId);

        if (systemCount == null) {
            log.error("No SystemCounts are available with google book id " + googleBookId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(systemCount);
    }

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
            case "favourite":
                res = service.getTop10Favourite();
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
     * @param id
     * @return
     */
    @DeleteMapping("/SystemCount/{id}")
    public String deleteSystemCount(@PathVariable long id) {
        return service.deleteSystemCount(id);
    }
}
