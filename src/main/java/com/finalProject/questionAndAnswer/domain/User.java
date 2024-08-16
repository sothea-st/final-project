package com.finalProject.questionAndAnswer.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name",length = 50 , nullable = false)
    private String userName;

    @Column(name = "email" ,length = 50 , nullable = false , unique = true)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "profile")
    private String profile;

    @Column(name = "uuid",nullable = false ,unique = true)
    private String uuid;

    @Column(name = "is_deleted",nullable = false)
    private Boolean isDeleted;

    @Column(name = "is_verify")
    private Boolean isVerify;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private List<Vote> votes;


    @ManyToMany
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id" , referencedColumnName = "id") ,
        inverseJoinColumns = @JoinColumn(name="role_id" ,referencedColumnName = "id")
    )
    private List<Role> roles;

    
    @ManyToMany
    @JoinTable(
        name = "users_comments",
        joinColumns = @JoinColumn(name="user_id" , referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name="comment_id" , referencedColumnName = "id")
    )
    private List<Comment> comments;


    @ManyToMany
    @JoinTable(
        name = "users_bookmarks" ,
        joinColumns = @JoinColumn(name="user_id" , referencedColumnName = "id") ,
        inverseJoinColumns = @JoinColumn(name="bookmark_id", referencedColumnName = "id")
    )
    private List<Bookmark> bookmarks;
 
}
