package pers.geolo.guitarworld.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;

import pers.geolo.guitarworld.R;
import pers.geolo.guitarworld.base.BaseDelegate;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-08
 */
public class ToolBarDelegate extends BaseDelegate {

    private static final String TITLE = "TITLE";
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_option)
    Button btnOption;

    public static ToolBarDelegate newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        ToolBarDelegate fragment = new ToolBarDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onNewBundle(Bundle args) {
        super.onNewBundle(args);
        if (args != null) {
            String title = args.getString(TITLE);
            tvTitle.setText(title);
        }
    }

    @Override
    public Object getLayout() {
        return R.layout.delegate_tool_bar;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String title = bundle.getString(TITLE);
            tvTitle.setText(title);
        }
    }

    @OnClick(R.id.btn_back)
    public void onBackClick() {
        getContainerActivity().pop();
    }

    @OnClick(R.id.btn_option)
    public void onOptionClick() {
        Toast.makeText(getContext(), "ic_option", Toast.LENGTH_SHORT).show();
    }
}
