package edu.cmu.sda.bookreader.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class SystemCount {
    @Id
    @GeneratedValue
    private long id;
    @NonNull
    private String googleBookId;
    @Column(columnDefinition = "integer default 0")
    private int readingCount;
    @Column(columnDefinition = "integer default 0")
    private int favoriteCount;
    @Column(columnDefinition = "integer default 0")
    private int readCount;
    @Column(columnDefinition = "integer default 0")
    private int wantToReadCount;

    public SystemCount (String gID) {
        this.googleBookId = gID;
    }

    public int getReadingCount() { return this.readingCount; }

    public int getFavoriteCount() { return this.favoriteCount; }

    public int getReadCount() { return this.readCount; }

    public String getGoogleBookId() { return googleBookId; }

    public long getId() { return id; }

    public void setReading(int count) { this.readingCount = count; }

    public void setFavorite(int count) { this.favoriteCount = count; }

    public void setReadingCount(int readingCount) {
        this.readingCount = readingCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public void setWantToReadCount(int wantToReadCount) {
        this.wantToReadCount = wantToReadCount;
    }
}
