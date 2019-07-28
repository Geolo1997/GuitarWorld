package pers.geolo.guitarworld.entity.event;

import java.io.File;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/28
 */
public class GetImageEvent {

    private File image;

    public GetImageEvent(File image) {
        this.image = image;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }
}
