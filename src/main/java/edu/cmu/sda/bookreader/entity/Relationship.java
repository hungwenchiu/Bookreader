package edu.cmu.sda.bookreader.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// source: https://www.codedodle.com/social-network-friends-database/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints={
    @UniqueConstraint(columnNames = {"user_one", "user_two"})
})
public class Relationship {
    public enum Status {
        PENDING, ACCEPTED
    }
    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @OneToOne
    @JoinColumn(name="user_one")
    private User userOne;
    @NotNull
    @OneToOne
    @JoinColumn(name="user_two")
    private User userTwo;
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Status status;
    @NotNull
    @OneToOne
    private User actionUser;

    public User getUserOne() {
        return userOne;
    }

    public void setUserOne(User userOne) {
        this.userOne = userOne;
    }

    public User getUserTwo() {
        return userTwo;
    }

    public void setUserTwo(User userTwo) {
        this.userTwo = userTwo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getActionUser() {
        return actionUser;
    }

    public void setActionUser(User actionUser) {
        this.actionUser = actionUser;
    }

    public long getId() {
        return id;
    }
}
