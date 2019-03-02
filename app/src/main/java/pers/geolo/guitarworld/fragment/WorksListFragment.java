package pers.geolo.guitarworld.fragment;

import pers.geolo.guitarworld.R;


/**
 * 作品列表碎片，接收参数String author...
 *
 * @author Geolo
 */
public class WorksListFragment extends BaseFragment {

//    // 作品列表
//    private List<Works> worksList;
//    // RecyclerView适配器
//    private WorksViewAdapter adapter;
//    // 保存传入参数
//    private Bundle bundle;
//
//    @BindView(R.id.srl_update)
//    SwipeRefreshLayout srlUpdate;
//    @BindView(R.id.rv_myWorks)
//    RecyclerView rvMyWorks;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View view = super.onCreateView(inflater, container, savedInstanceState);
//        // 设置RecyclerView管理器
//        rvMyWorks.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        // 初始化适配器
//        worksList = new ArrayList<>();
//        adapter = new WorksViewAdapter(worksList);
//        adapter.setOnItemListener(new WorksViewAdapter.OnItemListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                // 跳转到作品详情页
//                Intent intent = new Intent(getContext(), WorksDetailActivity.class);
//                intent.putExtra("id", worksList.get(position).getId());
//                startActivity(intent);
//            }
//        });
//        // 设置添加或删除item时的动画，这里使用默认动画
//        rvMyWorks.setItemAnimator(new DefaultItemAnimator());
//        // 设置适配器
//        rvMyWorks.setAdapter(adapter);
//
//        // 设置刷新事件监听
//        srlUpdate.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                updateWorksList(bundle.getString("author"));
//            }
//        });
//
//        // 获取数据更新视图
//        bundle = getArguments();
//        if (bundle != null) {
//            updateWorksList(bundle.getString("author"));
//        }
//        return view;
//
//
//        RecyclerView
//    }
//
    @Override
    protected int getContentView() {
        return R.layout.view_group_liner_list_view;
    }
//
//    public void updateWorksList(String author) {
//        HttpUtils.getWorksList(author, new BaseCallback<List<Works>>() {
//            @Override
//            public void onSuccess(List<Works> data) {
//                worksList.clearPassword();
//                worksList.addAll(data);
//                adapter.notifyDataSetChanged();
//                srlUpdate.setRefreshing(false);
//            }
//
//            @Override
//            public void onError(String message) {
//                toast(message);
//            }
//
//            @Override
//            public void onFailure() {
//            }
//        });
//    }

}
