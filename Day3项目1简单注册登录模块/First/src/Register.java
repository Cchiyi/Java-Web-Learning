import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
public class Register extends HttpServlet {


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");//先转码，避免中文过来没办法识别
        String username = request.getParameter("username");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        //得到前端发来的用户名和密码

        Statement statement=null;
        ResultSet resultSet=null;
        Utils_Test utt=new Utils_Test();
        if(password1==null||password2==null||username==null)
        {
            response.getWriter().write("All the textures musted be filled.");
        }
        else if(password1.equals(password2)) {
            try {//这里必须try...catch不然报错
                Connection connection = utt.getConnection();//连一下数据库
                if (connection != null) {
                    statement = connection.createStatement();
                    statement.executeQuery("USE users;");
                    resultSet = statement.executeQuery("SELECT * FROM password;");//给数据库调用语句


                    while (resultSet.next()) {
                        if (username.equals(resultSet.getString(1))) {
                            response.getWriter().write("You can't use this name.Because other hero used it.");
                        } else {
                            statement.execute("INSERT INTO password (username,password) VALUES (" + username + "," + password1 + ")");
                            response.getWriter().write("Successfully register!Now you could login.");
                            //在SQL语句中插入变量
                        }
                    }//遍历一下找找有没有这个用户名，如果有那就不给你注册。
                } else {
                    response.getWriter().write("Connection Failed.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
        {
           response.getWriter().write("You input different password two times.");
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
