package ru.otus.bookstore;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;


@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(BookstoreApplication.class, args);
		Console.main(args);
	}
}
