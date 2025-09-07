package org.example;

import java.util.Objects;

public class Book {
    private int pageCount;
    private int releaseYear;
    private String name;

    public Book(int pageCount, int releaseYear, String name){
        this.pageCount = pageCount;
        this.releaseYear = releaseYear;
        this.name = name;
    }

    public int getPageCount(){
        return pageCount;
    }
    public void setPageCount(int pageCount){
        this.pageCount = pageCount;
    }

    public int getReleaseYear(){
        return  releaseYear;
    }
    public void setReleaseYear(int releaseYear){
        this.releaseYear = releaseYear;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return String.format("Книга. Название: %s. Год выпуска: %d. Количество страниц: %d.", this.name, this.releaseYear, this.pageCount);
    }

    //методы equals и hashCode переопределены для работы с колекцией CustomLinkedList
    @Override
    public int hashCode() {
        int result = 31;
        result = 31 * result + (this.name != null ? name.hashCode() : 0);
        result = 31 * result + this.releaseYear;
        result = 31 * result + this.pageCount;
        return result;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Book) || (o == null))
            return false;
        Book book = (Book) o;
        return Objects.equals(book.name, this.name) && this.pageCount == book.pageCount && this.releaseYear == book.releaseYear;
    }

}
