package pers.geolo.guitarworldserver.util;

public class ResourceUtils {
    public static String getFilePath() {
        String filePath = "/geolo/work/Java/file/GuitarWorld/user_file/";
        return filePath;
    }

    public static String getUserAvatarRelativePath(String username) {
        return username + "/avatar.jpg";
    }
}
