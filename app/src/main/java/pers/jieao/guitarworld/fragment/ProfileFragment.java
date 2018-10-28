package pers.jieao.guitarworld.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import pers.jieao.guitarworld.R;
import pers.jieao.guitarworld.activity.MyCreationActivity;
import pers.jieao.guitarworld.base.BaseFragment;

public class ProfileFragment extends BaseFragment {

    @BindView(R.id.bt_profile)
    Button profileBt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_profile;
    }

    @OnClick(R.id.bt_profile)
    void myCreation() {
        Intent intent = new Intent(getActivity(), MyCreationActivity.class);
        startActivity(intent);
    }
}
