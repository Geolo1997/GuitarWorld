package pers.geolo.guitarworldserver.dao;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;

import pers.geolo.guitarworldserver.entity.Works;

@Repository
public interface WorksMapper {

    void insert(Works works);

    void delete(HashMap<String, Object> filter);

    List<Works> select(HashMap<String, Object> filter);
}
