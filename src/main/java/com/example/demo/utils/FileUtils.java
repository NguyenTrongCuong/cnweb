package com.example.demo.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.List;

public class FileUtils {

    public static boolean isImage(MultipartFile file) {
        try {
            return ImageIO.read(file.getInputStream()) != null;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean isVideo(MultipartFile file) {
        try {
            String mimeType = URLConnection.guessContentTypeFromName(file.getOriginalFilename());
            return mimeType != null && mimeType.startsWith("video");
        }
        catch (Exception e) {
            return false;
        }
    }

    public static Long calculateFilesSize(List<MultipartFile> files) {
        return files.stream()
                .mapToLong(MultipartFile::getSize)
                .sum();
    }

    public static String saveToDisk(MultipartFile file) throws IOException {
        File storage = new File("src/main/resources/static/storage/");

        if(!storage.exists()) storage.mkdirs();

        File uploadedFile = new File("src/main/resources/static/storage/" + file.getOriginalFilename().replaceAll(" +", "-"));

        FileOutputStream fos = new FileOutputStream(uploadedFile);

        BufferedOutputStream bos = new BufferedOutputStream(fos);

        bos.write(file.getBytes());

        bos.close();

        fos.close();

        return "http://localhost:8080/static/storage/" + file.getOriginalFilename().replaceAll(" +", "-");
    }

}
