package pers.geolo.guitarworld.presenter.image;

import java.io.InputStream;
import okhttp3.ResponseBody;
import retrofit2.Call;

import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.FileApi;
import pers.geolo.guitarworld.network.callback.FileCallBack;
import pers.geolo.guitarworld.presenter.base.BasePresenter;
import pers.geolo.guitarworld.view.ImageDetailView;

public class ImageDetailPresenter extends BasePresenter<ImageDetailView> {

    private String imagePath;

    public void setImagePath(String path) {
        imagePath = path;
    }

    public void loadImage() {
        getView().showLoading();
        HttpService.getInstance().getAPI(FileApi.class)
                .getImage(imagePath)
                .enqueue(new FileCallBack() {
                    @Override
                    protected void onResponseInputStream(InputStream inputStream) {
                        getView().setImageView(inputStream);
                    }

                    @Override
                    protected void onError(int code, String message) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }
}
