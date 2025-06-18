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

        System.out.print("Enter the last name of your favorite actor: ");
        String actorLastName = scanner.nextLine();

        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * " +
                            "FROM actor " +
                            "WHERE last_name LIKE ? "
            );

            preparedStatement.setString(1, "%" + actorLastName + "%");

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    System.out.printf("%s %s%n", resultSet.getString("first_name"), resultSet.getString("last_name"));
                }
            }

            System.out.println("Enter actor first name: ");
            String firstName = scanner.nextLine();
            System.out.println("Enter actor last name: ");
            String lastName = scanner.nextLine();

            PreparedStatement preparedStatement1 = connection.prepareStatement(
                    "SELECT first_name, last_name, title " +
                            "FROM actor " +
                            "JOIN film_actor " +
                            "ON actor.actor_id = film_actor.actor_id " +
                            "JOIN film " +
                            "ON film_actor.film_id = film.film_id " +
                            "WHERE first_name LIKE ? AND last_name LIKE ? "
            );

            preparedStatement1.setString(1, "%" + firstName + "%");
            preparedStatement1.setString(2, "%" + lastName + "%");

            try(ResultSet resultSet1 = preparedStatement1.executeQuery()) {

                while(resultSet1.next()) {
                    System.out.printf("%s%n", resultSet1.getString("title"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
