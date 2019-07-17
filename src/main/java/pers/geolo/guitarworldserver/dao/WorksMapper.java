package pers.geolo.guitarworldserver.dao;

import org.springframework.stereotype.Repository;
import pers.geolo.guitarworldserver.controller.param.WorksParam;
import pers.geolo.guitarworldserver.entity.Works;

import java.util.List;
import java.util.Map;

@Repository
public interface WorksMapper {

    List<Works> select(WorksParam param);

    void insert(Works works);

    void delete(WorksParam param);
}
