package pers.geolo.guitarworldserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.View;
import java.applet.Applet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

        //        Observable observable;
        //        Observer
//        new Thread().setPriority();
        ExecutorService service = Executors.newSingleThreadExecutor();
        new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        };
        new Thread().interrupt();
        HashMap hashMap;
//Thread.sleep();
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

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {

            int count = 10;

            @Override
            public void run() {
                while (count > 0) {
                    System.out.println(Thread.currentThread().getName() + " " + count);
                    count--;
                    Thread.yield();
                }
            }
        };
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(runnable);
            thread.setName("thread" + i);
            thread.setDaemon(true);
            thread.start();
        }
        System.out.println("end");
//        Executors.newCachedThreadPool(new )
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

            }

        }, 300);
        ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();
        stringThreadLocal.get();
        new Thread().isInterrupted();
//        Thread.interrupted()
        Future future;
//        Executors.newFixedThreadPool();
        new ArrayBlockingQueue<>(2);

    }



}
