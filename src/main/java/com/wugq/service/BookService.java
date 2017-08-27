package com.wugq.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.wugq.bean.Book;
import com.wugq.book.ConnectionFactory;
import com.wugq.book.UserDao;
import com.wugq.book.UserDaoImplement;

/**
 * Created by wuguoquan on 7/21/17.
 */
public class BookService {
    private UserDao mUserDao = new UserDaoImplement();
    private static final int RESULT_EXIST_BOOKNAME = 4; //此书已存在


    /***添加新书****/
    public int insertBook(String bookName, String author, String category, int count) {

        Connection connection = null;
        connection = ConnectionFactory.getInstance().makeConnection();

        Book book = new Book();
        book.setBookName(bookName);
        book.setAuthor(author);
        book.setCategory(category);
        book.setCount(count);

        try {
            //1、先判断是否有相应的用户名
            int id = mUserDao.queryBookName(connection, bookName);
            if (id != 0) {
                return RESULT_EXIST_BOOKNAME;
            }
            mUserDao.saveBook(connection, book);
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 100;
        }
    }

    /**查询书籍**/
    public List<Book> queryBook(String query){
        List<Book> listBook = null;
        Connection connection = null;
        connection = ConnectionFactory.getInstance().makeConnection();

        try {
            listBook = mUserDao.queryBook(connection, query);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return listBook;
    }

    /**查询用户自己借的书籍**/
    public List<Book> queryMyBorrowBook(String userName){
        List<Book> listBook = null;
        Connection connection = null;
        connection = ConnectionFactory.getInstance().makeConnection();

        try {
            listBook = mUserDao.queryMyBorowBook(connection, userName);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return listBook;
    }

    /**删除书籍**/
    public void deleteBook(String bookName){

        Connection connection = null;
        connection = ConnectionFactory.getInstance().makeConnection();

        try {
            mUserDao.deleteBook(connection, bookName);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
