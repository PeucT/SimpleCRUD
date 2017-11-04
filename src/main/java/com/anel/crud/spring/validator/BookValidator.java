package com.anel.crud.spring.validator;

import com.anel.crud.spring.model.Book;
import com.anel.crud.spring.service.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Date;


/**
 * Created by PeucT on 16.10.2017.
 */
@Component
public class BookValidator implements Validator {
    @Autowired
    private BookService bookService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Required");
        if (book.getTitle().length() > 255) {
            errors.rejectValue("title", "Size.title");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "author", "Required");
        if (book.getTitle().length() > 100) {
            errors.rejectValue("author", "Size.author");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "printYear", "Required");

        // Допустим, что год печати не превышает текущий год на 5 лет.
        if (book.getPrintYear() != null && book.getPrintYear() >= 1900 + new Date().getYear() + 5) {
            errors.rejectValue("printYear", "Year.tooLong");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isbn", "Required");
        if (book.getId() != null) {
            if (book.equals(bookService.getBookById(book.getId()))) {
                errors.rejectValue("title", "Save.equals");
            }
        }

        if (book.getDescription().length() > 255) {
            errors.rejectValue("description", "Size.description");
        }
    }
}
