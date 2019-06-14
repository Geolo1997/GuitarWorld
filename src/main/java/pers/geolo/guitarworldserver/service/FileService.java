package pers.geolo.guitarworldserver.service;

import java.io.*;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pers.geolo.guitarworldserver.dao.UserMapper;
import pers.geolo.guitarworldserver.entity.User;
import pers.geolo.guitarworldserver.util.IoUtils;
import pers.geolo.guitarworldserver.util.ResourceUtils;

@Service
public class FileService {

    @Autowired
    UserMapper userMapper;

    public String saveAvatar(String username, MultipartFile avatar) {
        String filePath = ResourceUtils.getFilePath() + username + "/";
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            File file = new File(filePath, "avatar.jpg");
            avatar.transferTo(file);
            // 更新user表avatar_path字段
            User user = userMapper.selectByUsername(username);
            user.setAvatarPath(username  + "/avatar.jpg");
            userMapper.update(user);
            return username + "/avatar.jpg";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public File getAvatar(String username) {
        String filePath = ResourceUtils.getFilePath() + username + "/";
        File file = new File(filePath, "avatar.jpg");
        return file.exists() ? file : null;
    }

    public void copyFile(String originPath, String destinationPath) throws IOException {
        InputStream inputStream = new FileInputStream(originPath);
        File directory = new File(destinationPath.substring(0, destinationPath.lastIndexOf("/")));
        System.out.println("dir: " + directory);
        if (!directory.exists()) {
            directory.mkdir();
        }
        OutputStream outputStream = new FileOutputStream(destinationPath);
        IoUtils.streamTransfor(inputStream, outputStream, 2048);
    }

    public String saveImage(String username, MultipartFile image) {
        String fileName = image.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        long currentTime = new Date().getTime();
        String filePath = ResourceUtils.getFilePath() + username + "/images/";
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            image.transferTo(new File(filePath, "" + currentTime + fileType));
            return username + "/images/" + currentTime + fileType;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public File getFile(String imagePath) {
        String filePath = ResourceUtils.getFilePath() + imagePath;
        File file = new File(filePath);
        return file.exists() ? file : null;
    }
}
