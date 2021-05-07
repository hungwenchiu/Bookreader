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
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @Column(unique=true)
    private String name;
    @NotNull
    private String password;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookshelfUser")
//    private Set<Bookshelf> bookshelves;


    @ManyToMany
    @JoinTable(
        name = "friendship",
        joinColumns = @JoinColumn(name = "user_id")
    )
    Set<User> friends = new HashSet<>();
}
