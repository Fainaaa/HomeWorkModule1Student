package org.example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Main {
    static final int [] YEAR_REL  = {1998, 1990, 2000, 2010, 2020}; //, 1990, 2000, 2010, 2020}
    static  final int [] PAGE_COUNT = {200, 250, 300, 350, 400};
    static final String [] BOOK_NAME = {"Мастер и Маргарита", "Программирование для чайников", "Собачье сердце", "Гроккаем алгоритмы", "Морфий"};

    public static void main(String[] args) {
       Student student1 = new Student("Иванов Иван", getRandomBooksList(5));
       Student student2 = new Student("Петров Петр", getRandomBooksList(5));
       Student student3 = new Student("Викторов Виктор", getRandomBooksList(5));

       ArrayList<Student> students= new ArrayList<>();
       students.add(student1);
       students.add(student2);
       students.add(student3);


      students.stream().peek(i -> System.out.println(i.toString()))                 //получаем каждого студента
               .peek(i ->System.out.println(i.getBooks()))                          //получаем книги кадого студента
               .flatMap(i -> i.getBooks().stream())                                 //переходим к самим книгам
               .sorted(Comparator.comparingInt((Book book) -> book.getPageCount())) //сортируем по количеству страниц
               .distinct()                                                          //берем уникальные книги
               .filter(i -> i.getReleaseYear() > 2000)                              //фильтруем по году выпуска
               .limit(3)                                                    //ограничиваем 3-мя штуками
               .peek(i -> System.out.println(i.getReleaseYear()))                   //Получаем их годы
               .findAny()                                                           //берем любую
               .ifPresentOrElse(                                                    //выводим год выпуска или ошибку
                i -> System.out.println("Год выпуска книги: " + i.getReleaseYear()),
                () -> System.out.println("Книга не найдена"));


    }

    public static ArrayList<Book> getRandomBooksList(int numBooks) {
        Random rand = new Random();
        ArrayList<Book> books= new ArrayList<>();
        for (int i = 0; i < numBooks; i++) {
            books.add(new Book( PAGE_COUNT[rand.nextInt(PAGE_COUNT.length)],
                                YEAR_REL[rand.nextInt(YEAR_REL.length)],
                                BOOK_NAME[rand.nextInt(BOOK_NAME.length)])
            );
        }
        return books;
    }
}