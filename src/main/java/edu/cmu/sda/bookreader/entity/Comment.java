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
public class Comment {
    @Id
    @GeneratedValue
    @Column(unique=true)
    private long id;
    private String eventid;
    private String receiver;
    private String bookname;
    private String reply;
    private String sender;
    @CreationTimestamp
    private Date time;
}
