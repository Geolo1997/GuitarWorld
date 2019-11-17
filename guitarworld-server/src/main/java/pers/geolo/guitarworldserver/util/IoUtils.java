package pers.geolo.guitarworldserver.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IoUtils {

    public static void streamTransfor(InputStream inputStream, OutputStream outputStream, int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
    }
}
