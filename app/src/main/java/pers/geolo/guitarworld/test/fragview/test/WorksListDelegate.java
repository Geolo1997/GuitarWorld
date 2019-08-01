package pers.geolo.guitarworld.test.fragview.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.Jzvd;
import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.delegate.base.BaseDelegate;
import pers.geolo.guitarworld.delegate.base.BeanFactory;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.model.AuthModel;
import pers.geolo.guitarworld.model.UserModel;
import pers.geolo.guitarworld.model.WorksModel;
import pers.geolo.guitarworld.network.ParamBeanHandler;
import pers.geolo.guitarworld.network.param.WorksParam;
import pers.geolo.guitarworld.test.fragview.ViewController;
import pers.geolo.guitarworld.test.fragview.WorksContentController;
import pers.geolo.guitarworld.util.RecyclerViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 桀骜(Geolo)
 * @date 2019-05-31
 */
public class WorksListDelegate extends BaseDelegate {

    private static final String FILTER = "FILTER";
    private static final String WORKS_PARAM = "WORKS_PARAM";

    @BindView(R.id.rv_works_list)
    RecyclerView rvWorksList;
    //    @BindView(R.id.srl_refresh)
//    SwipeRefreshLayout srlRefresh;
    @BindView(R.id.ll_works_list)
    LinearLayout llWorksList;

    List<Works> worksList = new ArrayList<>();
    Adapter adapter;
    Map<String, Object> filter = new HashMap<>();

    AuthModel authModel = BeanFactory.getBean(AuthModel.class);
    UserModel userModel = BeanFactory.getBean(UserModel.class);
    WorksModel worksModel = BeanFactory.getBean(WorksModel.class);

    @Deprecated
    public static WorksListDelegate newInstance(HashMap<String, Object> filter) {
        Bundle args = new Bundle();
        args.putSerializable(FILTER, filter);
        WorksListDelegate worksListDelegate = new WorksListDelegate();
        worksListDelegate.setArguments(args);
        return worksListDelegate;
    }

    public static WorksListDelegate newInstance(WorksParam param) {

        Bundle args = new Bundle();
        // TODO 临时转换
        args.putSerializable(FILTER, ParamBeanHandler.handle(param));
        WorksListDelegate fragment = new WorksListDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onNewBundle(Bundle args) {
        super.onNewBundle(args);
    }

    @Override
    public Object getLayout() {
        return R.layout.works_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        // 获取过滤器
        Bundle bundle = getArguments();
        if (bundle != null) {
            //noinspection unchecked
            filter = (Map<String, Object>) bundle.getSerializable(FILTER);
        }
        initRefreshLayout();
        initRecyclerView();
        loadWorksList();
    }

    private void initRecyclerView() {
        // 设置适配器
        RecyclerViewUtils.setDefaultConfig(getContext(), rvWorksList);
        adapter = new Adapter(WorksContentController.class);
        adapter.setDataList(new ArrayList<>());
        rvWorksList.setAdapter(adapter);
    }

    private void initRefreshLayout() {
        // 设置刷新控件监听
//        srlRefresh.setOnRefreshListener(() -> {
//            loadWorksList();
//            srlRefresh.setRefreshing(false);
//        });
    }

    public void loadWorksList() {
        worksModel.getWorks(filter, new DataListener<List<Works>>() {
            @Override
            public void onReturn(List<Works> worksList) {
                List<Integer> worksIdList = worksList.stream().map(new Function<Works, Integer>() {
                    @Override
                    public Integer apply(Works works) {
                        return works.getId();
                    }
                }).collect(Collectors.toList());
                adapter.setDataList(worksIdList);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    public int getScollYDistance() {
        // TODO 空指针异常
        LinearLayoutManager layoutManager = (LinearLayoutManager) rvWorksList.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        return (position) * itemHeight - firstVisiableChildView.getTop();
    }

    @Override
    public boolean onBackPressedSupport() {
        if (Jzvd.backPress()) {
            return true;
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private List<?> dataList = new ArrayList<>();
        private Class<? extends ViewController> controllerClass;

        public Adapter(Class<? extends ViewController> controllerClass) {
            this.controllerClass = controllerClass;
        }

        public void setDataList(List<?> dataList) {
            this.dataList = dataList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            try {
                ViewController viewController = controllerClass.newInstance();
                viewController.setContainer(viewGroup);
                return new ViewHolder(viewController.createView(), viewController);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
            return null;
        }

        // TODO ViewHolder回收后残留，导致加载混乱
        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Object data = dataList.get(i);
            viewHolder.controller.init(data);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewController controller;

        public ViewHolder(@NonNull View itemView, ViewController controller) {
            super(itemView);
            this.controller = controller;
            ButterKnife.bind(this, itemView);
        }
    }
}
