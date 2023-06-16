package ru.clevertec.statkevich.newsservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Column(name = "username")
    private String username;

    @ManyToOne
    private News news;
}
