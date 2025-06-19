package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.Scanner;

public class App {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("Application needs two arguments to run: java com.hca.jdbc.UsingDriverManager <username> " +
                    "<password>");
            System.exit(1);
        }

        String username = args[0];
        String password = args[1];

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        DataManager dataManager = new DataManager(dataSource);

        System.out.print("Enter the name of your favorite actor: ");
        String actorName = scanner.nextLine();
        for(Actor actor : dataManager.getActor(actorName)) {
            System.out.printf("ID: %d%n Name: %s %s%n", actor.getActorId(), actor.getFirstName(), actor.getLastName());
            System.out.println("-------------------");
        }


        System.out.print("Enter actor ID: ");
        int actorId = scanner.nextInt();
        System.out.println("This actor has played in:");
        for(Film film : dataManager.getFilm(actorId)) {
            System.out.printf("FilmID: %d%n Title: %s%n Description: %s%n Release Year: %d%n Length: %d mins%n",
                    film.getFilmId(), film.getTitle(), film.getDescription(), film.getReleaseYear(), film.getLength());
            System.out.println("---------------------");
        }
    }
}
