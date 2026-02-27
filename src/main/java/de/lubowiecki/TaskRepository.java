package de.lubowiecki;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskRepository {

    public static boolean insert(Task task) throws SQLException {
        final String SQL = "INSERT INTO tasks (id, name, done) VALUES(null, ?, ?)";
        return executePreparedSql(SQL, task) > 0;
    }

    public static boolean update(Task task) throws SQLException {
        final String SQL = "UPDATE tasks SET name = ?, done = ? WHERE id = " + task.getId();
        return executePreparedSql(SQL, task) > 0;
    }

    public static boolean delete(Task task) throws SQLException {
        return delete(task.getId());
    }

    public static boolean delete(int id) throws SQLException {
        final String SQL = "DELETE FROM tasks WHERE id = " + id + " LIMIT 1";
        return executeSql(SQL) > 0;
    }

    public static Optional<Task> find(int id) throws SQLException {
        return find("SELECT * FROM tasks WHERE id = " + id).stream().findFirst();
    }

    public static List<Task> find() throws SQLException {
        return find("SELECT * FROM tasks");
    }

    private static List<Task> find(final String SQL) throws SQLException {
        try(Connection dbh = DbUtils.getConnection(); Statement stmt = dbh.createStatement()) {
            stmt.execute(SQL);
            ResultSet result = stmt.getResultSet();
            List<Task> tasks = new ArrayList<>();
            while(result.next()) {
                tasks.add(create(result));
            }
            return tasks;
        }
    }

    private static Task create(ResultSet result) throws SQLException {
        return new Task(result.getInt("id"), result.getString("name"), result.getBoolean("done"));
    }

    public static void createTable() throws SQLException {
        final String SQL = "CREATE TABLE IF NOT EXISTS tasks " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, done INTEGER NOT NULL)";
        executeSql(SQL);
    }

    private static int executeSql(final String SQL) throws SQLException {
        try(Connection dbh = DbUtils.getConnection(); Statement stmt = dbh.createStatement()) {
            stmt.execute(SQL);
            return stmt.getUpdateCount();
        }
    }

    private static int executePreparedSql(final String SQL, Task task) throws SQLException {
        try(Connection dbh = DbUtils.getConnection(); PreparedStatement stmt = dbh.prepareStatement(SQL)) {
            stmt.setString(1, task.getName());
            stmt.setBoolean(2, task.isDone());
            stmt.execute();
            return stmt.getUpdateCount();
        }
    }
}
