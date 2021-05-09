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
    private int favouriteCount;
    @Column(columnDefinition = "integer default 0")
    private int readCount;
    @Column(columnDefinition = "integer default 0")
    private int wantToReadCount;

    public SystemCount (String gID) {
        this.googleBookId = gID;
    }

    public int getReadingCount() { return this.readingCount; }

    public int getFavouriteCount() { return this.favouriteCount; }

    public int getReadCount() { return this.readCount; }

    public int getWantToReadCount() { return this.wantToReadCount; }

    public void setWantToRead(int count) { this.wantToReadCount = count; }

    public void setRead(int count) { this.readCount = count; }

    public void setReading(int count) { this.readingCount = count; }

    public void setFavourite(int count) { this.favouriteCount = count; }
}
