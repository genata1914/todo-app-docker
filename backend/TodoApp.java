package backend;

import java.sql.*;
import java.util.*;

public class TodoApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static List<Todo> todos = new ArrayList<>();
    private static Connection connection;

    public static void main(String[] args) {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection("jdbc:mysql://db:3306/todo_app", "root", "root");
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            return;
        }

        while (true) {
            System.out.println("\nTODO List:");
            for (Todo todo : getTasks()) {
                System.out.printf("%d. [%s] %s\n", todo.getId(), todo.isCompleted() ? "x" : " ", todo.getTitle()); //?- IF PROVERKA NA EDIN RED ULESNQVA NI, TODO.ISCOMPLETED E STOINOSTTA KOQTO PROVERQVA
            }

            System.out.println("\nOptions:");
            System.out.println("1. Add task");
            System.out.println("2. Toggle complete");
            System.out.println("3. Edit task");
            System.out.println("4. Delete task");
            System.out.println("5. Exit");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addTask();
                case 2 -> toggleTask();
                case 3 -> editTask();
                case 4 -> deleteTask();
                case 5 -> System.exit(0);
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static List<Todo> getTasks() {
        List<Todo> todoList = new ArrayList<>();
        String sql = "SELECT * FROM todo";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                boolean completed = rs.getBoolean("completed");

                Todo todo = new Todo(id, title, completed);
                todoList.add(todo);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching todo list: " + e.getMessage());
        }

        return todoList;
    }

    private static void addTask() {
        String sql = "INSERT INTO todo(title) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            System.out.print("Enter task title: ");
            String title = scanner.nextLine();
            statement.setString(1, title);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding task: " + e.getMessage());
        }
    }

    private static void toggleTask() {
        String sql = "UPDATE todo SET completed = NOT completed WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            System.out.print("Enter task ID to toggle: ");
            int id = Integer.parseInt(scanner.nextLine());
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error toggling task: " + e.getMessage());
        }
    }

    private static void editTask() {
        String sql = "UPDATE todo SET title = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            System.out.print("Enter task ID to edit: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter new title: ");
            String title = scanner.nextLine();

            statement.setString(1, title);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating task title: " + e.getMessage());
        }
    }

    private static void deleteTask() {
        String sql = "DELETE FROM todo WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            System.out.print("Enter task ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting task: " + e.getMessage());
        }
    }
}
