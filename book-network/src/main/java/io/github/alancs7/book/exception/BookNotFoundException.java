package io.github.alancs7.book.exception;

public class BookNotFoundException extends ResourceNotFoundException {

    public BookNotFoundException(String message) {
        super(message);
    }
}
