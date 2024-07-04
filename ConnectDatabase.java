package doorlock;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.net.*;

public class ConnectDatabase {
    public static void main(String[] args) {
        testDatabaseConnection();
    }
    private static void testDatabaseConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("数据库连接成功！");
        } catch (SQLException e) {
            System.err.println("数据库连接失败！");
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
        private static final String URL = "jdbc:mysql://localhost:3306/dbsclab2018";
        private static final String USER = "root";
        private static final String PASSWORD = "sjz040705";


//查找普通用户
        public static boolean checkIfExists(String input) {
            boolean match = false;
            String query = "SELECT count(*) FROM usertab u where u.card_id=?";

            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, input);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        match = resultSet.getInt(1) > 0;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return match;
        }
        //查找管理员账户
    public static boolean checkroot(String input) {
        boolean match = false;
        String query = "SELECT COUNT(*) " +
                "FROM usertab u " +
                "INNER JOIN control c ON u.card_id = c.card_id " +
                "WHERE u.card_id = ? AND c.typee = 'control'";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, input);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    match = resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return match;
    }
    // 插入数据
    public static boolean insertusertabData(String card_id,String name,String sex) {
        boolean success = false;
        String query = "INSERT INTO usertab (card_id,name,sex) VALUES (?,?,?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, card_id);
            statement.setString(2, name);
            statement.setString(3, sex);
            success = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
    public static boolean insertcontrolData(String card_id,String typee) {
        boolean success = false;
        String query = "INSERT INTO control (card_id,typee) VALUES (?,?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, card_id);
            statement.setString(2, typee);
            success = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
    // 删除数据
    public static boolean deleteusertabData(String data) {
        boolean success = false;
        String query = "DELETE FROM usertab WHERE card_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, data);
            success = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    }

