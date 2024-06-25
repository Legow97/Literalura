package com.alura.demo.main;

import com.alura.demo.Repository.AuthorRepository;
import com.alura.demo.Repository.BookRepository;
import com.alura.demo.model.*;
import com.alura.demo.service.ConsumeAPI;
import com.alura.demo.service.DataConversor;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private DataConversor conversor = new DataConversor();
    private List<DataBook> dataBooks = new ArrayList<>();
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private String nameBook= null;
    private List<Book> books;

    private List<Author> authors;

    public Principal(BookRepository bookRepository, AuthorRepository authorRepository) {

        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void showMenu() {
        var option = -1;

        while (option!=0){
            var menu = """
                    1 - Buscar Libro por titulo
                    2 - Listar todos los libros
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir 
                    """;

        System.out.println(menu);
        option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                searchBookWeb();
                break;
            case 2:
                getAllBooks();
                break;
            case 3:
                getAllActors();
                break;
            case 4:
                liveActhors();
                break;
            case 5:
                getBooksForIdiom();
                break;
            case 0:
                System.out.println("Cerrando aplicación ...");
                break;
            default:
                System.out.println("Opción no válida");
        }

        }
    }

    private void getBooksForIdiom() {
        System.out.println("Introduce el idioma para buscar los libros:");
        var idioms = """
                    es - español
                    en - inglés
                    fr - francés
                    pt - protugué

                """;
        System.out.println(idioms);
        String idiom  = scanner.nextLine();
        try{
            Idiom idioma = Idiom.fromString(idiom);

            books = bookRepository.findByLanguages(idioma);
            books.forEach(System.out::println);
        }catch (IllegalArgumentException e){
            System.out.println("Idioma no encontrado" + "\n"+"Inserte una opción válida ");
        }

    }

    private void getAllActors() {
        authors = authorRepository.findAll();
        authors.forEach(System.out::println);
    }


    private DataBook searchBookForName() {
        var json = consumeAPI.getData(URL_BASE + "?search=" + nameBook.replace(" ","+")); //"https://gutendex.com/books/?search=Romeo+and+Juliet"
        var data = conversor.getData(json, DataBooksSearch.class);
        DataBook dataBook=null;
        Optional<DataBook> book = data.results()
                .stream()
                .filter(d -> d.title().toLowerCase().contains(nameBook.toLowerCase()))
                .findFirst();
        if(book.isPresent()){
            dataBook =book.get();
        }else {
            System.out.println("LIBRO NO ENCONTRADO");
        }
        return dataBook;
    }


    private void saveAuthors(DataBook data){
        if(data != null){

            DataAuthor dataAuthor = data.authors().get(0);

            Author author = new Author(dataAuthor);

            System.out.println(author);
            authorRepository.save(author);
        }
    }

    private void searchBookWeb(){
        System.out.println("Nombre del libro que desea buscar:");
        nameBook = scanner.nextLine();
        Optional<Book> bookInDB = bookRepository.findByTitleContainsIgnoreCase(nameBook);
        if(bookInDB.isPresent()){
            System.out.println("### El libro ya se encuentra registrado ###");
            System.out.println(bookInDB.get());
        }else {

            DataBook data = searchBookForName();
            if(data != null){
            DataAuthor dataAuthor = data.authors().get(0);
            Book book = new Book(data);

            Optional<Author> aut = authorRepository.findByNameContainsIgnoreCase(dataAuthor.name());
            if(aut.isPresent()){
                book.setAuthor(aut.get());
            }else{
                Author author = new Author(dataAuthor);
                authorRepository.save(author);
                book.setAuthor(author);
            }

            System.out.println(book);
            bookRepository.save(book);
        }}

    }


    private void liveActhors(){
        System.out.println("Introduce el año de búsqueda: ");
        var year = scanner.nextInt();
        List<Author> autores = authorRepository.findAuthorsAliveInYear(year);
        autores.forEach(System.out::println);
    }

    private void getAllBooks() {
        books = bookRepository.findAll();
        books.stream()
                .sorted(Comparator.comparing(Book::getLanguages))
                .forEach(System.out::println);
    }


}
