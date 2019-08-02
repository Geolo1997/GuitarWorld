package pers.geolo.guitarworldserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.geolo.guitarworldserver.controller.param.WorksParam;
import pers.geolo.guitarworldserver.dao.ImageMapper;
import pers.geolo.guitarworldserver.dao.LikeWorksMapper;
import pers.geolo.guitarworldserver.dao.WorksMapper;
import pers.geolo.guitarworldserver.entity.Works;

import java.util.List;

@Service
public class WorksService {

    @Autowired
    WorksMapper worksMapper;
    @Autowired
    ImageMapper imageMapper;
    @Autowired
    LikeWorksMapper likeWorksMapper;

    public List<Works> getWorksList(WorksParam param) {
        List<Works> worksList = worksMapper.select(param);
        for (int i = 0; i < worksList.size(); i++) {
            Works works = worksList.get(i);
            // 获取作品图片列表url
            List<String> imagePaths = imageMapper.selectByWorksId(works.getId());
            works.setImageUrls(imagePaths);
            // 获取作品点赞数
            int likeCount = likeWorksMapper.countLikeWorksByWorksId(works.getId());
            works.setLikeCount(likeCount);
            // 设置预览图
            works.setVideoPreviewUrl("http://192.168.1.103:8080/GuitarWorld/file?filePath=20197/31/7e5b8acd-4a12-4199-abd0-f13b2d87b276..jpg");
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

    public void addLikeWorks(String username, int worksId) {
        likeWorksMapper.insert(username, worksId);
    }

    public void cancelLikeWorks(String username, int worksId) {
        likeWorksMapper.delete(username,worksId);
    }
}
