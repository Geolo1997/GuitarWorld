package pers.geolo.guitarworldserver.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.geolo.guitarworldserver.dao.ImageMapper;

/**
 * @author 桀骜(Geolo)
 * @date 2019-05-30
 */
@Service
public class ImageService {

    @Autowired
    ImageMapper imageMapper;

    public void addImages(int id, List<String> imagePaths) {
        for (String imagePath : imagePaths) {
            imageMapper.insert(id, imagePath);
        }
    }

    public List<String> getImagePaths(int worksId) {
        return imageMapper.selectByWorksId(worksId);
    }
}
