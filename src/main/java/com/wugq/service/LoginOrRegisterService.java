package com.wugq.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.wugq.bean.LoginResult;
import com.wugq.bean.User;
import com.wugq.book.ConnectionFactory;
import com.wugq.book.UserDao;
import com.wugq.book.UserDaoImplement;

/**
 * Created by wuguoquan on 7/21/17.
 */
public class LoginOrRegisterService {
    private UserDao mUserDao = new UserDaoImplement();

    private static final int RESULT_NULL_USERNAME = 1;//没空此用户
    private static final int RESULT_WRONG_PASSWORD = 2; //密码错误
    private static final int RESULT_EXIST_USERNAME = 3; //用户已存在

    /***登陆***/
    public LoginResult login(String userName, String passWord) {

        Connection connection = null;
        LoginResult result = new LoginResult();

        connection = ConnectionFactory.getInstance().makeConnection();

        try {
            //1、先判断是否有相应的用户名
            int id = mUserDao.queryUserName(connection, userName);
            if (id == 0) {
                result.setCode(RESULT_NULL_USERNAME);
                return result;
            }

            //2、在判断密码是否正确
            int userId = mUserDao.queryPassWord(connection, id, passWord);
            if (userId == 0) {
                result.setCode(RESULT_WRONG_PASSWORD);
                return result;
            }

            //3、设置相应的token
            long currentTime  = System.currentTimeMillis();
            String token = userId+"_"+currentTime;

            mUserDao.updateToken(connection, userId, token);
            result.setUid(userId);
            result.setCode(0);
            result.setToken(token);

            return result;
        } catch (SQLException e) {

            e.printStackTrace();

            result.setCode(100);
            return result;
        }
    }

    /***注册****/
    public int register(String userName, String passWord) {

        Connection connection = null;
        connection = ConnectionFactory.getInstance().makeConnection();

        User user = new User();
        user.setName(userName);
        user.setPassword(passWord);

        try {
            //1、先判断是否有相应的用户名
            int id = mUserDao.queryUserName(connection, userName);
            if (id != 0) {
                return RESULT_EXIST_USERNAME;
            }
            mUserDao.save(connection, user);
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 100;
        }
    }
}
