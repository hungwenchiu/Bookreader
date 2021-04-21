package edu.cmu.sda.bookreader.entity;
import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="bookshelf")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Bookshelf implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    private String bookshelfType;
    @ManyToOne
    private User bookshelfUserID;
    @ManyToMany
    private Set<Book> books;

    public Bookshelf() {
        super();
    }

    public Bookshelf(long id, String bookshelfType, User bookshelfUserID, Set<Book> books) {
        super();
        this.id = id;
        this.bookshelfType = bookshelfType;
        this.bookshelfUserID = bookshelfUserID;
        this.books = books;
    }

    public long getBookshelfID() {
        return id;
    }

    public void setBookshelfType(String type) {
        this.bookshelfType = type;
    }

    public String getBookshelfType() {
        return bookshelfType;
    }

    public void setBookshelfUser(User userID) {
        this.bookshelfUserID = userID;
    }

    public User getBookshelfUser() {
        return bookshelfUserID;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Set<Book> getBooks() {
        return books;
    }
}
