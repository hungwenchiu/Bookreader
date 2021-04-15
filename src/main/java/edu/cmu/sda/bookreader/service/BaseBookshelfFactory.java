package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.Book;
import edu.cmu.sda.bookreader.entity.Bookshelf;
import edu.cmu.sda.bookreader.entity.User;
import edu.cmu.sda.bookreader.repository.BookshelfRepository;

import java.util.Set;

public abstract class BaseBookshelfFactory {
    public abstract BookshelfRepository getBookshelfRepository(String type);
}
