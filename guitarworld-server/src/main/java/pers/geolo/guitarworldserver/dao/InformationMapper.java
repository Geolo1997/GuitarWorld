package pers.geolo.guitarworldserver.dao;

import org.springframework.stereotype.Repository;
import pers.geolo.guitarworldserver.entity.Information;

import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/8/1
 */
@Repository
public interface InformationMapper {

    List<Information> selectAll();
}
