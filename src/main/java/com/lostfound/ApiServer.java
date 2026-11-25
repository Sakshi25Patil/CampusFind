
package com.lostfound;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class ApiServer {

    public static void main(String[] args) {

        try {

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(
                            LocalDate.class,
                            (JsonSerializer<LocalDate>)
                                    (date, type, context) ->
                                            new JsonPrimitive(date.toString())
                    )
                    .create();

            HttpServer server = HttpServer.create(
                    new InetSocketAddress(8080), 0
            );

            server.createContext("/api/items", exchange -> {

                try {
                    setCorsHeaders(exchange);

                    String method = exchange.getRequestMethod();

                    if (method.equals("OPTIONS")) {
                        exchange.sendResponseHeaders(204, -1);
                        exchange.close();
                        return;
                    }

                    if (method.equals("GET")) {

                        ItemDAO itemDAO = new ItemDAO();

                        sendJsonResponse(
                                exchange,
                                200,
                                gson.toJson(itemDAO.getAllItems())
                        );

                    } else if (method.equals("POST")) {

                        String body = new String(
                                exchange.getRequestBody().readAllBytes(),
                                StandardCharsets.UTF_8
                        );

                        ItemRequest request = gson.fromJson(
                                body, ItemRequest.class
                        );

                        if (request == null ||
                                isBlank(request.itemName) ||
                                isBlank(request.category) ||
                                isBlank(request.reportType) ||
                                isBlank(request.description) ||
                                isBlank(request.location) ||
                                isBlank(request.itemDate) ||
                                isBlank(request.studentName) ||
                                isBlank(request.contact)) {

                            sendJsonResponse(exchange, 400,
                                    gson.toJson(Map.of(
                                            "message",
                                            "Please fill in all required fields."
                                    )));
                            return;
                        }

                        String reportType = request.reportType.trim();

                        if (!reportType.equalsIgnoreCase("Lost") &&
                                !reportType.equalsIgnoreCase("Found")) {

                            sendJsonResponse(exchange, 400,
                                    gson.toJson(Map.of(
                                            "message",
                                            "Invalid report type."
                                    )));
                            return;
                        }

                        reportType = reportType.equalsIgnoreCase("Lost")
                                ? "Lost" : "Found";

                        if (!request.contact.trim().matches("[0-9]{10}")) {

                            sendJsonResponse(exchange, 400,
                                    gson.toJson(Map.of(
                                            "message",
                                            "Contact number must contain 10 digits."
                                    )));
                            return;
                        }

                        LocalDate itemDate = LocalDate.parse(
                                request.itemDate.trim()
                        );

                        Item item = new Item(
                                request.itemName.trim(),
                                request.category.trim(),
                                reportType,
                                request.description.trim(),
                                request.location.trim(),
                                itemDate,
                                request.studentName.trim(),
                                request.contact.trim()
                        );

                        boolean saved = new ItemDAO().addItem(item);

                        if (saved) {
                            sendJsonResponse(exchange, 201,
                                    gson.toJson(Map.of(
                                            "message",
                                            "Item report submitted successfully."
                                    )));
                        } else {
                            sendJsonResponse(exchange, 500,
                                    gson.toJson(Map.of(
                                            "message",
                                            "Unable to save item report."
                                    )));
                        }

                    } else {

                        sendJsonResponse(exchange, 405,
                                gson.toJson(Map.of(
                                        "message", "Method Not Allowed"
                                )));
                    }

                } catch (DateTimeParseException e) {

                    sendJsonResponse(exchange, 400,
                            gson.toJson(Map.of(
                                    "message", "Invalid date format."
                            )));

                } catch (Exception e) {

                    e.printStackTrace();

                    sendJsonResponse(exchange, 500,
                            gson.toJson(Map.of(
                                    "message", "Internal Server Error"
                            )));
                }
            });

            server.start();

            System.out.println(
                    "Lost & Found API running at http://localhost:8080"
            );

        } catch (IOException e) {

            System.out.println("Unable to start server!");
            e.printStackTrace();
        }
    }

    static class ItemRequest {
        String itemName;
        String category;
        String reportType;
        String description;
        String location;
        String itemDate;
        String studentName;
        String contact;
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static void setCorsHeaders(HttpExchange exchange) {

        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Origin", "*"
        );

        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Methods",
                "GET, POST, OPTIONS"
        );

        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Headers",
                "Content-Type"
        );
    }

    private static void sendJsonResponse(
            HttpExchange exchange,
            int statusCode,
            String response
    ) throws IOException {

        exchange.getResponseHeaders().set(
                "Content-Type",
                "application/json; charset=UTF-8"
        );

        sendResponse(exchange, statusCode, response);
    }

    private static void sendResponse(
            HttpExchange exchange,
            int statusCode,
            String response
    ) throws IOException {

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(statusCode, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
