package ru.clevertec.statkevich.newsservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.statkevich.newsservice.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
