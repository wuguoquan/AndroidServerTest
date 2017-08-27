package com.wugq.book;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.wugq.bean.Book;
import com.wugq.bean.User;

/**
 * Created by wuguoquan on 7/21/17.
 */
public interface UserDao {

    /**
     * 将User保存至数据库中
     *
     * @param connection
     * @param user
     */
    public void save(Connection connection, User user) throws SQLException;

    /**
     * 查询数据库中是否有对应的UserName，如果有，返回对应id，没有，返回0
     *
     * @param connection
     * @param userName
     * @param bookName
     * @return
     */
    public int queryUserIsBorrowBook(Connection connection, String userName, String bookName) throws SQLException;

    /**
     * 查询数据库中是否有对应的UserName，如果有，返回对应id，没有，返回0
     *
     * @param connection
     * @param userName
     * @return
     */
    public int queryUserName(Connection connection, String userName) throws SQLException;

    /**
     * 根据User查询数据库中相应的id的password是否正确。如果正确，返回对应的id，否则返回0
     *
     * @param connection
     * @param id
     * @param password
     * @return
     * @throws SQLException
     */
    public int queryPassWord(Connection connection, int id, String password) throws SQLException;

    /**
     * 向指定Id的User中更新token
     *
     * @param connection
     * @param userId
     * @param token
     * @throws SQLException
     */
    public void updateToken(Connection connection, int userId, String token) throws SQLException;

    /**
     * 清空所有人的token
     *
     * @param connection
     * @throws SQLException
     */
    public void clearToken(Connection connection) throws SQLException;

    /**
     * 根据token获取到用户的id，如果没有，返回0
     * @param connection
     * @param token
     * @throws SQLException
     */
    public int getUserId(Connection connection,  String token) throws SQLException;

    /**
     * 将Book保存至数据库中
     *
     * @param connection
     * @param book
     */
    public void saveBook(Connection connection, Book book) throws SQLException;

    /**
     * 查询数据库中是否有对应的bookName，如果有，返回对应id，没有，返回0
     *
     * @param connection
     * @param bookName
     * @return
     */
    public int queryBookName(Connection connection, String bookName) throws SQLException;

    /**
     * 查询数据库中的图书
     *
     * @param connection
     * @param query
     * @return
     */
    public List<Book> queryBook(Connection connection, String query) throws SQLException;

    /**
     *
     *
     * @param connection
     * @param userName
     * @param bookName
     * @param author
     * @param category
     */
    public int borrowBook(Connection connection, String userName, String bookName, String author, String category);

    /**
     *
     *
     * @param connection
     * @param userName
     * @param bookName
     */
    public void repayBook(Connection connection, String userName, String bookName);

    /**
     *
     *
     * @param connection
     * @param bookName
     */
    public void deleteBook(Connection connection, String bookName) throws SQLException;

    /**
     * 查询数据库中的图书
     *
     * @param connection
     * @param userName
     * @return
     */
    public List<Book> queryMyBorowBook(Connection connection, String userName) throws SQLException;

}
