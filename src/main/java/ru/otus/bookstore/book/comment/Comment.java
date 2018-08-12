package ru.otus.bookstore.book.comment;

import javax.persistence.Embeddable;

@Embeddable
public class Comment {
    private String text;

    public Comment() {
    }

    private Comment(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static Comment create(String comment) {
        return new Comment(comment);
    }
}
