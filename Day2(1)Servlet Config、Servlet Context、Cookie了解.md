三、servlet config
3.1
创建时机：在创建完Servlet对象之后，调用init方法之前创建。
得到对象：直接从有参数的init方法中得到。

3.2ServletConfig对象:
       在Servlet的配置文件中，可以使用一个或多个<init-param>标签为servlet配置一些初始化参数。当servlet配置了初始化参数后，web容器在创建servlet实例对象时，会自动将这些初始化参数封装到ServletConfig对象中，并在调用servlet的init()方法时，将ServletConfig对象传递给servlet。进而，通过ServletConfig对象就可以得到当前servlet的初始化参数信息。

3.3方法:
String getServletName()  -- 获取当前Servlet在web.xml中配置的名字
String getInitParameter(String name) -- 获取当前Servlet指定名称的初始化参数的值
Enumeration getInitParameterNames()  -- 获取当前Servlet所有初始化参数的名字组成的枚举
ServletContext getServletContext()  -- 获取代表当前web应用的ServletContext对象

bug日志：需要有一个init函数来初始化
在doGet里写代码以后要在doPost里调用（虽然我也不知道为什么
public void doGet(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
         //获取在web.xml中配置的初始化参数
        String paramVal = this.config.getInitParameter("name");//获取指定的初始化参数
         response.getWriter().print(paramVal);
         
         response.getWriter().print("<hr/>");
        //获取所有的初始化参数
       Enumeration<String> e = config.getInitParameterNames();
//当存在更多对象的时候 读取下一个
         while(e.hasMoreElements()){
            String name = e.nextElement();
             String value = config.getInitParameter(name);
             response.getWriter().print(name + "=" + value + "<br/>");
        }


四、ServletContext对象
4.1概述
WEB容器在启动时，它会为每个WEB应用程序都创建一个对应的ServletContext对象，它代表当前web应用。事实是一个web应用对应一个ServletContext
  ServletConfig对象中维护了ServletContext对象的引用，开发人员在编写servlet时，可以通过ServletConfig.getServletContext方法获得ServletContext对象。
  由于一个WEB应用中的所有Servlet共享同一个ServletContext对象，因此Servlet对象之间可以通过ServletContext对象来实现通讯。ServletContext对象通常也被称之为context域对象。
4.2对象共享
通过setAtrribute和getAtrribute实现对象共享。
先用ServletContextcontext=this.getServletContext();取出servlet context
setAtrribute("名字","值");//名字也要在双引号里面，不然会被当成变量
getAtrrribute("名字")；
每个servlet都是要单独声明的,也就是<servlet></servlet>标签下只有一个servlet声明!
如果声明多个,那就多写几个就可以了!mapping配置也是一样的! 

  原来print之类的指令要访问网页的时候才会执行…毕竟doGet也是载入网页才执行嘛…我说为什么我一直测试不出来…
4.3读取资源
  ServletContext读取就可以避免修改代码的情况，因为ServletContext对象是根据当前web站点而生成的

五、要得到浏览器信息，就找HttpServletRequest对象
doGet是处理客户端发来的Get请求，doPost是处理客户端发来的Post请求
通过<form action="域名" method="post">提交数据到后端去
然后因为是post后端通过doPost里的request.getParameter("");获得值

request.setCharacterEncoding("UTF-8");设置中文，否则乱码

get方式不同，它的数据是从消息行带过去的，没有封装到request对象里面

五、会话技术-Cookie
5.1Cookie的有效期
Cookie的有效期是通过setMaxAge()来设置的。
如果MaxAge为正数，浏览器会把Cookie写到硬盘中，只要还在MaxAge秒之前，登陆网站时该Cookie就有效【不论关闭了浏览器还是电脑】
如果MaxAge为负数，Cookie是临时性的，仅在本浏览器内有效，关闭浏览器Cookie就失效了，Cookie不会写到硬盘中。Cookie默认值就是-1。这也就为什么在我第一个例子中，如果我没设置Cookie的有效期，在硬盘中就找不到对应的文件。
如果MaxAge为0，则表示删除该Cookie。Cookie机制没有提供删除Cookie对应的方法，把MaxAge设置为0等同于删除Cookie

什么是会话技术
基本概念: 指用户开一个浏览器，访问一个网站,只要不关闭该浏览器，不管该用户点击多少个超链接，访问多少资源，直到用户关闭浏览器，整个这个过程我们称为一次会话.

Cookie可以用chorme的Network看，不要忙着在电脑里找cookie了
