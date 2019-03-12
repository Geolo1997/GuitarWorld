package pers.geolo.guitarworldserver.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.ibatis.io.Resources;

public class ResourceUtils {
    public static String getFilePath() {
        String filePath = "/geolo/work/Java/file/GuitarWorld/user_file/";
        return filePath;
    }
}
