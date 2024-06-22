package com.alura.demo.main;

import com.alura.demo.Repository.BookRepository;
import com.alura.demo.model.Book;
import com.alura.demo.model.DataBook;
import com.alura.demo.model.DataBooksSearch;
import com.alura.demo.service.ConsumeAPI;
import com.alura.demo.service.DataConversor;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private DataConversor conversor = new DataConversor();
    private List<DataBook> dataBooks = new ArrayList<>();
    private BookRepository repository;

    public Principal(BookRepository repository) {
        this.repository = repository;
    }

    public void showMenu() {
        var option = -1;

        while (option!=0){
            var menu = """
                    1 - Buscar Libro por titulo
                    2 - Listar todos los libros
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
            case 0:
                System.out.println("Cerrando aplicación ...");
                break;
            default:
                System.out.println("Opción no válida");
        }

        }
    }


    private DataBook searchBookForName() {
        System.out.println("Nombre del libro que desea buscar:");
        var nameBook = scanner.nextLine();
        var json = consumeAPI.getData(URL_BASE + "?search=" + nameBook.replace(" ","+")); //"https://gutendex.com/books/?search=Romeo+and+Juliet"
        var data = conversor.getData(json, DataBooksSearch.class);
        DataBook dataBook=null;
        Optional<DataBook> book = data.results()
                .stream()
                .filter(d -> d.title().toLowerCase().contains(nameBook.toLowerCase()))
                .findFirst();
        if(book.isPresent()){
            dataBook =book.get();
//            System.out.println(
//                            "Titulo: " + dataBook.title() +"\n"+
//                            "Autor: " + dataBook.authors() +"\n"+
//                            "Idiomas: " + dataBook.languages().get(0) +"\n"+
//                            "Número de descargas: " + dataBook.numberOfDownloads()
//                    );
        }else {
            System.out.println("Libro no encontrado");
        }
        return dataBook;
    }

    private void searchBookWeb(){
        DataBook data = searchBookForName();
        //dataBooks.add(data);
        Book book = new Book(data);
        repository.save(book);
        System.out.println(data);
    }

    private void getAllBooks() {

        List<Book> books = new ArrayList<>();
        books = dataBooks.stream()
                .map(d -> new Book(d))
                .collect(Collectors.toList());
        books.stream()
                .sorted(Comparator.comparing(Book::getLanguages))
                .forEach(System.out::println);


    }
}
