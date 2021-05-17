package edu.cmu.sda.bookreader.entity;

import javax.persistence.*;

@Entity
@Table(name = "bookshelf")
public class Bookshelf extends AbstractBookshelf {
    public Bookshelf() {
        super();
    }
}
