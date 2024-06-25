package com.alura.demo.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Integer birthYear;
    private Integer deathYear;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch =  FetchType.EAGER)
    private List<Book> books;

    public Author() {
    }

    public Author(DataAuthor dataAuthor) {
        this.name = dataAuthor.name();
        this.birthYear =dataAuthor.birthYear();
        this.deathYear = dataAuthor.deathYear();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(Book book) {
        this.books.add(book);
    }

    @Override
    public String toString() {
        return
                "Autor: " + getName() + "\n"+
                "Fecha de nacimiento: " + getBirthYear() +"\n"+
                "Fecha de fallecimiento: " + getDeathYear() + "\n"+
                        "Libros: " + getBooks().stream().map(l -> l.getTitle()).collect(Collectors.toList())+ "\n"
                ;
    }
}
