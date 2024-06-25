package com.alura.demo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity()
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    @Column(name = "languages", columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private Idiom languages;
    private Integer numberOfDownloads;

    @ManyToOne()
    @JoinColumn(nullable = false)
    private Author author;

    public Book() {

    }

    public Book(DataBook dataBook) {
        this.title = dataBook.title();
        this.languages = Idiom.fromString(dataBook.languages().get(0));
        this.numberOfDownloads = dataBook.numberOfDownloads();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Idiom getLanguages() {
        return languages;
    }

    public void setLanguages(Idiom languages) {
        this.languages = languages;
    }

    public Integer getNumberOfDownloads() {
        return numberOfDownloads;
    }

    public void setNumberOfDownloads(Integer numberOfDownloads) {
        this.numberOfDownloads = numberOfDownloads;
    }

    @Override
    public String toString() {
        return  "---LIBRO---"+"\n" +
                "Título: " + getTitle() + "\n" +
                "Autor: " + getAuthor().getName() + "\n" +
                "Idioma: " + getLanguages() +"\n" +
                "Número de descargas: " + getNumberOfDownloads()+"\n"+
                "-------------" + "\n";
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
