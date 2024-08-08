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
@NoArgsConstructor
@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "uuid" , nullable = false , unique = true)
    private String uuid;

    @Column(name = "is_deleted" ,nullable = false)
    private Boolean isDeleted;

    @Column(name = "created_at" ,nullable =  false , updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    private Question question;

    @ManyToOne
    private Answer answer;

}
