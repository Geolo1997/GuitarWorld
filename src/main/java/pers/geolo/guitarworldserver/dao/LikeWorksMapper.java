package pers.geolo.guitarworldserver.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/31
 */
@Repository
public interface LikeWorksMapper {

    int countLikeWorksByWorksId(@Param("worksId") int worksId);

    void insert(@Param("username") String username, @Param("worksId") int worksId);

    void delete(@Param("username") String username, @Param("worksId") int worksId);
}
