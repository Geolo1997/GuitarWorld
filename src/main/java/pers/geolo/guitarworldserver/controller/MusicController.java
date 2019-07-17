package pers.geolo.guitarworldserver.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.geolo.guitarworldserver.controller.param.MusicParam;
import pers.geolo.guitarworldserver.controller.param.MusicScoreImageParam;
import pers.geolo.guitarworldserver.controller.param.MusicScoreParam;
import pers.geolo.guitarworldserver.entity.Music;
import pers.geolo.guitarworldserver.entity.MusicScore;
import pers.geolo.guitarworldserver.entity.ResponseEntity;
import pers.geolo.guitarworldserver.service.MusicService;

import java.util.List;

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
    public ResponseEntity<List<Music>> getMusicList(MusicParam param) {
        logger.debug("收到获取音乐列表请求");
        List<Music> musicList = musicService.getMusicList(param);
        return new ResponseEntity<>(0, musicList, null);
    }

    @RequestMapping(value = "/score", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<MusicScore>> getMusicScoreList(@RequestBody MusicScoreParam param) {
        logger.debug("收到获取乐谱列表请求 musicId = " + param.getMusicId());
        List<MusicScore> musicScoreList = musicService.getMusicScoreList(param);
        return new ResponseEntity<>(0, musicScoreList, null);
    }

    @RequestMapping(value = "/score/image", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<String>> getMusicScoreImageList(MusicScoreImageParam param) {
        logger.debug("收到获取乐谱图片列表请求");
        List<String> musicScoreImageList = musicService.getMusicScoreImageList(param);
        return new ResponseEntity<>(0, musicScoreImageList, null);
    }
}
