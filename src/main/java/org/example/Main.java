package org.example;

import java.sql.SQLOutput;
import java.util.*;

public class Main {
    static final int [] YEAR_REL  = {1998, 1990, 2000, 2010, 2020}; //, 1990, 2000, 2010, 2020}
    static  final int [] PAGE_COUNT = {200, 250, 300, 350, 400};
    static final String [] BOOK_NAME = {"Мастер и Маргарита", "Программирование для чайников", "Собачье сердце", "Гроккаем алгоритмы", "Морфий"};

    public static void main(String[] args) {
        System.out.println("-------------------------------Работа с аналогом HashSet-------------------------------");
        CustomHashSet<Integer> customHashSet = new CustomHashSet<>();

        //Добавление элементов
        for(int i = 0; i < 3; i ++)
            customHashSet.add(i);
        System.out.println(customHashSet);

        //Удаление элементов
        customHashSet.remove(1);
        System.out.println(customHashSet);

        //Добавление дублирующихся элементов
        for(int i = 0; i < 3; i ++){
            customHashSet.add(i);
            customHashSet.add(i);
            customHashSet.add(i);
        }
        System.out.println(customHashSet);

        //Расширение внутренней таблицы с увеличением количества элементов
        System.out.println(customHashSet);

        for(int i = 0; i < 30; i ++){
            customHashSet.add(i);
            customHashSet.add(i);
            customHashSet.add(i);
        }
        System.out.println(customHashSet);

        //Сужение внутренней таблицы с уменьшением количества элементов
        for(int i = 0; i < 1; i ++)
            customHashSet.remove(i);
        System.out.println(customHashSet);

        for(int i = 0; i < 7; i ++)
            customHashSet.remove(i);
        System.out.println(customHashSet);

        System.out.println("-------------------------------Работа с аналогом LinkedList-------------------------------");

        //Добаление в список всех елементов другой коллекции на примере ArrayList. Добавление в конец списка
        CustomLinkedList<Book> books = new CustomLinkedList<>();
        books.addAll(getRandomBooksList(5));
        System.out.println(books);

        //Добаление в список одтельных элементов в конец списка
        CustomLinkedList<Book> addedBooks = new CustomLinkedList<>();
        addedBooks.add(new Book(200, 1972, "Пикник на обочине"));
        addedBooks.addLast(new Book(220, 1998, "Турецкий гамбит"));
        System.out.println(addedBooks);

        //Добавление элемента в в начало списка
        addedBooks.addFirst(new Book(250, 1956, "Марсианские хроники"));
        System.out.println(addedBooks);

        //Добавление в середину списка
        addedBooks.add(1, new Book(350, 2013, "Острые предметы"));
        System.out.println(addedBooks);

        //Добаление в спиосок всех елементов коллекции CustomLinkedList. Добавление в середину списка
        books.addAll(2, addedBooks);
        System.out.println(books);

        //Удаление элемента из списка по значению
        Book forRemove = new Book(220, 1998, "Турецкий гамбит");
        books.remove(forRemove);
        System.out.println(books);

        //Удаление элемента из списка по индексу
        books.remove(6);
        System.out.println(books);

        //Бэд кейс - выброс исключения на примере удаления по индексу, выходящему за пределы списка
        try{
            books.remove(20);
        }
        catch (Exception e){
            System.out.println("\nПолучение исключения при удалении елемента по несуществующему индексу: ");
            System.out.println(e.getMessage());
        }

        System.out.println("-------------------------------Работа со студентами и книгами-------------------------------");

        Student student1 = new Student("Иванов Иван", getRandomBooksList(5));
        Student student2 = new Student("Петров Петр", getRandomBooksList(5));
        Student student3 = new Student("Викторов Виктор", getRandomBooksList(5));

        ArrayList<Student> students= new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);

        students.stream().peek(i -> System.out.println(i.toString()))               //получаем каждого студента
               .peek(i ->System.out.println(i.getBooks()))                          //получаем книги кадого студента
               .flatMap(i -> i.getBooks().stream())                                 //переходим к самим книгам
               .sorted(Comparator.comparingInt(i -> i.getPageCount()))              //сортируем по количеству страниц
               .distinct()                                                          //берем уникальные книги
               .filter(i -> i.getReleaseYear() > 2000)                              //фильтруем по году выпуска
               .limit(3)                                                     //ограничиваем 3-мя штуками
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