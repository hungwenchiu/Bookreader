package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.SystemCount;
import edu.cmu.sda.bookreader.repository.SystemCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Scope(value = "session")
@Component(value = "systemCountService")
public class SystemCountService {
    @Autowired
    private SystemCountRepository repository;

    public SystemCount updateSystemCount(String googleBookId, String type, int amount) {
        SystemCount existing = repository.findByGoogleBookId(googleBookId);
        if (existing == null) {
            existing = new SystemCount(googleBookId);
        }
        switch (type) {
            case "Reading":
                existing.setReadingCount(existing.getReadingCount() + amount);
                break;
            case "Read":
                existing.setReadCount(existing.getReadCount() + amount);
                break;
            case "Favorite":
                existing.setFavoriteCount(existing.getFavoriteCount() + amount);
                break;
            case "WantToRead":
                existing.setWantToReadCount(existing.getWantToReadCount() + amount);
                break;
        }
        return repository.save(existing);
    }

    public SystemCount getSystemCountsByGoogleBookId(String googleBookId) {
        return repository.findByGoogleBookId(googleBookId);
    }

    public List<SystemCount> getTop10Reading() {
        return repository.findTop10ByOrderByReadingCountDesc();
    }

    public List<SystemCount> getTop10Read() {
        return repository.findTop10ByOrderByReadCountDesc();
    }

    public List<SystemCount> getTop10Favorite() {
        return repository.findTop10ByOrderByFavoriteCountDesc();
    }

    public List<SystemCount> getTop10WantToRead() {
        return repository.findTop10ByOrderByWantToReadCountDesc();
    }

    public String deleteSystemCount(long id) {
        repository.deleteById(id);
        return "SystemCount removed - " + id;
    }

}
