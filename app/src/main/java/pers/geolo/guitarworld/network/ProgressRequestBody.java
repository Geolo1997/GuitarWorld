package pers.geolo.guitarworld.network;

import android.os.Handler;
import android.os.Looper;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import pers.geolo.guitarworld.entity.FileListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/15
 */
public class ProgressRequestBody extends RequestBody {

    private File file;
    private String mediaType;
    private int onProgressBuffer;
    private FileListener<String> listener;

    public ProgressRequestBody(File file, String mediaType, int onProgressBuffer, FileListener<String> listener) {
        this.file = file;
        this.mediaType = mediaType;
        this.onProgressBuffer = onProgressBuffer;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse(mediaType);
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        byte[] buffer = new byte[onProgressBuffer];
        FileInputStream inputStream = new FileInputStream(file);
        Handler handler = new Handler(Looper.getMainLooper());
        long currentLength = 0, totalLength = file.length();
        int read;
        while ((read = inputStream.read(buffer)) != -1) {
            handler.post(new ProgressRunnable(currentLength, totalLength));
            currentLength += read;
            sink.write(buffer, 0, read);
        }
    }

    class ProgressRunnable implements Runnable {

        private long currentLength;
        private long totalLength;

        public ProgressRunnable(long currentLength, long totalLength) {
            this.currentLength = currentLength;
            this.totalLength = totalLength;
        }

        @Override
        public void run() {
            listener.onProgress(currentLength, totalLength);
        }
    }

}
