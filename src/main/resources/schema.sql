CREATE TABLE BOOK (
  ID          BIGINT AUTO_INCREMENT,
  NAME        VARCHAR(128)
);

CREATE TABLE AUTHOR (
  ID   BIGINT AUTO_INCREMENT,
  NAME VARCHAR(128)
);

CREATE TABLE GENRE (
  ID   BIGINT AUTO_INCREMENT,
  NAME VARCHAR(128)
);

CREATE TABLE BOOK_AUTHOR (
  BOOK_ID  BIGINT REFERENCES BOOK (ID),
  AUTHOR_ID BIGINT REFERENCES AUTHOR (ID),
);

CREATE TABLE BOOK_GENRE (
  BOOK_ID  BIGINT REFERENCES BOOK (ID),
  GENRE_ID BIGINT REFERENCES GENRE (ID)
);