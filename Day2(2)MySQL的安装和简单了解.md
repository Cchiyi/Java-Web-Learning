下载mySQL然后把bin路径添加到环境变量
用管理员权限运行cmd后输入install命令
搞了半天才搞懂原来mysql就是个黑框
网上给的临时密码查找方法是没有问题的，修改密码用网上给的方法不ok应该是alter开头的一个命令。

1.1数据库储存在MySQL下的data路径，那些文件夹就是了
首先列出所有数据库用SHOW DATABASES;指令
创建新的数据库用CREATE DATABASE 数据库名;
删除用DROP DATABASE 数据库名；
要操作某个数据库需要先切换到那个数据库用USE 数据库名;
列出当前数据库所有表用SHOW TABLES；命令
MySQL好像不区分大小写 这些命令小写也行

1.2创建表
CREATE TABLE 数据表名 (字段名 字段类型);
字段名用''括起来 字段类型是后置的 整数是int 名字之类的字符串是varchar(括号里面是长度)
1.3在登录前要先启动MySQL服务，命令为net start mysql
登录命令是…..mysql -uxxx -pxxx
u username缩写 p password缩写 注意不要加空格
