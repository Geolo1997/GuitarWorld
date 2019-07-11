package pers.geolo.guitarworldserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.geolo.guitarworldserver.dao.MusicMapper;
import pers.geolo.guitarworldserver.entity.Music;

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

    public List<Music> getMusicList(Map<String, Object> filter) {
        return musicMapper.select(filter);
    }
}
