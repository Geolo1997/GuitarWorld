package pers.geolo.guitarworldserver.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "DownLoadServlet", urlPatterns = "/download")
public class DownLoadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String realPath = getServletContext().getRealPath("hello");
        System.out.println(realPath);
        resp.setStatus(200);
//        URLEncoder.encode()
//       Part req.getPart()
    }
}
