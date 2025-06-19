package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;
import java.util.Scanner;

public class App {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        String username = args[0];
        String password = args[1];

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        System.out.println("Enter shipper Name: ");
        String shipperName = scanner.nextLine();

        System.out.println("Enter shipper phone number");
        String shipperPhone = scanner.nextLine();

        ShippersDao shippersDao = new ShippersDao(dataSource);
        shippersDao.insertShipper(shipperName, shipperPhone);

        for(Shipper shipper : shippersDao.getAllShippers()) {
            System.out.printf("ID: %d%n", shipper.getShipperId());
            System.out.printf("Company: %s%n", shipper.getCompanyName());
            System.out.printf("Phone: %s%n", shipper.getPhone());
        }

        System.out.print("Enter shipper id: ");
        int searchId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new phone number");
        String newPhone = scanner.nextLine();

        shippersDao.updatePhoneNumber(searchId, newPhone);

        for(Shipper shipper : shippersDao.getAllShippers()) {
            System.out.printf("ID: %d%n", shipper.getShipperId());
            System.out.printf("Company: %s%n", shipper.getCompanyName());
            System.out.printf("Phone: %s%n", shipper.getPhone());
        }

        System.out.print("Enter id to delete: ");
        int deleteId = scanner.nextInt();
        scanner.nextLine();

        shippersDao.deleteShipper(deleteId);

        for(Shipper shipper : shippersDao.getAllShippers()) {
            System.out.printf("ID: %d%n", shipper.getShipperId());
            System.out.printf("Company: %s%n", shipper.getCompanyName());
            System.out.printf("Phone: %s%n", shipper.getPhone());
        }


    }
}
