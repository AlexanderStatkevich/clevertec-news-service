package ru.clevertec.statkevich.newsservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;


@Entity
@Table(name = "news")
public class News extends BaseEntity {

    @Column(name = "title")
    private String title;

    @OneToMany
    private List<Comment> comments;
}
