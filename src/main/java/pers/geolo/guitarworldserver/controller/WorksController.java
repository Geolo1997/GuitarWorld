package pers.geolo.guitarworldserver.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pers.geolo.guitarworldserver.entity.ResponseJSONBody;
import pers.geolo.guitarworldserver.entity.Works;
import pers.geolo.guitarworldserver.service.WorksService;
import pers.geolo.guitarworldserver.util.ControllerUtils;

@Controller
public class WorksController {

    private Logger logger = Logger.getLogger(WorksController.class);

    @Autowired
    WorksService worksService;

    @RequestMapping(value = "/publishWorks", method = RequestMethod.POST)
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

    @RequestMapping(value = "/getWorksList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJSONBody<List<Works>> getWorksList(String author) {
        logger.debug("收到获取作品请求:" + author);
        List<Works> worksList = worksService.getWorksList(author);
        return new ResponseJSONBody<>(0, worksList, null);
    }

    @RequestMapping(value = "/getWorks", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJSONBody<Works> getWorksList(int id) {
        logger.debug("收到获取作品请求:" + id);
        Works works = worksService.getWorks(id);
        return new ResponseJSONBody<>(0, works, null);
    }

    //---------test---------------
    @RequestMapping(value = "/getAllWorks", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJSONBody<List<Works>> getAllWorks() {
        List<Works> worksList = worksService.getAllWorks();
        return new ResponseJSONBody<>(0, worksList, null);
    }
}
