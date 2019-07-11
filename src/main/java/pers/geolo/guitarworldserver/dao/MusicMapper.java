package pers.geolo.guitarworldserver.dao;

import org.springframework.stereotype.Repository;
import pers.geolo.guitarworldserver.entity.Music;

import java.util.List;
import java.util.Map;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/11
 */
@Repository
public interface MusicMapper {

    List<Music> select(Map<String, Object> filter);
}
