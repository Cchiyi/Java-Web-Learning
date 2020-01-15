1.配置
新建一个普通的idea项目，导入sql的jar就可以
在project structure-Libraries里添加sql的支持包
Modules里面也要添加，Modules里面添加成功可以看见左侧的Project栏会多出mysql-connection-…..jar的字样，比较好辨认
Libraries里面忘记添加会编译失败
view-Tool windows-database里面记得也要设置

2.写一个连接的简单class
需要三个支持的头文件
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

接下来声明三个private的string
   private static final String URL = "jdbc:mysql://localhost:3306/helloworld?serverTimezone=UTC";
  这个是mysql库的地址，helloworld可以换成mysql资源文件夹里任意一个数据库的名字，可以加database名字也可以不加，不加的话后面的语句应该有USE helloworld，是等效的
后面的?serverTimezone=UTC是我后来加的，因为不加会报错，报错信息百度翻译是时区不一致，在网上查阅相关资料后了解到是数据库的时区和我当地时区不一致，要解决这个问题就需要加上时区的设置。
    private static final String USER = "root";
   上面是登录mysql的用户名，下面是登录mysql的密码，都要用双引号括起来。
    private static final String PASSWORD = "004500";

    private static Connection conn = null;   //(定义一个空的Connection)

我发现这里去掉static就没办法try…catch….我也不知道为啥，所以这就是加个static的原因吧
  static {
        //使用try-catch语句，抛出错误
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            //使用你在头部定义的三个变量，分别确定连接数据库的位置，用户名，密码
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
Java class.forname:菜鸟教程上的解释：Class.forName 传入 com.mysql.jdbc.Driver 之后,就知道我连接的数据库是 mysql。
  我传入这句话编译器提醒我这句话太老了，应该换成新的com.mysql.cj.jdbc.Driver，就按照编译器提醒的换了。
  依然复制官方解释：Class.forName 方法的作用，就是初始化给定的类。而我们给定的 MySQL 的 Driver 类中，它在静态代码块中通过 JDBC 的 DriverManager 注册了一下驱动。我们也可以直接使用 JDBC 的驱动管理器注册 mysql 驱动，从而代替使用 Class.forName。
  综上所述这句话照抄就完了。
  后面connection开头的那句话就是连接数据库，固定格式耶~
  后面是正常的捕获错误语句。
  public static Connection getConnection() {
        return conn;
    }
原来方法getConnection是自定义的，尴尬orz
啊可是感觉跟前面调用的话有点矛盾，又调用又返回，算了不管了先按这个写。
//不不不上面的getConnection是DriverManager里面的的方法
这里定义的是给main里调用的啊喂！不矛盾的！！！
  public static void main(String[] args) {
        Connection connection = getConnection();
        if (connection != null) {
            System.out.println("JDBC测试成功,成功连接到数据库！");
        }
    }

测试一下有没有获得连接，因为一开始getConnection的值应该是null如果连接上了就不是null。
  虽然只是一个简单的测试类但基本掌握了JDBC配置的方法，还是很开心哒。

3.入个门
Statement statement = null;
ResultSet resultSet = null;
多了俩语句，第一个语句貌似用来在Java里给数据库语句
第二个语句是结果集，可能是用来打印库的？
所以try里面补两句
statement= conn.createStatement();
resultSet=statement.executeQuery("SELECT*FROM helloworld");
这条用来执行SQL语句

读出表里的数据，在main里执行，用了try…catch
while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
           }
后面的1/2数字是第几栏，如果有更多栏需要读更多
可以根据数据类型换成getInt(num);

后面还有关闭资源的语句：
 /*关闭资源，后调用的先关闭。关闭之前，要判断对象是否存在* */
if (resultSet != null) {
      try {resultSet.close(); } catch (SQLException e) {e.printStackTrace();}
     }
 if (statement != null) {
   try { statement.close(); } catch (SQLException e) {e.printStackTrace(); }
     }
if (conn != null) {
     try { conn.close();} catch (SQLException e) {e.printStackTrace();}
     }

Connection对象 客户端与数据库所有的交互都是通过Connection来完成的。
Statement对象 用于向数据库发送Sql语句，对数据库的增删改查都可以通过此对象发送sql语句完成。
ResultSet对象 代表Sql语句的执行结果，当Statement对象执行executeQuery()时，会返回一个ResultSet对象
  ResultSet对象维护了一个数据行的游标【简单理解成指针】，调用ResultSet.next()方法，可以让游标指向具体的数据行，进行获取该行的数据

4.写一个简单工具类
  通过上面的理解，我们已经能够使用JDBC对数据库的数据进行增删改查了，我们发现，无论增删改查都需要连接数据库，关闭资源，所以我们把连接数据库，释放资源的操作抽取到一个工具类。
  连接数据库的driver，url，username，password通过配置文件来配置，可以增加灵活性
当我们需要切换数据库的时候，只需要在配置文件中改以上的信息即可
①写配置文件
 文件名db.properties 
文件内容
url=jdbc:mysql://localhost:3306/helloworld?serverTimezone=UTC
username=root
password=004500
driver=com.mysql.cj.jdbc.Driver
内容文本一律不需要加引号
②写一个工具类命名为UtilsDemo(其实你爱命名成啥就是啥)
先初始化：
    private static String  driver = null;
    private static String  url = null;
    private static String  username = null;
    private static String password = null;
给了注释说静态代码块在文件编译的时候执行：
static包try，里面
a.
//获取配置文件的读入流
InputStream inputStream = UtilsDemo.class.getClassLoader().getResourceAsStream("db.properties");            Properties properties = new Properties();
properties.load(inputStream);

b.
//获取配置文件的信息
driver = properties.getProperty("driver");
url = properties.getProperty("url");
username = properties.getProperty("username");
password = properties.getProperty("password");
//加载驱动类
Class.forName(driver);

c.写一个连接方法
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }
d.关闭方法
代码同“3.入个门”中关闭资源的方法
