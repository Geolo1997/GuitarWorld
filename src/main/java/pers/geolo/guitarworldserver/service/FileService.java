package pers.geolo.guitarworldserver.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pers.geolo.guitarworldserver.util.ControllerUtils;
import pers.geolo.guitarworldserver.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

@Service
public class FileService {

//    @Autowired
//    UserMapper userMapper;
//
//    public String saveAvatar(String username, MultipartFile avatar) {
//        String filePath = ResourceUtils.getFilePath() + username + "/";
//        File directory = new File(filePath);
//        if (!directory.exists()) {
//            directory.mkdirs();
//        }
//        try {
//            File file = new File(filePath, "avatar.jpg");
//            avatar.transferTo(file);
//            // 更新user表avatar_path字段
//            User user = userMapper.selectByUsername(username);
//            user.setAvatarUrl(username + "/avatar.jpg");
//            userMapper.update(user);
//            return username + "/avatar.jpg";
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public File getAvatar(String username) {
//        String filePath = ResourceUtils.getFilePath() + username + "/";
//        File file = new File(filePath, "avatar.jpg");
//        return file.exists() ? file : null;
//    }
//
//    public void copyFile(String originPath, String destinationPath) throws IOException {
//        InputStream inputStream = new FileInputStream(originPath);
//        File directory = new File(destinationPath.substring(0, destinationPath.lastIndexOf("/")));
//        System.out.println("dir: " + directory);
//        if (!directory.exists()) {
//            directory.mkdir();
//        }
//        OutputStream outputStream = new FileOutputStream(destinationPath);
//        IoUtils.streamTransfor(inputStream, outputStream, 2048);
//    }
//
//    public String saveImage(String username, MultipartFile image) {
//        String fileName = image.getOriginalFilename();
//        String fileType = fileName.substring(fileName.lastIndexOf("."));
//        long currentTime = new Date().getTime();
//        String filePath = ResourceUtils.getFilePath() + username + "/images/";
//        File directory = new File(filePath);
//        if (!directory.exists()) {
//            directory.mkdirs();
//        }
//        try {
//            image.transferTo(new File(filePath, "" + currentTime + fileType));
//            return username + "/images/" + currentTime + fileType;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    /**
     * 根据路径从文件服务器中获取文件
     *
     * @param path 相对路径
     * @return 相对路径所指文件
     */
    public File getFile(String path) {
        String filePath = ResourceUtils.getFilePath() + path;
        File file = new File(filePath);
        return file.exists() ? file : null;
    }

    /**
     * 将文件存入文件服务器中
     *
     * @param file 文件
     * @return 文件存入的相对路径
     * @throws IOException
     */
    public String saveFile(MultipartFile file) throws IOException {
        String relativeFilePath = makeFilePath();
        String filePath = ResourceUtils.getFilePath() + relativeFilePath;
        String fileName = makeFileName() + "." + getFileType(file);
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        file.transferTo(new File(filePath, fileName));
        // TODO 动态获取
        HttpServletRequest request = ControllerUtils.getRequest();
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        return baseUrl + "/file?filePath=" + relativeFilePath + fileName;
    }

    /**
     * 获取原始文件类型
     *
     * @param file 文件
     * @return 文件类型
     */
    private String getFileType(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        return fileType;
    }

    /**
     * 根据当前年月日生成文件路径：年月/日
     *
     * @return 生成的文件路径
     */
    private String makeFilePath() {
        StringBuilder fileNameBuilder = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        fileNameBuilder.append(calendar.get(Calendar.YEAR))
                .append(calendar.get(Calendar.MONTH) + 1)
                .append("/" + calendar.get(Calendar.DATE))
                .append("/");
        return fileNameBuilder.toString();
    }

    /**
     * 通过UUID生成随机文件名
     *
     * @return
     */
    private String makeFileName() {
        return UUID.randomUUID().toString();
    }
}
