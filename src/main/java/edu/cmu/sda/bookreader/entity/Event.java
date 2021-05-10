package edu.cmu.sda.bookreader.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Event {
    @Id
    @GeneratedValue
    @Column(unique=true)
    private long id;
    private String name;
    private String bookName;
    private String action;
    private String review;
    private String rate;
    private String progress;
    private String img;
    @CreationTimestamp
    private Date time;
}