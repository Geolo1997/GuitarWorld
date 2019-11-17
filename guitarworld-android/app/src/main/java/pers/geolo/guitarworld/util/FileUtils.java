package pers.geolo.guitarworld.util;

import java.io.*;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileUtils {

    public static MultipartBody.Part createMultipartBodyPart(File file, String fieldName) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData(fieldName, file.getName(), requestBody);
        return part;
    }

//    public static String getImagePathFromUri(Uri uri) {
//
//    }

    public static void saveFile(String filePath, InputStream inputStream) throws IOException {

        OutputStream outputStream = new FileOutputStream(filePath);
        byte[] buffer = new byte[2048];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public static File getDirectory(File file) {
        String filePath = file.getPath();
        String directoryPath = filePath.substring(0, filePath.lastIndexOf("/"));
        return new File(directoryPath);
    }
}
