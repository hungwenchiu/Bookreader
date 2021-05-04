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
public class Review {
    @Id
    @GeneratedValue
    private long id;
    @NonNull
    private String googleBookId;
    @NonNull
    private long userId;
    @NonNull
    private String content;
    @NonNull
    private int rating;
}
