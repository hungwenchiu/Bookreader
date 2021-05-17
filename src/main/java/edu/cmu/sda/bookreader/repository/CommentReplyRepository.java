package edu.cmu.sda.bookreader.repository;

import edu.cmu.sda.bookreader.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentReplyRepository extends JpaRepository<Comment, Long> {
    /**
     * find all comment with event with eventId
     * @param eventid
     * @return
     */
    List<Comment> findAllByEventid(String eventid);
}