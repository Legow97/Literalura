package com.alura.demo.Repository;

import com.alura.demo.model.Book;
import com.alura.demo.model.Idiom;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByTitleContainsIgnoreCase(String title);
    List<Book> findByLanguages(Idiom language);
}
