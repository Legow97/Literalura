package com.alura.demo;

import com.alura.demo.Repository.BookRepository;
import com.alura.demo.main.Principal;
import com.alura.demo.model.DataBook;
import com.alura.demo.model.DataBooksSearch;
import com.alura.demo.service.ConsumeAPI;
import com.alura.demo.service.DataConversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private BookRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository);
		principal.showMenu();

	}

}
