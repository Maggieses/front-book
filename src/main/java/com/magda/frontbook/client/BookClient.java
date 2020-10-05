package com.magda.frontbook.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magda.frontbook.model.Book;
import com.magda.frontbook.model.BookView;
import com.magda.frontbook.model.BooksConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BookClient {

    private ObjectMapper objectMapper = new ObjectMapper();

    private RestTemplate restTemplate;

    public BookClient(@Value("${books.backend.url}") final String url) {
        restTemplate = new RestTemplateBuilder().rootUri(url).build();
    }

    public List<BookView> findAll() {
        List<Book> books = new ArrayList<>();
        ResponseEntity<Book[]> response = restTemplate.getForEntity("/books", Book[].class);
        books.addAll(Arrays.asList(response.getBody()));
        return BooksConverter.modelToView(books);
    }

    public void addBook(@Valid BookView book) {
        HttpEntity<Book> bookHttpEntity = new HttpEntity<>(BooksConverter.viewToModel(book));
        restTemplate.postForEntity("/books", bookHttpEntity, Book.class);
    }

    public BookView getBook(long id) {
        ResponseEntity<Book> response = restTemplate.getForEntity("/books/" + id, Book.class);
        return BooksConverter.modelToView(response.getBody());
    }

    public void deleteBook(long id) {
        restTemplate.delete("/books/" + id);
    }
}
