package com.anel.crud.spring.controller;

import com.anel.crud.spring.model.Book;
import com.anel.crud.spring.model.Filter;
import com.anel.crud.spring.service.interfaces.BookService;
import com.anel.crud.spring.validator.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by PeucT on 15.10.2017.
 */
@Controller
public class MainController {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookValidator bookValidator;

    private int maxRowCount = 10;

    @RequestMapping(value = {"/index", "/"})
    public String initialize(){
        return "redirect:/crud";
    }

    @RequestMapping(value = "/crud" )
    public @ResponseBody ModelAndView main(@ModelAttribute("filter")Filter filter, HttpServletRequest request, Model model){
        ModelAndView modelAndView = new ModelAndView();

        if ("GET".equals(request.getMethod())){
            filter = parseFilterURL(request);
        }else {
            filter.setMaxResult(null);
            filter.setFirstResult(null);
        }


        Integer page;

        if ( request.getParameter("page") == null) {
            page = 1;
        }else {
            try {
                page = Integer.valueOf(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }

        }


        Integer rowsAmount = bookService.countRows(filter);

        if (request.getParameter("psize") != null) {
            try {
                maxRowCount = Integer.valueOf(request.getParameter("psize"));
            } catch (NumberFormatException e) {}
        }

        filter.setFirstResult((page - 1) * maxRowCount);
        filter.setMaxResult(maxRowCount);

        Integer pagesCount = rowsAmount / maxRowCount + ( (rowsAmount % maxRowCount) == 0 ? 0 : 1);

        modelAndView.addObject("data", bookService.getBooks(filter));
        modelAndView.addObject("filter", filter);
        modelAndView.addObject("pageSize", filter.getMaxResult());
        modelAndView.addObject("pagesCount", pagesCount );
        modelAndView.addObject("currentPage", page);



        String filterUrl = bookService.getFilterUrl(filter);
        if (filterUrl != null && !"".equals(filterUrl)){
            modelAndView.addObject("filterStatus", true);
        }else {
            modelAndView.addObject("filterStatus", false);
        }

        modelAndView.addObject("filterURL", filterUrl);
        modelAndView.addObject("readFilterList", bookService.getReadFilterList());
        modelAndView.addObject("urlFirst","/crud?page=1&psize=" + maxRowCount + filterUrl);
        modelAndView.addObject("urlPrev","/crud?page=" + (page - 1) + "&psize=" + maxRowCount + filterUrl);
        modelAndView.addObject("urlNext","/crud?page=" + (page + 1)+ "&psize=" + maxRowCount + filterUrl);
        modelAndView.addObject("urlLast","/crud?page=" + pagesCount +"&psize=" + maxRowCount + filterUrl);

        modelAndView.setViewName("index");
        return modelAndView;

    }

    private Filter parseFilterURL(HttpServletRequest request){

        return bookService.parseFilterURL(request);

    }

    @RequestMapping(value = "/crud/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id")int id){

        bookService.deleteBook(id);

        return "redirect:/crud";
    }

    @RequestMapping(value = "/crud/addBook", method = RequestMethod.GET )
    public @ResponseBody ModelAndView createUser(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("book", new Book());
        modelAndView.setViewName("bookForm");
        return modelAndView;
    }

    @RequestMapping(value = "/addBook/back", method = RequestMethod.GET)
    public String createUserBack() {
        return "redirect:/";
    }

    @RequestMapping(value = "/crud/bookManager/save", method = RequestMethod.POST)
    public String userManagerSave(@ModelAttribute("book")Book book, BindingResult bindingResult, Model model){
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "bookForm";
        }

        bookService.save(book);

        return "redirect:/crud";
    }

    @RequestMapping(value = "/crud/edit/{id}", method = RequestMethod.GET)
    public @ResponseBody ModelAndView editUser(@PathVariable("id")int id){
        ModelAndView modelAndView = new ModelAndView();

        Book book = bookService.getBookById(id);

        if (book == null){
            modelAndView.setViewName("error");
            return modelAndView;
        }
        modelAndView.addObject("book", book);
        modelAndView.setViewName("bookForm");
        return modelAndView;
    }


    @RequestMapping(value = "/crud/setReadTrue/{id}", method = RequestMethod.GET)
    public String saveReadStatus(@PathVariable("id")int id){

        if (bookService.saveReadAlready(id)) {
            return "redirect:/crud";
        }else {
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/error")
    public @ResponseBody ModelAndView errorPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        return modelAndView;
    }

}
