package pers.geolo.guitarworldserver.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.geolo.guitarworldserver.dao.WorksMapper;
import pers.geolo.guitarworldserver.entity.Works;

@Service
public class WorksService {

    @Autowired
    WorksMapper worksMapper;

    public int publishWorks(Works works) {
        worksMapper.insert(works);
        return 0;
    }

    public int removeWorksList(Map<String, Object> filter) {
        worksMapper.delete(filter);
        return 0;
    }

    public List<Works> getWorksList(Map<String, Object> filter) {
        return worksMapper.select(filter);
    }
}
