package pers.geolo.guitarworldserver.controller;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import pers.geolo.guitarworldserver.entity.ResponseJSONBody;
import pers.geolo.guitarworldserver.entity.Works;
import pers.geolo.guitarworldserver.service.WorksService;
import pers.geolo.guitarworldserver.util.ControllerUtils;

@Controller
@RequestMapping("/works")
public class WorksController {

    @Autowired
    WorksService worksService;
    private Logger logger = Logger.getLogger(WorksController.class);

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseJSONBody<Void> publishWorks(@RequestBody Works works) {
        logger.debug("收到发布作品请求：" + works.getTitle());
        String currentUsername = (String) ControllerUtils.getSessionAttribute("username");
        if (currentUsername != null && currentUsername.equals(works.getAuthor())) {
            int code = worksService.publishWorks(works);
            return new ResponseJSONBody<>(code);
        } else {
            return new ResponseJSONBody<>(-1);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseJSONBody<List<Works>> getWorksList(String author) {
        logger.debug("收到获取作品请求:" + author);
        List<Works> worksList = worksService.getWorksList(author);
        return new ResponseJSONBody<>(0, worksList, null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJSONBody<Works> getWorks(@PathVariable("id") int id) {
        logger.debug("收到获取作品请求:" + id);
        Works works = worksService.getWorks(id);
        return new ResponseJSONBody<>(0, works, null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseJSONBody<Void> removeWorks(@PathVariable("id") int id) {
        logger.debug("收到删除作品请求：" + id);
        //TODO 删除的是自己的作品
        int code = worksService.removeWorks(id);
        return new ResponseJSONBody<>(code);
    }

    //---------test---------------
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    public ResponseJSONBody<List<Works>> getAllWorks() {
        List<Works> worksList = worksService.getAllWorks();
        return new ResponseJSONBody<>(0, worksList, null);
    }
}
