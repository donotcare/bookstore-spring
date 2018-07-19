INSERT INTO BOOK VALUES (2, 'Spring in action');
INSERT INTO BOOK VALUES (3, 'Hibernate in action');
INSERT INTO BOOK VALUES (4, 'Concurrency in practice');

INSERT INTO AUTHOR VALUES (2, 'Craig Walls');
INSERT INTO AUTHOR VALUES (3, 'Christian Bauer');
INSERT INTO AUTHOR VALUES (4, 'Brian Goetz');
INSERT INTO AUTHOR VALUES (5, 'Joshua Bloch');

INSERT INTO GENRE VALUES (1, 'Programming');

INSERT INTO BOOK_AUTHOR (BOOK_ID, AUTHOR_ID) VALUES (2, 2);
INSERT INTO BOOK_AUTHOR (BOOK_ID, AUTHOR_ID) VALUES (4, 4);
INSERT INTO BOOK_AUTHOR (BOOK_ID, AUTHOR_ID) VALUES (4, 5);

INSERT INTO BOOK_GENRE (BOOK_ID, GENRE_ID) VALUES (2, 1);
INSERT INTO BOOK_GENRE (BOOK_ID, GENRE_ID) VALUES (3, 1);
INSERT INTO BOOK_GENRE (BOOK_ID, GENRE_ID) VALUES (4, 1);



