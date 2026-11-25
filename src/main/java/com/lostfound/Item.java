
package com.lostfound;

import java.time.LocalDate;

public class Item {

    private int id;
    private String itemName;
    private String category;
    private String reportType;
    private String description;
    private String location;
    private LocalDate itemDate;
    private String studentName;
    private String contact;
    private String status;

    // Constructor
    public Item(String itemName, String category,
                String reportType, String description,
                String location, LocalDate itemDate,
                String studentName, String contact) {

        this.itemName = itemName;
        this.category = category;
        this.reportType = reportType;
        this.description = description;
        this.location = location;
        this.itemDate = itemDate;
        this.studentName = studentName;
        this.contact = contact;
        this.status = "Active";
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public String getCategory() {
        return category;
    }

    public String getReportType() {
        return reportType;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public LocalDate getItemDate() {
        return itemDate;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getContact() {
        return contact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
