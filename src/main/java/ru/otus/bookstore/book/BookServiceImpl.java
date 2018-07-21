package ru.otus.bookstore.book;

import ru.otus.bookstore.author.Author;
import ru.otus.bookstore.author.AuthorService;
import ru.otus.bookstore.book.comment.Comment;
import ru.otus.bookstore.genre.Genre;
import ru.otus.bookstore.genre.GenreService;

import java.util.Collection;

public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceImpl(BookDao bookDao, AuthorService authorService, GenreService genreService) {
        this.bookDao = bookDao;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public Book create(String name, String authorName, String genreName) {
        Book newBook = Book.create(name);
        Author author = authorService.findByName(authorName);
        bookDao.insert(newBook);
        newBook.addAuthor(author);
        Genre genre = genreService.findByName(genreName);
        newBook.addGenre(genre);
        bookDao.update(newBook);
        return newBook;
    }



    @Override
    public Collection<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public void addComment(String bookName, String comment) {
        Book book = bookDao.findByName(bookName);
        book.addComment(comment);
        bookDao.update(book);
    }

    @Override
    public Collection<Comment> getAllCommentsByBook(String bookName) {
        Book book = bookDao.findByName(bookName);
        return book.getComments();
    }
}
