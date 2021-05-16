package edu.cmu.sda.bookreader.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,    //Use PROPERTY if you want to include type as an attribute
        property = "type",
        defaultImpl = Bookshelf.class,
        visible = true)                              //useful for the factory method
@JsonSubTypes({
        @JsonSubTypes.Type(value = Bookshelf.class, name = "regular"),
        @JsonSubTypes.Type(value = RecommendedBookshelf.class, name = "recommended")
})

@Entity
@Table(name = "abstractbookshelf")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AbstractBookshelf {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    private Long id;
    private String name;
    @NonNull
    private long bookshelfUserID;
    @Column
    @ElementCollection(targetClass=String.class)
    private List<String> books = new ArrayList<>();
    private String type;

    public AbstractBookshelf() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public long getBookshelfUserID() { return bookshelfUserID; }

    public void setBookshelfUser(long bookshelfUserID) { this.bookshelfUserID = bookshelfUserID; }

    public List<String> getBooks() {
        return books;
    }

    public void setBooks(List<String> books) {
        this.books = books;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AbstractBookshelf{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user='" + bookshelfUserID + '\'' +
                ", books=" + books +
                '}';
    }
}
