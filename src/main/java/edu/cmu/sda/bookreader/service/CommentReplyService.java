package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.Comment;
import edu.cmu.sda.bookreader.repository.CommentReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component(value = "commentReplyService")
public class CommentReplyService {

    @Autowired
    private CommentReplyRepository repository;

    public void saveReply(Comment comment) {
        repository.save(comment);
    }

    public List<Comment> getAllReplyById(String commentid) {
        return repository.findAllByEventid(commentid);
    }

}
