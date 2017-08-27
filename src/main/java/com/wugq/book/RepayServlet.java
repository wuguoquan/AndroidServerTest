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
@WebServlet("/RepayServlet")
public class RepayServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    //请求的Json
    private JSONObject requestJson;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RepayServlet() {
        super();
        System.out.println("RepayServlet()--------构造函数");
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

        System.out.println("请求的bookId为" + bookName + "\n请求的userId为" + userName);

        BorrowService borrowService = new BorrowService();
        int code = borrowService.repay(userName, bookName);

        Map<String, Object> map = new HashMap<>();
        map.put("code", code);

        //如果成功，还需要加上token
        if (code == 0) {

            map.put("msg", "OK");

        }else if(code == 6){
            map.put("msg", "用户没有借此书!");
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
