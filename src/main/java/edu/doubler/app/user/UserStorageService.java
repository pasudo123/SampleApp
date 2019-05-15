package edu.doubler.app.user;

import edu.doubler.app.config.FileStorageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class UserStorageService {

    private Logger logger = LoggerFactory.getLogger(UserStorageService.class);
    private final Path fileStorageLocation;

    public UserStorageService(FileStorageProperties properties) {
        this.fileStorageLocation = Paths.get(properties.getUploadDirectory())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            logger.error("업로드 하기 위한 파일 디렉토리 생성 에러 : {}", e.getMessage());
        }
    }

    public String storeFile(MultipartFile file) {

        // 노멀라이징
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                logger.error("유효하지 않은 파일 경로입니다.");
                return null;
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            logger.error(fileName + " 파일을 저장할 수 없습니다. :: {}", e.getMessage());
        }
        return fileName;
    }

    public Resource loadFileAsResource(String fileName) {

        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                logger.error("파일을 찾지 못했습니다. :: {}", fileName);
                return null;
            }
        } catch (MalformedURLException e) {
            logger.error(fileName + " 파일을 찾지 못했습니다. :: {}", e.getMessage());
        }

        return null;
    }
}
