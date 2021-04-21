package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.AbstractBookshelf;
import edu.cmu.sda.bookreader.entity.Bookshelf;
import edu.cmu.sda.bookreader.entity.RecommendedBookshelf;
import edu.cmu.sda.bookreader.repository.BookRepository;
import edu.cmu.sda.bookreader.repository.BookshelfRepository;
import edu.cmu.sda.bookreader.repository.RecommendedBookshelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Component(value = "abstractBookService")
public class AbstractBookshelfService {
    @Autowired
    private BookshelfRepository bookshelfRepository;

    @Autowired
    private RecommendedBookshelfRepository recommendedBookshelfRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Bookshelf> getAllRegularBookshelves() {
        List<Bookshelf> bookshelves = new ArrayList<>();
        for (Object bookshelf: bookshelfRepository.findAll()) {
            bookshelves.add((Bookshelf) bookshelf);
        }

        return bookshelves;
    }

    // Get all recommended bookshelves
    public List<RecommendedBookshelf> getAllRecommendedBookshelves() {
        List<RecommendedBookshelf> recommendedBookshelves = new ArrayList<>();
        recommendedBookshelfRepository.findAll().forEach(recommendedBookshelves::add);

        return recommendedBookshelves;
    }

    // Get all bookshelves
    public List<AbstractBookshelf> getAllAbstractBookshelf() {
        List<AbstractBookshelf> all = new ArrayList<>();

        for (Object bookshelf: bookshelfRepository.findAll()) {
            all.add((AbstractBookshelf) bookshelf);
        }
        recommendedBookshelfRepository.findAll().forEach(all::add);

        return all;
    }


    public AbstractBookshelf getBookshelf(int id) {
        Optional<Bookshelf> bookshelf = bookshelfRepository.findById(new Long(id));

        if (bookshelf.isPresent()) {
            return bookshelf.get();
        }

        return null;

    }

    /*
    It is working as our factory method
     */
    public void addBookshelf(AbstractBookshelf abstractBookshelf) {
        System.out.println(" Insde add bookshelf");
        if (abstractBookshelf instanceof Bookshelf) {
            System.out.println("reached abstract bookshelf");
            bookshelfRepository.save((Bookshelf) abstractBookshelf);;
        } else {
            recommendedBookshelfRepository.save((RecommendedBookshelf) abstractBookshelf);
        }
    }

    public void recommendBookshelf(Bookshelf bookshelf) {
        bookshelfRepository.save(bookshelf);
    }


}
