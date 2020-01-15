import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class Login extends HttpServlet{
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");//先转码，避免中文过来没办法识别
        String username = request.getParameter("username");
        String password = request.getParameter("password");//得到前端发来的用户名和密码

        Boolean isFind=false;
        Boolean isCorrect=false;//两个用来显示错误信息的变量

        Statement statement=null;
        ResultSet resultSet=null;//声明两个要用的变量，一个是执行语句，一个是数据库读出的结果。
        Utils_Test utt=new Utils_Test();//实例化连接数据库的工具类Utils_Test.class

        try {//这里必须try...catch不然报错
            Connection connection = utt.getConnection();//连一下数据库
            if(connection !=null)
            {
                statement=connection.createStatement();
                statement.executeQuery("USE users;");
                resultSet=statement.executeQuery("SELECT * FROM password;");//给数据库调用语句
                while (resultSet.next())
                {
                    System.out.println(resultSet.getString(1));
                    if(username.equals(resultSet.getString(1)))
                    {
                        isFind=true;
                    }
                }//遍历一下找找有没有这个用户名，我知道这种遍历效率很低，以后再改。
            }
            else
            {
                response.getWriter().write("Connection Failed.");
             }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        if(isFind)//如果找到了用户名，再验密码
        {

            try
            {
                resultSet=statement.executeQuery("SELECT * FROM password;");
                /*再读一遍数据库，因为之前遍历完已经走到数据库末端了，这里把它再放到头头*/
                while (resultSet.next())
                {
                    if(password.equals(resultSet.getString(2)))
                    {
                        isCorrect=true;
                    }
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            response.getWriter().write("No find username,please check your input.You will return to login page after 3 seconds.");
            response.setHeader("Refresh", "3;url=/website/index.html");
            //输出错误信息，三秒后回到登录页面
        }

        if(isCorrect)
        {
            response.getWriter().write("Successfully login!");
        }
        else if(isFind==true&&isCorrect==false)
        {
            response.getWriter().write("Wrong password!You will return to login page after 3 seconds.");
            response.setHeader("Refresh", "3;url=/website/index.html");
            //输出错误信息，三秒后回到登录页面
        }
    }
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException
    {

    }
}
