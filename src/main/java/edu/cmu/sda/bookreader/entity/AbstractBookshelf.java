package edu.cmu.sda.bookreader.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;
import java.util.HashSet;
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
    public Long id;

    public String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "abstractbookshelf_user",
            joinColumns = @JoinColumn(name = "user_name"),
            inverseJoinColumns = @JoinColumn(name = "abstractbookshelf_id")
    )
    private User bookshelfUser;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "abstractbookshelf_books",
            joinColumns = @JoinColumn(name = "abstractbookshelf_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books = new HashSet<>();

    private String type;

    public void setType(String type) { this.type = type; }

    public String getType() { return type; }

    public AbstractBookshelf() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getBookshelfUser() { return bookshelfUser; }

    public void setBookshelfUser(User bookshelfUser) { this.bookshelfUser = bookshelfUser; }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "AbstractBookshelf{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user='" + bookshelfUser + '\'' +
                ", books=" + books +
                '}';
    }
}
