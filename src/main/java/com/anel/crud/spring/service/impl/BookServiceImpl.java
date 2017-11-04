package com.anel.crud.spring.service.impl;

import com.anel.crud.spring.dao.interfaces.BookDao;
import com.anel.crud.spring.model.Book;
import com.anel.crud.spring.model.Filter;
import com.anel.crud.spring.service.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PeucT on 15.10.2017.
 */
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    public List<Book> getBooks(Filter filter) {
        return bookDao.getData(filter);
    }

    @Override
    public Integer countRows(Filter filter) {
        return bookDao.countRows(filter);
    }

    @Override
    public void deleteBook(Integer id) {
        bookDao.deleteBook(id);
    }

    @Override
    public void save(Book book) {
        if (book.getId() == null || book.getId() == 0){
            bookDao.addBook(book);
        }else {
            Book bookCurrent = bookDao.getBookById(book.getId());
            if (!bookCurrent.equals(book)){
                book.setReadAlready(false);
                bookDao.updateBook(book);
            }
        }
    }


    @Override
    public Boolean saveReadAlready(Integer id){

        Book book = getBookById(id);

        if (book == null){
            return false;
        }
        book.setReadAlready(true);
        bookDao.updateBook(book);
        return true;
    }

    @Override
    public String getFilterUrl(Filter filter) {
        String filterUrl = "";

        if (filter.getId() != null && filter.getId() != 0){
            filterUrl = filterUrl + "&id=" + filter.getId();
        }
        if (filter.getTitle() != null && !"".equals(filter.getTitle())) {
            filterUrl = filterUrl + "&title=" + filter.getTitle();
        }
        if (filter.getAuthor() != null && !"".equals(filter.getAuthor())) {
            filterUrl = filterUrl + "&author=" + filter.getAuthor();
        }
        if (filter.getPrintYear() != null && filter.getPrintYear() != 0) {
            filterUrl = filterUrl + "&year=" + filter.getPrintYear();
        }
        if (filter.getReadAlready() != null) {
            filterUrl = filterUrl + "&read=" + filter.getReadAlready();
        }
        return filterUrl;

    }

    @Override
    public Filter parseFilterURL(HttpServletRequest request) {
        Filter filter = new Filter();
        Integer id;

        if ( request.getParameter("id") == null) {
            id = null;
        } else {
            try {
                id = Integer.valueOf(request.getParameter("id"));
            } catch (NumberFormatException e) {
                id = null;
            }
        }

        Integer year;
        if ( request.getParameter("year") == null) {
            year = null;
        } else {
            try {
                year = Integer.valueOf(request.getParameter("year"));
            } catch (NumberFormatException e) {
                year = null;
            }
        }

        if (request.getParameter("read") != null){
            filter.setReadAlready(new Boolean(request.getParameter("read")));
        }

        filter.setId(id);
        filter.setPrintYear(year);
        filter.setTitle(request.getParameter("title"));
        filter.setAuthor(request.getParameter("author"));
        return filter;
    }

    @Override
    public Map<String, String> getReadFilterList() {
        Map<String, String> readFilterList = new LinkedHashMap<>();
        readFilterList.put("","Статус прочтения");
        readFilterList.put("false","Не прочитано");
        readFilterList.put("true","Прочитано");
        return readFilterList;
    }

    @Override
    public Book getBookById(Integer id) {
        return bookDao.getBookById(id);
    }


}
