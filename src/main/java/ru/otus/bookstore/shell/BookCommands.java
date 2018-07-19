package ru.otus.bookstore.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.bookstore.book.BookService;

@ShellComponent
public class BookCommands {
    private final BookService bookService;

    public BookCommands(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod("Create new book.")
    public String create(@ShellOption("name") String name, @ShellOption("author") String authorName, @ShellOption("genre") String genreName){
        return bookService.create(name, authorName, genreName).toString();
    }

    @ShellMethod("List all books.")
    public String list() {
        return bookService.findAll().toString();
    }
}
