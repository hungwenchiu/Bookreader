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
@Table
public class Book {
    @Id
    @GeneratedValue
    private long id;
    @NonNull
    private String googleBookId;
    @NonNull
    private String title;
    private String author;
    @NonNull
    private int totalPage;
    private String kind;
    private String thumbnail;

    public String getGoogleBookId() {
        return googleBookId;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getKind() {
        return kind;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
