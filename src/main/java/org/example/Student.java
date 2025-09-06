package org.example;

import java.util.ArrayList;


public class Student {
    private String name;
    private ArrayList<Book> books;

    public Student(String name, ArrayList<Book> books){
        this.name = name;
        this.books = books;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public ArrayList<Book> getBooks(){
        return  books;
    }
    public void setBooks(ArrayList<Book> books){
        this.books = books;
    }

    @Override
    public String toString(){
        return String.format("Студент. Имя: %s", this.name);
    }
}
