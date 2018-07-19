package ru.otus.bookstore.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.bookstore.author.Author;
import ru.otus.bookstore.author.AuthorService;
import ru.otus.bookstore.genre.Genre;
import ru.otus.bookstore.genre.GenreService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private AuthorService authorService;
    @Mock
    private GenreService genreService;
    @Mock
    private BookDao bookDao;

    @Test
    public void createTest() {
        BookService bookService = new BookServiceImpl(bookDao, authorService, genreService);
        Book book = Book.create("Effective Java");
        doAnswer(i -> {
            ((Book) i.getArgument(0)).setId(100);
            book.setId(100);
            return null;
        }).when(bookDao).insert(book);
        Author author = Author.of(1, "Joshua Block");
        given(authorService.findByName("Joshua Block")).willReturn(author);
        Genre genre = Genre.of(1, "Programming");
        given(genreService.findByName("Programming")).willReturn(genre);
        doNothing().when(bookDao).update(book);
        Book createdBook = bookService.create("Effective Java", "Joshua Block", "Programming");

        assertThat(createdBook).isEqualTo(book);
        assertThat(createdBook.getGenres()).extracting("genre.name").contains("Programming");
        assertThat(createdBook.getAuthors()).extracting("author.name").contains("Joshua Block");
    }
}
