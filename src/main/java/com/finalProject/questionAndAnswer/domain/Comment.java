package com.finalProject.questionAndAnswer.domain;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@Setter
@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "comment" , nullable = false , length = 1000)
    private String comment;

    @ManyToOne
    private Question question; // question_id

    @ManyToOne
    private Answer answer; // answer_id

    @Column(name = "uuid" , nullable=false , unique = true)
    private String uuid;

    @Column(name="is_deleted" , nullable = false)
    private Boolean isDeleted;

    @CreationTimestamp
    @Column(name = "created_at",nullable = false , updatable = false)
    private LocalDateTime createdAt;

}
