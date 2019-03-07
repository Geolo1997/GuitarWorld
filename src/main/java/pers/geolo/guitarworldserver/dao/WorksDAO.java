package pers.geolo.guitarworldserver.dao;

import org.springframework.stereotype.Repository;

import pers.geolo.guitarworldserver.entity.Works;

import java.util.List;

@Repository
public interface WorksDAO {

    void add(Works works);

    Works getWorks(int id);

    List<Works> listWorks(String author);

    List<Works> listAllWorks();
}
