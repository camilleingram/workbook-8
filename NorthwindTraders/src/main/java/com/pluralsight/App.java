package com.pluralsight;

import java.sql.*;

public class App {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        if (args.length != 2) {
            System.out.println("Application needs two arguments to run: java com.pluralsight.UsingDriverManager <username> <password>");
            System.exit(1);
        }

        String username = args[0];
        String password = args[1];

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", username, password);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Products");

        ResultSet resultSet = preparedStatement.executeQuery();

       while(resultSet.next()) {
           System.out.printf("Product Name: %s%n", resultSet.getString("ProductName"));
       }
    }
}
