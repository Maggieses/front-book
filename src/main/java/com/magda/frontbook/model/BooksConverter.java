package com.magda.frontbook.model;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BooksConverter {

    public static Book viewToModel(final BookView bookView) {
        final Book book = new Book();
        book.setId(bookView.getId());
        book.setTitle(bookView.getTitle());
        book.setAuthors(getAuthors(bookView.getAuthors()));
        book.setReleaseDate(ZonedDateTime.now().withYear(Integer.parseInt(bookView.getYear())));
        book.setDescription(bookView.getDescription());
        return book;
    }

    public static BookView modelToView(final Book book) {
        final BookView bookView = new BookView();
        bookView.setId(book.getId());
        bookView.setTitle(book.getTitle());
        bookView.setAuthors(getAuthors(book.getAuthors()));
        bookView.setYear("" + book.getReleaseDate().getYear());
        bookView.setDescription(book.getDescription());
        return bookView;
    }

    public static List<BookView> modelToView(List<Book> books) {
        return books.stream().map(b -> modelToView(b)).collect(Collectors.toList());
    }

    public static List<Book> viewToModel(List<BookView> books) {
        return books.stream().map(b -> viewToModel(b)).collect(Collectors.toList());
    }

    private static String getAuthors(List<String> authors) {
        StringBuilder authorsStr = new StringBuilder();
        for (final String author : authors) {
            authorsStr.append(author + ";");
        }
        return authorsStr.toString();
    }

    private static List<String> getAuthors(String authorsStr) {
        final String[] authors = authorsStr.split(";");
        return Arrays.asList(authors);
    }
}
