package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        if (args.length != 2) {
            System.out.println("Application needs two arguments to run: java com.pluralsight.UsingDriverManager <username> <password>");
            System.exit(1);
        }

        String username = args[0];
        String password = args[1];

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", username, password);

            System.out.println("What do you want to do?");
            System.out.println("1) Display all products");
            System.out.println("2) Display all customers");
            System.out.println("0) Exit");
            System.out.println("Select an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    preparedStatement =
                            connection.prepareStatement("SELECT ProductId, ProductName, UnitPrice, UnitsInStock FROM Products");

                    resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        System.out.printf("Product Id: %d%n", resultSet.getInt("ProductID"));
                        System.out.printf("Name: %s%n", resultSet.getString("ProductName"));
                        System.out.printf("Price: %.2f%n", resultSet.getDouble("UnitPrice"));
                        System.out.printf("Stock: %d%n", resultSet.getInt("UnitsInStock"));
                        System.out.println("---------------");
                    }
                    break;
                case 2:
                    preparedStatement = connection.prepareStatement(
                            "SELECT ContactName, CompanyName, City, Country, Phone " +
                                    "FROM Customers " +
                                    "ORDER BY Country"
                    );

                    resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        System.out.printf("Name: %s%n", resultSet.getString("ContactName"));
                        System.out.printf("Company Name: %s%n", resultSet.getString("CompanyName"));
                        System.out.printf("City: %s%n", resultSet.getString("City"));
                        System.out.printf("Country: %s%n", resultSet.getString("Country"));
                        System.out.printf("Phone: %s%n", resultSet.getString("Phone"));
                        System.out.println("---------------");
                    }
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
