package edu.doubler.app.comment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "group_no")
    private Integer groupNo;

    @Column(name = "group_order")
    private Integer groupOrder;

    @Builder
    public Comment(String content, Integer groupNo, Integer groupOrder){
        this.content = content;
        this.groupNo = groupNo;
        this.groupOrder = groupOrder;
    }
}
