package pers.geolo.guitarworld.view;

/**
 * 相册视图回调
 */
public interface PhotoAlbumViewCallBack {

    void onSuccess(String filePath);

    void onFailure();
}
