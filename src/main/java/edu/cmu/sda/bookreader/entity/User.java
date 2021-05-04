package edu.cmu.sda.bookreader.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class User {
    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @Column(unique=true)
    private String name;
    @NotNull
    private String password;
}
