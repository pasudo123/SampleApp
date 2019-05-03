package edu.doubler.app.comment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.setMaxElementsForPrinting;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommentTests {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentRepositoryImpl commentRepositoryImpl;

    @Autowired
    CommentRepositorySupport commentRepositorySupport;

    List<Comment> comments = new ArrayList<Comment>();

    @Before
    public void setup(){

    }

    @Test
    public void Querydsl_기본_테스트(){

        String content = "안녕";
        Integer groupNo = 0;
        Integer groupOrder = 0;

        Comment comment = Comment.builder()
                .content(content)
                .groupNo(groupNo)
                .groupOrder(groupOrder)
                .build();

        List<Comment> resultComment = commentRepositoryImpl.findByContent(content);

        for(Comment element : resultComment){
            assertThat(element.getContent()).isEqualTo(content);
        }
    }

    @Test
    public void 댓글_3개_조회(){

        String content = "안녕";
        Integer groupNo = 0;
        Integer groupOrder = 0;

    }

}
