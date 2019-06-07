package pers.geolo.guitarworld.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-07
 */
public class WorksList extends ArrayList<Works> implements Parcelable {
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
