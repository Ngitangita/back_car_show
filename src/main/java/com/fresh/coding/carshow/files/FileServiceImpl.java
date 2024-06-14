package com.fresh.coding.carshow.files;

import com.fresh.coding.carshow.exceptions.FileOperationException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final String uploadDir = "static/";

    @Override
    public String saveFile(MultipartFile file) {
        try {
            var fileName = this.generateRandomFileName(file);
            var classPathResources = new ClassPathResource(uploadDir);
            var uploadDirFile = classPathResources.getFile();
            var folderPath = uploadDirFile.getAbsolutePath();
            var folder = new File(folderPath);
            if (!folder.exists()) {
                var created = folder.mkdirs();
                if (!created) {
                    throw new FileOperationException("Failed to create directory: " + folderPath);
                }
            }
            var filePath = Paths.get(folderPath, fileName).toString();
            var dest = new File(filePath);
            file.transferTo(dest);
            return ServletUriComponentsBuilder.fromCurrentContextPath().path("/" + fileName).toUriString();
        } catch (IOException e) {
            throw new FileOperationException("Failed to save file", e);
        }
    }

    @Override
    public boolean deleteFile(String url) {
        try {
            var fileName = url.substring(url.lastIndexOf('/') + 1);
            var classPathResources = new ClassPathResource(uploadDir);
            var uploadDirFile = classPathResources.getFile();
            var folderPath = uploadDirFile.getAbsolutePath();
            var filePath = Paths.get(folderPath, fileName).toString();
            var file = new File(filePath);
            return file.delete();
        } catch (IOException e) {
            throw new FileOperationException("Failed to delete file", e);
        }
    }

    private String generateRandomFileName(MultipartFile file) {
        var currentTime = System.currentTimeMillis();
        var uuid = UUID.randomUUID().toString();
        return currentTime + "_" + uuid + "_" + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    }
}
