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

    public int publishWorks(Works works) {
        worksDAO.add(works);
        return 0;
    }

    public List<Works> getWorksList(String author) {
        return worksDAO.listWorks(author);
    }

    public Works getWorks(int id) {
        return worksDAO.getWorks(id);
    }

    public List<Works> getAllWorks() {
        return worksDAO.listAllWorks();
    }

    public int removeWorks(int id) {
        worksDAO.remove(id);
        return 0;
    }
}
