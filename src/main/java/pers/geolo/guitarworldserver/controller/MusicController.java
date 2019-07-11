package pers.geolo.guitarworldserver.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.geolo.guitarworldserver.entity.Music;
import pers.geolo.guitarworldserver.entity.ResponseJSONBody;
import pers.geolo.guitarworldserver.service.MusicService;

import java.util.List;
import java.util.Map;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/11
 */
@Controller
@RequestMapping("/music")
public class MusicController {

    @Autowired
    MusicService musicService;

    Logger logger = Logger.getLogger(MusicController.class);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseJSONBody<List<Music>> getMusicList(@RequestParam Map<String, Object> filter) {
        logger.debug("收到获取音乐列表请求");
        List<Music> musicList = musicService.getMusicList(filter);
        return new ResponseJSONBody<>(0, musicList, null);
    }
}
