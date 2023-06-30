package ru.clevertec.statkevich.newsservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Column(name = "username")
    private String username;


    @JoinColumn(name = "news_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private News news;
}
