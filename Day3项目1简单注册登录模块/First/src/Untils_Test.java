import java.io.*;
import java.util.Properties;
import java.sql.*;

public class Utils_Test {
    private static String  driver = null;
    private static String  url = null;
    private static String  username = null;
    private static String password = null;

    static {
        try {

            //获取配置文件的读入流
            InputStream inputStream = Utils_Test.class.getClassLoader().getResourceAsStream("db.properties");

            Properties properties = new Properties();
            properties.load(inputStream);

            //获取配置文件的信息
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");

            //加载驱动类
            Class.forName(driver);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }
    public static void release(Connection connection, Statement statement, ResultSet resultSet) {

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

