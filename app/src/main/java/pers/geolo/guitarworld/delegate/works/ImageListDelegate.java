package pers.geolo.guitarworld.delegate.works;

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
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BaseDelegate;
import pers.geolo.guitarworld.util.GlideUtils;
import pers.geolo.guitarworld.util.WidgetUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/29
 */
public class ImageListDelegate extends BaseDelegate {

    private static final String IMAGE_URLS = "IMAGE_URLS";
    @BindView(R.id.grid_view)
    GridView gridView;

    List<String> imageUrls;
    GridViewAdapter adapter;

    @Override
    public Object getLayout() {
        return R.layout.image_list;
    }

    public static ImageListDelegate newInstance(ArrayList<String> imageUrls) {

        Bundle args = new Bundle();
        args.putSerializable(IMAGE_URLS, imageUrls);
        ImageListDelegate fragment = new ImageListDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            imageUrls = (List<String>) arguments.getSerializable(IMAGE_URLS);
        }
        adapter = new GridViewAdapter();
        gridView.setAdapter(adapter);
    }

    class GridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return imageUrls.size();
        }

        @Override
        public Object getItem(int position) {
            return imageUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                //第一次加载创建View，其余复用 View
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_item, null);
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
//            // 图片铺满
//            ImageView imageView = (ImageView) ((ViewGroup) finalConvertView).getChildAt(0);
//            WidgetUtils.measure(imageView, new ViewTreeObserver.OnPreDrawListener() {
//                @Override
//                public boolean onPreDraw() {
//                    int width = finalConvertView.getMeasuredWidth();
//                    int imageWidth = imageView.getMeasuredWidth();
//                    int imageHeight = imageView.getMeasuredHeight();
//                    if (imageHeight != 0 && imageWidth != 0) {
//                        LinearLayout.LayoutParams params;
//                        if (imageWidth < imageHeight) {
//                            params = new LinearLayout.LayoutParams(width, width * imageHeight / imageWidth);
//                        } else {
//                            params = new LinearLayout.LayoutParams(width * imageWidth / imageHeight, width);
//                        }
//                        imageView.setLayoutParams(params);
//                        imageView.getViewTreeObserver().removeOnPreDrawListener(this);
//                    }
//                    return true;
//                }
//            });
            String imageUrl = imageUrls.get(position);
            holder.imageUrl = imageUrl;
            GlideUtils.load(getContext(), imageUrl, holder.imageView);
            return convertView;
        }

        class ViewHolder {

            @BindView(R.id.image_view)
            ImageView imageView;

            String imageUrl;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }

            @OnClick(R.id.image_view)
            public void onImageViewClick() {
                getContainerActivity().start(ImageDetailDelegate.newInstance(imageUrl));
            }
        }
    }
}
