package com.pluralsight;

import javax.sql.DataSource;
import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShippersDao {

    private DataSource dataSource;

    public ShippersDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertShipper(String name, String phoneNumber) {
//        List<Shipper> shippers = new ArrayList<>();

        String query = "INSERT INTO Shippers(CompanyName, Phone) " +
                "VALUES(?, ?)";

        try(Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement =
                connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phoneNumber);

            int rowsUpdated = preparedStatement.executeUpdate();

            if(rowsUpdated > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    while(generatedKeys.next()) {
                        System.out.printf("Shipper Id: %d%n", generatedKeys.getInt(1));
                    }
                }
            }else {
                System.out.println("No rows updated!");
            }

        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Shipper> getAllShippers() {
        List<Shipper> shippers = new ArrayList<>();

        String query = "SELECT * FROM Shippers";

        try(Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement =
                connection.prepareStatement(query)) {

            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                while(resultSet.next()) {
                    int id = resultSet.getInt("ShipperID");
                    String companyName = resultSet.getString("CompanyName");
                    String phone = resultSet.getString("phone");

                    Shipper shipper = new Shipper(id, companyName, phone);
                    shippers.add(shipper);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shippers;
    }

    public void updatePhoneNumber(int id, String phone) {

        String query = "UPDATE Shippers " +
                "SET phone = ? " +
                "WHERE ShipperID = ?";
        String disableSafeUpdates = "SET SQL_SAFE_UPDATES = 0";
        String enableSafeUpdates = "SET SQL_SAFE_UPDATES = 1";

        try(Connection connection = dataSource.getConnection()) {

            try(PreparedStatement disableStatement = connection.prepareStatement(disableSafeUpdates)) {
                disableStatement.executeUpdate();
            }

            try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, phone);
                preparedStatement.setInt(2, id);

                int rowsUpdated = preparedStatement.executeUpdate();

                if(rowsUpdated > 0) {
                    System.out.printf("Phone number updated for Shipper ID %d%n", id);
                } else {
                    System.out.println("Company not found");
                }
            }

            try(PreparedStatement enableStatement = connection.prepareStatement(enableSafeUpdates)) {
                enableStatement.executeUpdate();
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteShipper(int id) {

        String query = "DELETE FROM Shippers " +
                "WHERE ShipperID = ?";
        String disableSafeUpdates = "SET SQL_SAFE_UPDATES = 0";
        String enableSafeUpdates = "SET SQL_SAFE_UPDATES = 1";


        try(Connection connection = dataSource.getConnection()) {

            try(PreparedStatement disableStatement = connection.prepareStatement(disableSafeUpdates)) {
                disableStatement.executeUpdate();
            }

            try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                int rowsUpdated = preparedStatement.executeUpdate();

                if(rowsUpdated > 0) {
                    System.out.printf("Deleted shipper with id %d%n", id);
                }else {
                    System.out.println("ID not found");
                }
            }

            try(PreparedStatement enableStatement = connection.prepareStatement(enableSafeUpdates)) {
                enableStatement.executeUpdate();
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
