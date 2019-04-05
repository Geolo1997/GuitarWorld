package pers.geolo.guitarworldserver.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import pers.geolo.guitarworldserver.entity.Works;

@Repository
public interface WorksMapper {

    void insert(Works works);

    void delete(Map<String, Object> filter);

    List<Works> select(Map<String, Object> filter);

    List<Works> selectByFollower(String followerUsername);
}
