package edu.cmu.sda.bookreader.entity;
import javax.persistence.*;
import java.util.Set;

@Entity
public class RecommendedBookshelf extends Bookshelf {
    @ManyToMany
    private Set<User> recommendersID;

    public RecommendedBookshelf(long id, String bookshelfType, User bookshelfUserID, Set<Book> books, Set<User> recommendersID) {
        super(id, bookshelfType, bookshelfUserID, books);
        this.recommendersID = recommendersID;
    }

    public RecommendedBookshelf() {
        super();
    }

    public void setRecommendersID(Set<User> recommendersID) {
        this.recommendersID = recommendersID;
    }

    public Set<User> getRecommendersID() {
        return recommendersID;
    }
}
