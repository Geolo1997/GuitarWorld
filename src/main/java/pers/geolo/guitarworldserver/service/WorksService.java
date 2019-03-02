package pers.geolo.guitarworldserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.geolo.guitarworldserver.dao.WorksDAO;
import pers.geolo.guitarworldserver.entity.Works;

import java.util.List;

@Service
public class WorksService {

    @Autowired
    WorksDAO worksDAO;

    public List<Works> getWorksList(String author) {
        return worksDAO.listWorks(author);
    }

    public int publishWorks(Works works) {
        worksDAO.add(works);
        return 0;
    }
}
