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
    private String userid;
    private String name;
    private String bookName;
    private String action;
    @Column(columnDefinition="varchar(2000)")
    private String content;
    @Column(columnDefinition="varchar(500)")
    private String googlebookid;
    private String rating;
    @CreationTimestamp
    private Date time;
}