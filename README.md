# AndroidServerTest
搭建简单Android服务器，可以查阅图书资料

[安装ideal]
1. 解压ideaIU-2017.1.5-no-jdk.tar.gz
2. 点击./bin/idea.sh
3. 选择菜单Help->Register->License server，填上http://idea.iteblog.com/key.php来激活

[安装Tomcat]
1. 解压apache-tomcat-8.0.45.tar.gz
2. 修改bin/startup.sh如下：
	##20170711添加jdk和jre环境变量
	JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
	JRE_HOME=$JAVA_HOME/jre
	PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME
	CLASSPATH=.:$JRE_HOME/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
	TOMCAT_HOME=/home/wuguoquan/tomcat
3. 启动tomcat： sudo ./bin/startup.sh
4. 验证是否启动成功：http://localhost:8080/
4. 关闭tomcat： sudo ./bin/shutdown.sh

[安装MySQL]
1. sudo apt-get install mysql-server  //安装过程中会提示设置密码什么的，注意设置了不要忘了
2. apt-get isntall mysql-client
3. sudo apt-get install libmysqlclient-dev
4. sudo netstat -tap | grep mysql //通过命令检查之后，如果看到有mysql 的socket处于 listen 状态则表示安装成功。
5. mysql -u root -p //登陆mysql数据库,-u 表示选择登陆的用户名， -p 表示登陆的用户密码，上面命令输入之后会提示输入密码，此时输入密码就可以登录到mysql。

[连接MySQL]
1. mysql -u root -p
2. GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'wugq901216' WITH GRANT OPTION;
3. exit
4. cd /etc/mysql
5. sudo vi mysql.conf.d/mysqld.cnf
6. 注释bind-address = 127.0.0.1
7. sudo /etc/init.d/mysql restart

[执行SQL语句]
1. mysql -u root -p
2. source SQL文件路径
3. exit

[京东云远程上传]
sudo apt-get install lrzsz 
sz urls.txt
rz
