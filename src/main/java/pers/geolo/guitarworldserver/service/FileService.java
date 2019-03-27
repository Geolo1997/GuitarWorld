package pers.geolo.guitarworldserver.service;

import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pers.geolo.guitarworldserver.util.ResourceUtils;

@Service
public class FileService {

    public int saveProfilePicture(String username, MultipartFile profilePicture) {
        String filePath = ResourceUtils.getFilePath() + username + "/";
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdir();
        }
        try {
            profilePicture.transferTo(new File(filePath, "profilePicture.jpg"));
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
    }

    public File getProfilePicture(String username) {
        String filePath = ResourceUtils.getFilePath() + username + "/";
        File file = new File(filePath, "profilePicture.jpg");
        return file.exists() ? file : null;
    }
}
