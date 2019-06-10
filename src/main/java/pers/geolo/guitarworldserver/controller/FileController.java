package pers.geolo.guitarworldserver.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import pers.geolo.guitarworldserver.entity.ResponseJSONBody;
import pers.geolo.guitarworldserver.service.FileService;
import pers.geolo.guitarworldserver.util.ControllerUtils;

@Controller
public class FileController {

    Logger logger = Logger.getLogger(FileController.class);

    @Autowired
    FileService fileService;

    @RequestMapping(value = "/avatar", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJSONBody<String> uploadAvatar(MultipartFile avatar) {
        logger.debug("收到更新头像请求：" + avatar.getName());
        String username = (String) ControllerUtils.getSessionAttribute("username");
        String path = fileService.saveAvatar(username, avatar);
        return new ResponseJSONBody<>(0, path, null);
    }

    @RequestMapping(value = "/avatar", method = RequestMethod.GET)
    public void getAvatar(String username, HttpServletResponse response) throws IOException {
        logger.debug("获取头像 username = " + username);
        File file = fileService.getAvatar(username);
        if (file == null) {
            logger.debug("文件不存在");
        }
        ControllerUtils.returnFile(file, response);
//        // 设置格式
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDispositionFormData("attachment", file.getName());
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        // 返回下载
//        return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public void getImage(String imagePath, HttpServletResponse response) throws IOException {
        File file = fileService.getImage(imagePath);
        if (file == null) {
            logger.debug("文件不存在");
        }
        ControllerUtils.returnFile(file, response);
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJSONBody<String> uploadImage(MultipartFile image) {
        logger.debug("收到上传图片请求：" + image.getOriginalFilename());
        String username = (String) ControllerUtils.getSessionAttribute("username");
        String path = fileService.saveImage(username, image);
        logger.debug("存储路径为：" + path);
        return new ResponseJSONBody<>(0, path, null);
    }
}
