package edu.cmu.sda.bookreader.repository;

import edu.cmu.sda.bookreader.entity.Comment;
import edu.cmu.sda.bookreader.entity.Relationship;
import edu.cmu.sda.bookreader.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentReplyRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByEventid(String eventid);
}