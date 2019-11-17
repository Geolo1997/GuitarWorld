package pers.geolo.guitarworldserver.dao;

import org.springframework.stereotype.Repository;
import pers.geolo.guitarworldserver.entity.MusicScore;
import pers.geolo.guitarworldserver.controller.param.MusicScoreParam;

import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/12
 */
@Repository
public interface MusicScoreMapper {

    List<MusicScore> select(MusicScoreParam param);
}
