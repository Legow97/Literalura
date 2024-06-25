package com.alura.demo.Repository;

import com.alura.demo.model.Author;
import com.alura.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author,Long> {
    Optional<Author> findByNameContainsIgnoreCase(String name);
    @Query("SELECT a FROM Author a LEFT JOIN FETCH a.books b WHERE :year BETWEEN a.birthYear AND COALESCE(a.deathYear, :year)")
    List<Author> findAuthorsAliveInYear(@Param("year") Integer year);
}
