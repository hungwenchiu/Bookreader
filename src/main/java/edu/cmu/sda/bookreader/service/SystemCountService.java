package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.SystemCount;
import edu.cmu.sda.bookreader.repository.SystemCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(value = "session")
@Component(value = "systemCountService")
public class SystemCountService {
    @Autowired
    private SystemCountRepository repository;

    public SystemCount saveSystemCount(SystemCount systemCount) {
        return repository.save(systemCount);
    }

    public SystemCount updateSystemCount(String googleBookId, String type) {
        SystemCount existing = repository.findByGoogleBookId(googleBookId);
        if (existing == null) {
            existing = new SystemCount(googleBookId);
        }
        switch (type) {
            case "reading":
                existing.setReadingCount(existing.getReadingCount() + 1);
                break;
            case "read":
                existing.setReadCount(existing.getReadCount() + 1);
                break;
            case "favourite":
                existing.setFavouriteCount(existing.getFavouriteCount() + 1);
                break;
            case "wantToRead":
                existing.setWantToReadCount(existing.getWantToReadCount() + 1);
                break;
        }
        return repository.save(existing);
    }

    public List<SystemCount> getSystemCounts() {
        return repository.findAll();
    }

    public SystemCount getSystemCountById (long Id) { return repository.findById(Id); }

    public SystemCount getSystemCountsByGoogleBookId (String googleBookId) {
        return repository.findByGoogleBookId(googleBookId);
    }

    public List<SystemCount> getTop10Reading() {
        return repository.findTop10ByOrderByReadingCountDesc();
    }

    public List<SystemCount> getTop10Read() {
        return repository.findTop10ByOrderByReadCountDesc();
    }

    public List<SystemCount> getTop10Favourite() {
        return repository.findTop10ByOrderByFavouriteCountDesc();
    }

    public List<SystemCount> getTop10WantToRead() {
        return repository.findTop10ByOrderByWantToReadCountDesc();
    }

    public String deleteSystemCount(long id) {
        repository.deleteById(id);
        return "SystemCount removed - " + id;
    }

}
