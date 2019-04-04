package pers.geolo.guitarworld.view;

import java.io.InputStream;

import pers.geolo.guitarworld.ui.temp.ConfirmCallBack;
import pers.geolo.guitarworld.view.list.ListItemView;

public interface UserItemView extends ListItemView {

    void setUsername(String otherUsername);

    void setFollowState(String followText);

    String getFollowState();

    void showConfirmAlert(ConfirmCallBack confirmCallBack);

    void setEmail(String email);

    String getUsername();

    void setAvatar(InputStream inputStream);
}
