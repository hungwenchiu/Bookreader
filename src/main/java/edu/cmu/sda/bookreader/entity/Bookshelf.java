package edu.cmu.sda.bookreader.entity;
import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "bookshelf")
public class Bookshelf extends AbstractBookshelf {
    public Bookshelf() {
        super();
    }
}
