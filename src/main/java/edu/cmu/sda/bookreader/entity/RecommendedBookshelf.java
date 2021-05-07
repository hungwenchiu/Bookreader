package edu.cmu.sda.bookreader.entity;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "recommendedBookshelf")
public class RecommendedBookshelf extends AbstractBookshelf {
    @Column
    @ElementCollection(targetClass=Long.class)
    private List<Long> recommenderIDs;

    public RecommendedBookshelf() {
        super();
    }

    public List<Long> getRecommenders() {
        return recommenderIDs;
    }

    public void setRecommenders(List<Long> recommenderIDs) {
        this.recommenderIDs = recommenderIDs;
    }
}
