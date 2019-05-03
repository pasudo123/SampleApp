package edu.doubler.app.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepositoryImpl extends QuerydslRepositorySupport implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Comment.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Comment> findByContent(String content){

        final QComment comment = QComment.comment;

        return queryFactory
                .selectFrom(comment)
                .where(comment.content.eq(content))
                .fetch();
    }
}
