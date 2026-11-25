
package com.lostfound;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    // Add a new item
    public boolean addItem(Item item) {

        String sql = "INSERT INTO items " +
                "(item_name, category, report_type, description, " +
                "location, item_date, student_name, contact, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, item.getItemName());
            statement.setString(2, item.getCategory());
            statement.setString(3, item.getReportType());
            statement.setString(4, item.getDescription());
            statement.setString(5, item.getLocation());
            statement.setDate(6, java.sql.Date.valueOf(item.getItemDate()));
            statement.setString(7, item.getStudentName());
            statement.setString(8, item.getContact());
            statement.setString(9, item.getStatus());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding item!");
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve all items
    public List<Item> getAllItems() {

        List<Item> items = new ArrayList<>();

        String sql = "SELECT * FROM items ORDER BY created_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Item item = new Item(
                        resultSet.getString("item_name"),
                        resultSet.getString("category"),
                        resultSet.getString("report_type"),
                        resultSet.getString("description"),
                        resultSet.getString("location"),
                        resultSet.getDate("item_date").toLocalDate(),
                        resultSet.getString("student_name"),
                        resultSet.getString("contact")
                );

                item.setId(resultSet.getInt("id"));
                item.setStatus(resultSet.getString("status"));

                items.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving items!");
            e.printStackTrace();
        }

        return items;
    }


    public List<Item> getItemsByReportType(String reportType) {

        List<Item> items = new ArrayList<>();

        String sql = "SELECT * FROM items WHERE report_type = ? ORDER BY created_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, reportType);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Item item = new Item(
                            resultSet.getString("item_name"),
                            resultSet.getString("category"),
                            resultSet.getString("report_type"),
                            resultSet.getString("description"),
                            resultSet.getString("location"),
                            resultSet.getDate("item_date").toLocalDate(),
                            resultSet.getString("student_name"),
                            resultSet.getString("contact")
                    );

                    item.setId(resultSet.getInt("id"));
                    item.setStatus(resultSet.getString("status"));

                    items.add(item);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error filtering items!");
            e.printStackTrace();
        }

        return items;
    }


    public boolean updateItemStatus(int itemId, String status) {

        String sql = "UPDATE items SET status = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, itemId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error updating item status!");
            e.printStackTrace();
            return false;
        }
    }


}
