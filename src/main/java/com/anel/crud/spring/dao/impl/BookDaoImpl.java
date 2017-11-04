package com.anel.crud.spring.dao.impl;

import com.anel.crud.spring.dao.interfaces.BookDao;
import com.anel.crud.spring.model.Book;
import com.anel.crud.spring.model.Filter;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by PeucT on 15.10.2017.
 */
@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private SessionFactory sessionFactory;



    @Transactional
    @Override
    public void deleteBook(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = (Book) session.load(Book.class, id);
        if (book != null) {
            session.delete(book);
            session.flush() ;
        }
    }

    @Transactional
    @Override
    public void addBook(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
        session.flush() ;
    }

    @Transactional
    @Override
    public void updateBook(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.update(book);
        session.flush();
    }

    @Transactional
    @Override
    public Book getBookById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = (Book) session.get(Book.class, id);
        return book;
    }

    @Transactional
    @Override
    public List<Book> getData(Filter filter) {
        Criteria crit = restrictResultSize(filter, getCriteria(filter));
        return crit.list();
    }

    @Transactional
    @Override
    public Integer countRows(Filter filter){
        Criteria crit = getCriteria(filter);
        Integer result = Integer.parseInt(crit.setProjection(Projections.rowCount()).uniqueResult().toString());

        return result;
    }

    private Criteria getCriteria(Filter filter){
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Book.class);
        if (filter.getId() != null && filter.getId() != 0){
            crit.add(Restrictions.eq("id", filter.getId()));
        }
        if (filter.getReadAlready() != null) {
            crit.add(Restrictions.eq("readAlready", filter.getReadAlready()));
        }
        if (filter.getAuthor() != null && !"".equals(filter.getAuthor())){
            crit.add(Restrictions.like("author", "%" + filter.getAuthor() + "%"));
        }
        if (filter.getTitle() != null && !"".equals(filter.getTitle())){
            crit.add(Restrictions.like("title", "%" + filter.getTitle() + "%"));
        }
        if (filter.getPrintYear() != null && filter.getPrintYear() != 0){
            crit.add(Restrictions.eq("printYear", filter.getPrintYear()));
        }
        crit.addOrder(Order.asc("id"));

        return crit;
    }

    private Criteria restrictResultSize(Filter filter, Criteria crit){
        if (filter.getFirstResult() != null && filter.getFirstResult() != 0){
            crit.setFirstResult(filter.getFirstResult());
        }
        if (filter.getMaxResult() != null && filter.getMaxResult() != 0){
            crit.setMaxResults(filter.getMaxResult());
        }

        return crit;
    }

}
