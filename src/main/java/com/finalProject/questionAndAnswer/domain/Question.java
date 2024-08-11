package com.finalProject.questionAndAnswer.domain;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Bookmark bookmark;

    @ManyToOne
    private User user; // user_id
 
    @OneToMany(mappedBy = "question")
    private List<Comment> comments;

    @Column(name = "title" , nullable = false)
    private String title;

    @Column(name = "content" , columnDefinition = "TEXT" , nullable = false)
    private String content;

    @Column(name = "snipped_code" , columnDefinition = "TEXT")
    private String snippedCode;

    @Column(name = "uuid",unique = true , nullable = false)
    private String uuid;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_at",nullable = false , updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "question")
    private List<Image> images;

    @OneToMany(mappedBy = "question")
    private List<Vote> votes;

}
