package pers.geolo.guitarworld.view;

import pers.geolo.guitarworld.view.base.BaseView;
import pers.geolo.guitarworld.view.base.ToastView;

/**
 * @author 桀骜(Geolo)
 * @date 2019-05-29
 */
public interface UploadImageView extends ToastView {
    void addImage(String imagePath);
}
