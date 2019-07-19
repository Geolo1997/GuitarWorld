package pers.geolo.guitarworldserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.geolo.guitarworldserver.controller.param.WorksParam;
import pers.geolo.guitarworldserver.dao.ImageMapper;
import pers.geolo.guitarworldserver.dao.WorksMapper;
import pers.geolo.guitarworldserver.entity.Works;

import java.util.List;

@Service
public class WorksService {

    @Autowired
    WorksMapper worksMapper;
    @Autowired
    ImageMapper imageMapper;

    public List<Works> getWorksList(WorksParam param) {
        List<Works> worksList = worksMapper.select(param);
        for (int i = 0; i < worksList.size(); i++) {
            List<String> imagePaths = imageMapper.selectByWorksId(worksList.get(i).getId());
            worksList.get(i).setImagePaths(imagePaths);
        }
        return worksList;
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
