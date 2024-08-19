package com.finalProject.questionAndAnswer.domain;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user; // user_id

    @ManyToOne
    private Question question;

    @OneToMany(mappedBy = "answer")
    private List<Vote> votes;

    @OneToMany(mappedBy = "answer")
    private List<Comment> comments;

    @OneToMany(mappedBy = "answer")
    private List<Image> images;

    @Column(name = "answer" , nullable = false)
    private String answer;

    @Column(name = "snipped_code" , columnDefinition = "TEXT")
    private String snippedCode;

    @Column(name = "uuid",unique = true , nullable = false)
    private String uuid;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_at",nullable = false , updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

}
