package pers.geolo.guitarworld.controller.works;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.shaohui.bottomdialog.BottomDialog;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.controller.base.BaseController;
import pers.geolo.guitarworld.ui.PhotoOptionDialog;
import pers.geolo.guitarworld.util.GlideUtils;
import pers.geolo.guitarworld.util.PhotoUtils;
import pers.geolo.guitarworld.util.ToastUtils;
import pers.geolo.guitarworld.util.WidgetUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/28
 */
public class AddImageManagerController extends BaseController {

    public static final int MAX_IMAGE_NUM = 9;

    @BindView(R.id.grid_view)
    GridView gridView;

    List<File> imageFiles;
    GridViewAdapter adapter;

    @Override
    public Object getLayoutView() {
        return R.layout.add_image_manager;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        imageFiles = new ArrayList<>();
        adapter = new GridViewAdapter();
        gridView.setAdapter(adapter);
        addImagePlaceHolder();
    }

    public void addImagePlaceHolder() {
        imageFiles.add(null);
        adapter.notifyDataSetChanged();
    }

    public List<File> getImageFiles() {
        checkListNotNull();
        return imageFiles;
    }

    private void checkListNotNull() {
        for (int i = 0; i < imageFiles.size(); i++) {
            if (imageFiles.get(i) == null) {
                imageFiles.remove(i);
                i--;
            }
        }
    }

    class GridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return imageFiles.size();
        }

        @Override
        public Object getItem(int position) {
            return imageFiles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //TODO -----------------------------代码优化------------------------------------
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            convertView = null;
            if (convertView == null) {
                //第一次加载创建View，其余复用 View
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.add_image, null);
                holder = new ViewHolder(convertView);
                // 打标签
                convertView.setTag(holder);
            } else {
                //从标签中获取数据
                holder = (ViewHolder) convertView.getTag();
            }
            View finalConvertView = convertView;
            WidgetUtils.measure(convertView, new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    int width = finalConvertView.getMeasuredWidth();
                    int height = width;
                    AbsListView.LayoutParams param = new AbsListView.LayoutParams(width, height);
                    finalConvertView.setLayoutParams(param);
                    LinearLayout.LayoutParams gridViewParam = new LinearLayout.LayoutParams(width * 3, height * 3);
                    if (gridView != null) {
                        gridView.setLayoutParams(gridViewParam);
                    }
                    return true;
                }
            });
            File image = imageFiles.get(position);
            holder.index = position;
            holder.setImage(image);
//            convertView.setId(View.generateViewId());
            return convertView;
        }

        class ViewHolder {

            @BindView(R.id.add_image_button)
            ImageButton addImageButton;
            @BindView(R.id.close_button)
            ImageButton closeButton;

            int index;
            File image;
            PhotoCallback callback;
            BottomDialog dialog;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
                callback = new PhotoCallback();
            }

            public void setImage(File image) {
                this.image = image;
                if (image != null) {
                    GlideUtils.load(getContext(), image, addImageButton);
                    closeButton.setVisibility(View.VISIBLE);
                } else {
                    addImageButton.setImageDrawable(null);
                    addImageButton.setImageResource(R.drawable.ic_add_64);
                    closeButton.setVisibility(View.GONE);
                }
            }

            @OnClick(R.id.add_image_button)
            public void addImage() {
                if (image != null) { // 有图片
                    // TODO 编辑图片
                } else { // 没有图片
                    if (imageFiles.size() >= 9) {
                        ToastUtils.showInfoToast(getContext(), "最多上传9张图片");
                        return;
                    }
//                    dialog = PhotoOptionDialog.show(getChildFragmentManager(), v -> {
//                        PhotoUtils.openCamera(getActivity(), callback);
//                    }, v -> {
//                        PhotoUtils.openAlbum(getActivity(), callback);
//                    });
                }
            }

            @OnClick(R.id.close_button)
            public void close() {
                imageFiles.remove(image);
                notifyDataSetChanged();
            }

            class PhotoCallback implements PhotoUtils.Callback {

                @Override
                public void onSuccess(File photo) {
                    dialog.dismiss();
                    imageFiles.remove(null);
                    imageFiles.add(photo);
                    closeButton.setVisibility(View.VISIBLE);
                    addImagePlaceHolder();
                }

                @Override
                public void onFailure(PhotoUtils.FailureType failureType) {
                    dialog.dismiss();
                    ToastUtils.showErrorToast(getContext(), failureType.name());
                }
            }

        }
    }
}
