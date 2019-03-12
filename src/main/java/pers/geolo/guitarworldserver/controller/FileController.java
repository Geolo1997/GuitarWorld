package pers.geolo.guitarworldserver.controller;

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

    @Autowired
    FileService fileService;

    @RequestMapping(value = "/uploadProfilePicture", method = RequestMethod.POST)
    @ResponseBody

    public ResponseJSONBody<Void> uploadProfilePicture(MultipartFile profilePicture) {
        System.out.println("收到更新头像请求：" + profilePicture.getName());
        String username = (String) ControllerUtils.getSessionAttribute("username");
        int code = fileService.saveProfilePicture(username, profilePicture);
        return new ResponseJSONBody<>(code);
    }
}
