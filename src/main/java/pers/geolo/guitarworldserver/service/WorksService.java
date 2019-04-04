package pers.geolo.guitarworldserver.service;

import java.util.HashMap;
import java.util.List;
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

    public int removeWorksList(HashMap<String, Object> filter) {
        worksMapper.delete(filter);
        return 0;
    }

    public List<Works> getWorksList(HashMap<String, Object> filter) {
        return worksMapper.select(filter);
    }
}
