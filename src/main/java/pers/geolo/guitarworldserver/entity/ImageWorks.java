package pers.geolo.guitarworldserver.entity;

import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @date 2019-05-30
 */
public class ImageWorks extends Works {
    private List<String> imagePaths;

    public void addImage(String imagePath) {
        imagePaths.add(imagePath);
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }
}
