package com.anel.crud.spring.service.interfaces;

import com.anel.crud.spring.model.Book;
import com.anel.crud.spring.model.Filter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by PeucT on 15.10.2017.
 */
public interface BookService {
    List<Book> getBooks(Filter filter);
    Integer countRows(Filter filter);
    void deleteBook(Integer id);
    void save(Book book);

    Book getBookById(Integer id);
    Boolean saveReadAlready(Integer id);

    String getFilterUrl(Filter filter);

    Filter parseFilterURL(HttpServletRequest request);
    Map<String, String> getReadFilterList();
}
