package com.anel.crud.spring.dao.interfaces;

import com.anel.crud.spring.model.Book;
import com.anel.crud.spring.model.Filter;

import java.util.List;

/**
 * Created by PeucT on 15.10.2017.
 */
public interface BookDao {
    List<Book> getData (Filter filter);
    Integer countRows(Filter filter);

    void deleteBook(Integer id);

    void addBook(Book book);
    void updateBook(Book book);

    Book getBookById(Integer id);
}
