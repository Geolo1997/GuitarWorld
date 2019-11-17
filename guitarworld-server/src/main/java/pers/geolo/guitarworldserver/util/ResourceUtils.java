package pers.geolo.guitarworldserver.util;

public class ResourceUtils {
    public static String getFilePath() {
        String filePath = "D:/work/Java/file/GuitarWorld/user_file/";
        return filePath;
    }

    public static String getUserAvatarRelativePath(String username) {
        return username + "/avatar.jpg";
    }
}
