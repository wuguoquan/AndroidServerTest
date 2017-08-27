package com.wugq.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64.Decoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wugq.service.BorrowService;
import sun.misc.BASE64Decoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by wuguoquan on 7/21/17.
 */
@WebServlet("/BorrowServlet")
public class BorrowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    //请求的Json
    private JSONObject requestJson;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowServlet() {
        super();
        System.out.println("BorrowServlet()--------构造函数");
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

        String bookName = requestJson.getString("bookName");//getString("bid");
        String userName = requestJson.getString("userName");
        String author = requestJson.getString("author");
        String category = requestJson.getString("category");

        System.out.println("请求的bookName为" + bookName + "\n请求的userName为" + userName);

        BorrowService borrowService = new BorrowService();
        int code = borrowService.borrow(userName, bookName,author,category);

        Map<String, Object> map = new HashMap<>();
        map.put("code", code);

        //如果成功，还需要加上token
        if (code == 0) {

            map.put("msg", "OK");

        }else if(code == 4){
            map.put("msg", "用户已借有此书!");
        }else if(code == 5){
            map.put("msg", "图书库存不足!");
        }else{
            map.put("msg", "未知错误!");
        }

        String result = JSON.toJSONString(map);
        System.out.println("结果为"+result);

        PrintWriter printWriter = response.getWriter();
        printWriter.write(result);
        printWriter.close();
    }
}
