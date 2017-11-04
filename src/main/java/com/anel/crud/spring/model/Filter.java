package com.anel.crud.spring.model;

/**
 * Created by PeucT on 15.10.2017.
 */
public class Filter {
    private Integer id;
    private String title;
    private String author;
    private Integer printYear;
    private Boolean readAlready;
    private Integer firstResult;
    private Integer maxResult;

    public Filter() {
    }

    public Filter(Book book){
        id = book.getId();
        title = book.getTitle();
        author = book.getAuthor();
        printYear = book.getPrintYear();
        readAlready = book.getReadAlready();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPrintYear() {
        return printYear;
    }

    public void setPrintYear(Integer printYear) {
        this.printYear = printYear;
    }

    public Boolean getReadAlready() {
        return readAlready;
    }

    public void setReadAlready(Boolean readAlready) {
        this.readAlready = readAlready;
    }

    public Integer getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    public Integer getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(Integer maxResult) {
        this.maxResult = maxResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Filter filter = (Filter) o;

        if (id != null ? !id.equals(filter.id) : filter.id != null) return false;
        if (title != null ? !title.equals(filter.title) : filter.title != null) return false;
        if (author != null ? !author.equals(filter.author) : filter.author != null) return false;
        if (printYear != null ? !printYear.equals(filter.printYear) : filter.printYear != null) return false;
        if (readAlready != null ? !readAlready.equals(filter.readAlready) : filter.readAlready != null) return false;
        if (firstResult != null ? !firstResult.equals(filter.firstResult) : filter.firstResult != null) return false;
        return maxResult != null ? maxResult.equals(filter.maxResult) : filter.maxResult == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (printYear != null ? printYear.hashCode() : 0);
        result = 31 * result + (readAlready != null ? readAlready.hashCode() : 0);
        result = 31 * result + (firstResult != null ? firstResult.hashCode() : 0);
        result = 31 * result + (maxResult != null ? maxResult.hashCode() : 0);
        return result;
    }
}
