package edu.cmu.sda.bookreader.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "recommendedBookshelf")
public class RecommendedBookshelf extends AbstractBookshelf {
    @Column
    @ElementCollection(targetClass = Long.class)
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
