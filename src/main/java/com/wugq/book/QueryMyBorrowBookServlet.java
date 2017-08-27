package com.wugq.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64.Decoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wugq.service.BookService;
import sun.misc.BASE64Decoder;

import com.wugq.bean.Book;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


/**
 * Created by wuguoquan on 7/21/17.
 */
@WebServlet("/QueryMyBorrowBookServlet")
public class QueryMyBorrowBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    //请求的Json
    private JSONObject requestJson;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryMyBorrowBookServlet() {
        super();
        System.out.println("QueryMyBorrowBookServlet()--------构造函数");
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String securityString = req.getParameter("datas");
        System.out.println("获取的加密String为:" + securityString);


        requestJson = JSON.parseObject(URLDecoder.decode(securityString, "UTF8"));

        super.service(req, resp);
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        String userName = requestJson.getString("userName");
        System.out.println("userName:"+userName);

        BookService bookService = new BookService();
        List<Book> listBook = bookService.queryMyBorrowBook(userName);

        JSONObject jsonRoot = new JSONObject();
        JSONArray array = new JSONArray();

        if (null != listBook && listBook.size() > 0) {
            for (Book book : listBook) {
                JSONObject jsonOne = new JSONObject();
                jsonOne.put("userName", book.getUserName());
                jsonOne.put("bookName", book.getBookName());
                jsonOne.put("author", book.getAuthor());
                jsonOne.put("category", book.getCategory());
                jsonOne.put("count", book.getCount());
                jsonOne.put("borrowTime", book.getTime());

                array.add(jsonOne);
            }
            jsonRoot.put("books", array);

        }


        String result = jsonRoot.toJSONString() ;
        System.out.println("结果为"+result);

        PrintWriter printWriter = response.getWriter();
        printWriter.write(result);
        printWriter.close();
    }
}
