package pers.geolo.guitarworld.ui.temp;

import android.app.Activity;
import android.content.Intent;

public interface ActivityCallback {
    void onSuccess(Intent data);

    void onFailure();
}
