package pers.geolo.guitarworldserver.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceContext;

@WebServlet(name = "TestServlet", urlPatterns = "/test")
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String json = getJsonRequest(req);
//        System.out.println(json);
//        Cookie cookie = new Cookie("Geolo", "123456");
//        cookie.setMaxAge(0);
////        resp.setStatus(401);
//        resp.addCookie(cookie);
//        resp.setStatus(200);
//        Date
        // 设置响应内容类型
        resp.setContentType("text/html;charset=UTF-8");

        // 要重定向的新位置
//        String site = new String("http://www.runoob.com");
//
//        resp.setStatus(resp.SC_MOVED_TEMPORARILY);
//        resp.setHeader("Location", site);
        resp.setHeader("Refresh", "3;url='http://www.baidu.com'");


    }

    private String getJsonRequest(HttpServletRequest request) throws IOException {
        StringBuffer sb = new StringBuffer();
        InputStream is = request.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String s = "";
        while ((s = br.readLine()) != null) {
            sb.append(s + "\n");
        }
        return sb.toString();
    }
}
