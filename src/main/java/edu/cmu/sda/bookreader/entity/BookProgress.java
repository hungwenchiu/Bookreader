package edu.cmu.sda.bookreader.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_progress")
public class BookProgress {
    @Id
    @GeneratedValue
    private long id;
    @NonNull
    private String googleBookID;
    @NonNull
    private long userID;
    @NonNull
    private long pagesFinished;

    public long getId() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getGoogleBookID() {
        return googleBookID;
    }

    public void setGoogleBookID(String googleBookId) {
        this.googleBookID = googleBookId;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getPagesFinished() {
        return pagesFinished;
    }

    public void setPagesFinished(long pagesFinished) {
        this.pagesFinished = pagesFinished;
    }
}
