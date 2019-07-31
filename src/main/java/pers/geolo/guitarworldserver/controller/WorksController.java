package pers.geolo.guitarworldserver.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pers.geolo.guitarworldserver.controller.param.WorksParam;
import pers.geolo.guitarworldserver.entity.ResponseEntity;
import pers.geolo.guitarworldserver.entity.Works;
import pers.geolo.guitarworldserver.service.FileService;
import pers.geolo.guitarworldserver.service.ImageService;
import pers.geolo.guitarworldserver.service.WorksService;
import pers.geolo.guitarworldserver.util.ControllerUtils;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/works")
public class WorksController {

    Logger logger = Logger.getLogger(WorksController.class);

    @Autowired
    WorksService worksService;
    @Autowired
    FileService fileService;
    @Autowired
    ImageService imageService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Works>> getWorks(WorksParam param) {
//        logger.debug("收到获取Works的请求:id = " + filter.get("id") + ", author = " + filter.get("author"));
        List<Works> worksList = worksService.getWorksList(param);
        return new ResponseEntity<>(0, worksList, null);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> publishWorks(@RequestBody Works works) {
        logger.debug("收到发布作品请求：" + works.toString());
        String currentUsername = (String) ControllerUtils.getSessionAttribute("username");
        if (currentUsername != null && currentUsername.equals(works.getAuthor())) {
            int code = worksService.publishWorks(works);
            imageService.addImages(works.getId(), works.getImageUrls());
            return new ResponseEntity<>(code);
        } else {
            return new ResponseEntity<>(-1);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> removeWorks(WorksParam param) {
        logger.debug("收到删除作品请求：");
        //TODO 删除的是自己的作品
        int code = worksService.removeWorks(param);
        return new ResponseEntity<>(code);
    }


    @RequestMapping(value = "/image", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> uploadImage(MultipartFile image) throws IOException {
        logger.debug("收到上传图片请求：" + image.getOriginalFilename());
        String path = fileService.saveFile(image);
        logger.debug("存储路径为：" + path);
        return new ResponseEntity<>(0, path, null);
    }

    @RequestMapping(value = "/like", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> addLikeWorks(String username, int worksId) {
        logger.debug("用户" + username + "对worksId=" + worksId + "的作品点赞");
        worksService.addLikeWorks(username, worksId);
        return new ResponseEntity<>(0);
    }

    @RequestMapping(value = "/like", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> cancelLikeWorks(String username, int worksId) {
        logger.debug("用户" + username + "对worksId=" + worksId + "的作品取消点赞");
        worksService.cancelLikeWorks(username, worksId);
        return new ResponseEntity<>(0);
    }
}
