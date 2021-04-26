package edu.cmu.sda.bookreader.entity;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "recommendedBookshelf")
public class RecommendedBookshelf extends AbstractBookshelf {
    @ManyToMany
    @JoinTable(
            name = "recommendedBookshelf_recommenders",
            joinColumns = @JoinColumn(name = "abstractbookshelf_id"),
            inverseJoinColumns = @JoinColumn(name = "user_name")
    )
    private Set<User> recommenders;

    public RecommendedBookshelf() {
        super();
    }

    public Set<User> getRecommenders() {
        return recommenders;
    }

    public void setRecommenders(Set<User> recommenders) {
        this.recommenders = recommenders;
    }
}
