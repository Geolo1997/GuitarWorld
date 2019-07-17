package pers.geolo.guitarworldserver.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.geolo.guitarworldserver.controller.param.WorksParam;
import pers.geolo.guitarworldserver.dao.WorksMapper;
import pers.geolo.guitarworldserver.entity.Works;

@Service
public class WorksService {

    @Autowired
    WorksMapper worksMapper;

    public List<Works> getWorksList(WorksParam param) {
        return worksMapper.select(param);
    }

    public int publishWorks(Works works) {
        worksMapper.insert(works);
        return 0;
    }

    public int removeWorks(WorksParam param) {
        worksMapper.delete(param);
        return 0;
    }
}
