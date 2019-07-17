package pers.geolo.guitarworldserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.geolo.guitarworldserver.controller.param.MusicParam;
import pers.geolo.guitarworldserver.dao.MusicMapper;
import pers.geolo.guitarworldserver.dao.MusicScoreImageMapper;
import pers.geolo.guitarworldserver.dao.MusicScoreMapper;
import pers.geolo.guitarworldserver.entity.Music;
import pers.geolo.guitarworldserver.entity.MusicScore;
import pers.geolo.guitarworldserver.controller.param.MusicScoreImageParam;
import pers.geolo.guitarworldserver.controller.param.MusicScoreParam;

import java.util.List;
import java.util.Map;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/11
 */
@Service
public class MusicService {

    @Autowired
    MusicMapper musicMapper;
    @Autowired
    MusicScoreMapper musicScoreMapper;
    @Autowired
    MusicScoreImageMapper musicScoreImageMapper;

    public List<Music> getMusicList(MusicParam param) {
        return musicMapper.select(param);
    }

    public List<MusicScore> getMusicScoreList(MusicScoreParam param) {
        return musicScoreMapper.select(param);
    }

    public List<String> getMusicScoreImageList(MusicScoreImageParam param) {
        return musicScoreImageMapper.select(param);
    }
}
