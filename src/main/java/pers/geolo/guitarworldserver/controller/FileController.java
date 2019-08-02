package pers.geolo.guitarworldserver.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pers.geolo.guitarworldserver.annotation.Auth;
import pers.geolo.guitarworldserver.annotation.AuthType;
import pers.geolo.guitarworldserver.service.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@Auth(AuthType.LOGGED)
public class FileController {

    Logger logger = Logger.getLogger(FileController.class);

    @Autowired
    FileService fileService;

    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public void download(String filePath, HttpServletResponse response) throws IOException {
        logger.debug("收到获取文件请求；filePath=" + filePath);
        File file = fileService.getFile(filePath);
        if (file == null) {
            logger.debug("文件不存在：" + filePath);
            return;
        }
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        InputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[10240];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        inputStream.close();
    }
}
