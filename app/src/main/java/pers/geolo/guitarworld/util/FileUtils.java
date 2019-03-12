package pers.geolo.guitarworld.util;

import android.net.Uri;
import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileUtils {

    public static MultipartBody.Part createMultipartBodyPart(String filePath, String fileType) {
        File file = new File(filePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData(fileType, file.getName(), requestBody);
        return part;
    }

//    public static String getImagePathFromUri(Uri uri) {
//
//    }
}
