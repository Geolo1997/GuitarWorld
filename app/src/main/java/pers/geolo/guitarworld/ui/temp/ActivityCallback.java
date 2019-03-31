package pers.geolo.guitarworld.temp;

import android.app.Activity;
import android.content.Intent;

public interface ActivityCallback {
    void onSuccess(Intent data);

    void onFailure();
}
