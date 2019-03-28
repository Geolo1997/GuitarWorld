package pers.geolo.guitarworldserver.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import static pers.geolo.guitarworldserver.util.ControllerUtils.getRequest;

import pers.geolo.guitarworldserver.entity.ResponseJSONBody;
import pers.geolo.guitarworldserver.service.FileService;
import pers.geolo.guitarworldserver.util.ControllerUtils;

@Controller
public class FileController {

    Logger logger = Logger.getLogger(FileController.class);

    @Autowired
    FileService fileService;

    @RequestMapping(value = "/profilePicture", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJSONBody<Void> uploadProfilePicture(MultipartFile profilePicture) {
        logger.debug("收到更新头像请求：" + profilePicture.getName());
        String username = (String) ControllerUtils.getSessionAttribute("username");
        int code = fileService.saveProfilePicture(username, profilePicture);
        return new ResponseJSONBody<>(code);
    }

    @RequestMapping(value = "/profilePicture", method = RequestMethod.GET)
    public void getProfilePicture(String username, HttpServletResponse response) throws IOException {
        logger.debug("获取头像 username = " + username);
        File file = fileService.getProfilePicture(username);
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
}
