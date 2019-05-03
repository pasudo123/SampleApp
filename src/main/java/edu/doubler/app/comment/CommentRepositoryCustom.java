package edu.doubler.app.comment;

import java.util.List;

public interface CommentRepositoryCustom {

    List<Comment> findByContent(String content);

}
