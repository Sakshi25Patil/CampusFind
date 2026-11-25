
package com.lostfound;

public class Main {

    public static void main(String[] args) {

        ItemDAO itemDAO = new ItemDAO();

        int itemId = 1;

        boolean result = itemDAO.updateItemStatus(
                itemId,
                "Recovered"
        );

        if (result) {
            System.out.println("Item Status Updated Successfully!");
        } else {
            System.out.println("Failed to Update Item Status!");
        }
    }
}
