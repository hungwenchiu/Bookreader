package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.Review;
import edu.cmu.sda.bookreader.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(value = "session")
@Component(value = "reviewService")
public class ReviewService {
    @Autowired
    private ReviewRepository repository;

    public Review saveReview(Review review) {
        return repository.save(review);
    }

    public Review getReviewById (long Id) { return repository.findById(Id); }

    public List<Review> getReviewsByGoogleBookId (String googleBookID) {
        return repository.findByGoogleBookId(googleBookID);
    }

    public List<Review> getReviewsByUserId(long userId) {
        return repository.findByUserId(userId);
    }

    public String deleteReview(long id) {
        repository.deleteById(id);
        return "review removed - " + id;
    }

}
