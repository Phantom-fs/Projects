CREATE DATABASE book_rental;

USE book_rental;

-- DROP Database book_rental;

CREATE TABLE users (
  user_id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(50) NOT NULL,
  PRIMARY KEY (user_id)
);

CREATE TABLE books (
  book_id INT NOT NULL AUTO_INCREMENT,
  title VARCHAR(100) NOT NULL,
  author VARCHAR(50) NOT NULL,
  description VARCHAR(500),
  available BOOLEAN DEFAULT true,
  PRIMARY KEY (book_id)
);

CREATE TABLE rentals (
  rental_id INT NOT NULL AUTO_INCREMENT,
  user_id INT NOT NULL,
  book_id INT NOT NULL,
  rental_date DATE NOT NULL,
  due_date DATE NOT NULL,
  return_date DATE,
  returned BOOLEAN DEFAULT false,
  PRIMARY KEY (rental_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (book_id) REFERENCES books(book_id)
);

SELECT * FROM users;
SELECT * FROM books;
SELECT * FROM rentals;

INSERT INTO users (username, password) VALUES 
    ('user1', 'pass1'), 
    ('user2', 'pass2');

INSERT INTO books (title, author, description) VALUES
    ('To Kill a Mockingbird', 'Harper Lee', 'A classic novel about racial injustice in the Deep South.'),
    ('1984', 'George Orwell', 'A dystopian novel about a totalitarian regime and government surveillance.'),
    ('The Catcher in the Rye', 'J.D. Salinger', 'A novel about a teenage boy struggling with identity and conformity.'),
    ('Pride and Prejudice', 'Jane Austen', 'A novel about love and marriage in 19th century England.'),
    ('The Great Gatsby', 'F. Scott Fitzgerald', 'A novel about the American Dream and the decadence of the wealthy elite.'),
    ('The Hobbit', 'J.R.R. Tolkien', 'A fantasy novel about a hobbit who goes on an adventure with a group of dwarves and a wizard.'),
    ('Harry Potter and the Philosopher's Stone', 'J.K. Rowling', 'The first book in the Harry Potter series, about a young boy who discovers he is a wizard.'),
    ('The Lord of the Rings', 'J.R.R. Tolkien', 'A trilogy of fantasy novels about a hobbit and a group of companions who must destroy an evil ring.'),
    ('Animal Farm', 'George Orwell', 'An allegorical novella about the Russian Revolution and the rise of Stalin.'),
    ('The Picture of Dorian Gray', 'Oscar Wilde', 'A novel about a man who sells his soul for eternal youth and beauty.'),
    ('The Adventures of Huckleberry Finn', 'Mark Twain', 'A novel about a boy and a runaway slave who journey down the Mississippi River.'),
    ('The Diary of a Young Girl', 'Anne Frank', 'A memoir about a Jewish girl who lived in hiding during the Holocaust.'),
    ('Brave New World', 'Aldous Huxley', 'A dystopian novel about a society where people are genetically engineered and conditioned for their roles in life.'),
    ('The Canterbury Tales', 'Geoffrey Chaucer', 'A collection of stories told by pilgrims on their way to Canterbury Cathedral.'),
    ('The Divine Comedy', 'Dante Alighieri', 'An epic poem about a journey through Hell, Purgatory, and Heaven.');