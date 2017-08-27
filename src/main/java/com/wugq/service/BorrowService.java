package com.wugq.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.wugq.bean.LoginResult;
import com.wugq.book.ConnectionFactory;
import com.wugq.book.UserDao;
import com.wugq.book.UserDaoImplement;

/**
 * Created by wuguoquan on 7/21/17.
 */
public class BorrowService {
    private UserDao mUserDao = new UserDaoImplement();

    private static final int RESULT_NULL_USERNAME = 1;//没空此用户
    private static final int RESULT_WRONG_PASSWORD = 2; //密码错误
    private static final int RESULT_EXIST_USERNAME = 3; //用户已存在
    private static final int RESULT_EXIST_BOOK = 4; //用户已借有此书
    private static final int RESULT_NULL_BOOK = 6; //用户没有借此书

    /***借书***/
    public int borrow(String userName, String bookName, String author, String category) {

        Connection connection = null;
        LoginResult result = new LoginResult();

        connection = ConnectionFactory.getInstance().makeConnection();

        try {
            //1、判断是否借有此书
            int userId = mUserDao.queryUserIsBorrowBook(connection, userName, bookName);
            if (userId != 0) {

                return RESULT_EXIST_BOOK;
            }
            //2、借书
            int code = mUserDao.borrowBook(connection, userName, bookName, author, category);

            return code;
        } catch (SQLException e) {
            e.printStackTrace();

            return 100;
        }
    }

    /***还书****/
    public int repay(String userName, String bookName) {

        Connection connection = null;
        LoginResult result = new LoginResult();

        connection = ConnectionFactory.getInstance().makeConnection();

        try {
            //1、判断是否借有此书
            int userId = mUserDao.queryUserIsBorrowBook(connection, userName, bookName);
            if (userId == 0) {

                return RESULT_NULL_BOOK; // 用户没有借此书
            }
            //2、借书
            mUserDao.repayBook(connection, userName, bookName);

            return 0;
        } catch (SQLException e) {
            e.printStackTrace();

            return 100;
        }
    }
}
