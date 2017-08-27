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

import com.wugq.service.LoginOrRegisterService;
import sun.misc.BASE64Decoder;

import com.wugq.bean.LoginResult;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by wuguoquan on 7/21/17.
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    //请求的Json
    private JSONObject requestJson;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        System.out.println("LoginServlet()--------构造函数");
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

        String userName = requestJson.getString("uname");
        String passWord = requestJson.getString("upwd");

        System.out.println("请求的userName为" + userName + "\n请求的passWord为" + passWord);

        LoginOrRegisterService service = new LoginOrRegisterService();
        LoginResult loginResult = service.login(userName, passWord);

        Map<String, Object> map = new HashMap<>();
        map.put("code", loginResult.getCode());

        //如果成功，还需要加上token
        if (loginResult.getCode() == 0) {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("uid", loginResult.getUid());
            dataMap.put("token", loginResult.getToken());

            map.put("msg", "OK");
            map.put("data", dataMap);
        }else if(loginResult.getCode() == 1){
            map.put("msg", "账户不存在!");
        }else if(loginResult.getCode() == 2){
            map.put("msg", "密码错误!");
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
