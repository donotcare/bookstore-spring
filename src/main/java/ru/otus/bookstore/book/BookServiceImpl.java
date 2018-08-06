package ru.otus.bookstore.book;

import org.springframework.transaction.annotation.Transactional;
import ru.otus.bookstore.author.Author;
import ru.otus.bookstore.author.AuthorService;
import ru.otus.bookstore.book.comment.Comment;
import ru.otus.bookstore.genre.Genre;
import ru.otus.bookstore.genre.GenreService;

import java.util.Collection;

@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceImpl(BookRepository bookDao, AuthorService authorService, GenreService genreService) {
        this.bookDao = bookDao;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public Book create(String name, String authorName, String genreName) {
        Book newBook = Book.create(name);
        Author author = authorService.findByName(authorName);
        bookDao.save(newBook);
        newBook.addAuthor(author);
        Genre genre = genreService.findByName(genreName);
        newBook.addGenre(genre);
        bookDao.save(newBook);
        return newBook;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public void addComment(String bookName, String comment) {
        Book book = bookDao.findFirstByName(bookName);
        book.addComment(comment);
        bookDao.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Comment> getAllCommentsByBook(String bookName) {
        Book book = bookDao.findFirstByName(bookName);
        return book.getComments();
    }
}
