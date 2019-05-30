package pers.geolo.guitarworldserver.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 桀骜(Geolo)
 * @date 2019-05-30
 */
@Repository
public interface ImageMapper {
    void insert(@Param("works_id") int worksId, @Param("path") String path);

    List<String> selectByWorksId(int worksId);
}
