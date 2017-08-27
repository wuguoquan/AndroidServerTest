package com.wugq.book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wugq.bean.Book;
import com.wugq.bean.User;


/**
 * Created by wuguoquan on 7/21/17.
 */
public class UserDaoImplement implements UserDao {
    @Override
    public void save(Connection connection, User user) throws SQLException {
        PreparedStatement statement = connection
                .prepareCall("insert into book_man_sys_user (username,password) values(?,?)");

        statement.setString(1, user.getName());
        statement.setString(2, user.getPassword());

        statement.execute();
    }

    @Override
    public int queryUserName(Connection connection, String userName)
            throws SQLException {
        PreparedStatement statement = connection
                .prepareCall("Select * from book_man_sys_user where username = ?");

        statement.setString(1, userName);

        ResultSet set = statement.executeQuery();
        // 下一个不为空，说明数据库中包含有此字段,则返回此条数据的id
        if (set.next()) {
            return set.getInt("id");
        } else {
            return 0;
        }
    }

    @Override
    public int queryPassWord(Connection connection, int id, String password)
            throws SQLException {
        PreparedStatement statement = connection
                .prepareCall("Select * from book_man_sys_user where id = ? and password = ?");

        statement.setInt(1, id);
        statement.setString(2, password);

        ResultSet set = statement.executeQuery();
        // 下一个不为空，说明数据库中包含有此字段,则返回此条数据的id
        if (set.next()) {
            return set.getInt("id");
        } else {
            return 0;
        }
    }

    @Override
    public void updateToken(Connection connection, int userId, String token)
            throws SQLException {
        PreparedStatement statement = connection
                .prepareCall("update book_man_sys_user set token =  ? where id = ?");

        statement.setString(1, token);
        statement.setInt(2, userId);

        statement.execute();
    }

    @Override
    public void clearToken(Connection connection) throws SQLException {
        PreparedStatement statement = connection
                .prepareCall("update book_man_sys_user set token = ?");

        statement.setString(1, "");

        statement.execute();
    }

    @Override
    public int getUserId(Connection connection, String token)
            throws SQLException {
        PreparedStatement statement = connection
                .prepareCall("select * from book_man_sys_user where token = ?");

        statement.setString(1, token);
        statement.executeQuery();

        ResultSet set = statement.executeQuery();
        if (set.next()) {
            return set.getInt("id");
        } else {
            return 0;
        }
    }

    @Override
    public void saveBook(Connection connection, Book book) throws SQLException {
        PreparedStatement statement = connection
                .prepareCall("insert into book_man_sys_books (bookname,author,category,count) values(?,?,?,?)");

        statement.setString(1, book.getBookName());
        statement.setString(2, book.getAuthor());
        statement.setString(3, book.getCategory());
        statement.setInt(4, book.getCount());

        statement.execute();
    }

    @Override
    public int queryBookName(Connection connection, String bookName)
            throws SQLException {
        PreparedStatement statement = connection
                .prepareCall("Select * from book_man_sys_books where bookname = ?");

        statement.setString(1, bookName);

        ResultSet set = statement.executeQuery();
        // 下一个不为空，说明数据库中包含有此字段,则返回此条数据的id
        if (set.next()) {
            return set.getInt("id");
        } else {
            return 0;
        }
    }

    @Override
    public List<Book> queryBook(Connection connection, String value)
            throws SQLException {
        PreparedStatement statement = connection
                .prepareCall("Select * from book_man_sys_books where bookname like '%"
                        + value
                        + "%'"
                        + " or author like '%"
                        + value
                        + "%' or category like '%" + value + "%'");

        // statement.setString(1, value);
        // statement.setString(2, value);
        // statement.setString(3, value);

        ResultSet set = statement.executeQuery();
        List<Book> listBook = new ArrayList<>();
        // 下一个不为空，说明数据库中包含有此字段,则返回此条数据的id
        while (set.next()) {
            Book book = new Book();
            book.setBookName(set.getString("bookname"));
            book.setAuthor(set.getString("author"));
            book.setCategory(set.getString("category"));
            book.setCount(set.getInt("count"));

            listBook.add(book);
        }
        return listBook;
    }

    @Override
    public int queryUserIsBorrowBook(Connection connection, String userName, String bookName) throws SQLException {
        PreparedStatement statement = connection
                .prepareCall("Select * from book_man_sys_borrow where uname = ? and bname = ?");

        // statement.setString(1, userId);
        statement.setString(1, userName);
        statement.setString(2, bookName);

        ResultSet set = statement.executeQuery();
        // 下一个不为空，说明数据库中包含有此字段,则返回此条数据的id
        if (set.next()) {
            return set.getInt("id");
        } else {
            return 0;
        }
    }

    @Override
    public int borrowBook(Connection connection, String userName, String bookName, String author, String category) {
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        try {
            PreparedStatement statement = connection
                    .prepareCall("Select * from book_man_sys_books where bookname = ?");
            statement.setString(1, bookName);
            ResultSet set = statement.executeQuery();
            // 下一个不为空，说明数据库中包含有此字段,则返回此条数据的id
            if (set.next()) {
                int count = set.getInt("count");
                if (count > 0) {
                    connection.setAutoCommit(false);// JDBC中默认是true，自动提交事务
                    ps1 = connection
                            .prepareStatement("update book_man_sys_books set count = count - 1 where bookname = ?");
                    ps1.setObject(1, bookName);
                    ps1.execute();

                    ps2 = connection
                            .prepareStatement("insert into book_man_sys_borrow (uname,bname,author,category) values(?,?,?,?)");
                    ps2.setObject(1, userName);
                    ps2.setObject(2, bookName);
                    ps2.setObject(3, author);
                    ps2.setObject(4, category);
                    ps2.execute();

                    connection.commit();
                }
            } else {
                return 5;// 图书库存不足
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            return 100;
        }
        return 0;
    }

    @Override
    public void repayBook(Connection connection, String userName, String bookName) {
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        try {
            connection.setAutoCommit(false);
            // JDBC中默认是true，自动提交事务
            ps1 = connection
                    .prepareStatement("update book_man_sys_books set count = count + 1 where bookname = ?");
            ps1.setObject(1, bookName);
            ps1.execute();

            ps2 = connection
                    .prepareStatement("delete from book_man_sys_borrow where uname = ? and bname = ?");
            ps2.setObject(1, userName);
            ps2.setObject(2, bookName);
            ps2.execute();

            connection.commit();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void deleteBook(Connection connection, String bookName) throws SQLException {
        PreparedStatement statement = connection
                .prepareCall("delete from book_man_sys_books where bookname = ?");

        statement.setString(1, bookName);

        int id = statement.executeUpdate();
        System.out.println("deleteBook:"+id);

    }

    @Override
    public List<Book> queryMyBorowBook(Connection connection, String userName) throws SQLException {
        PreparedStatement statement = connection
                .prepareCall("Select * from book_man_sys_borrow where uname = ? ");

        //statement.setString(1, value);
        statement.setString(1, userName);


        ResultSet set = statement.executeQuery();
        List<Book> listBook = new ArrayList<>();
        // 下一个不为空，说明数据库中包含有此字段,则返回此条数据的id
        while (set.next()) {
            Book book = new Book();
            book.setUserName(set.getString("uname"));
            book.setBookName(set.getString("bname"));
            book.setAuthor(set.getString("author"));
            book.setCategory(set.getString("category"));
            book.setTime(set.getTimestamp("borrowtime")+"");

            listBook.add(book);
        }
        return listBook;
    }
}
