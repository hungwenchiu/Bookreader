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
    @Column(columnDefinition="varchar(500)")
    private String thumbnail;

    @Column(columnDefinition="varchar(2000)")
    private String description;

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

    public String getDescription() {
        return description;
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

    public void setDescription(String description) {
        this.description = description;
    }

}
