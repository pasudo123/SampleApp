package edu.doubler.app.user;

import edu.doubler.app.storage.UploadFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserStorageService userStorageService;

    public UserController(UserStorageService userStorageService) {
        this.userStorageService = userStorageService;
    }

    @PostMapping("/image/{id}")
    public UploadFileResponse uploadImage(@PathVariable("id") Long id,
                                          @RequestParam("image") MultipartFile file) {

        logger.info("=====> 이미지 파일 업로드 수행");
        logger.info("File-Name :: {}", file.getName());
        logger.info("Content-Type :: {}", file.getContentType());
        logger.info("File-Resource :: {}", file.getResource().toString());

        String fileName = userStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/users/image/" + id + "/")
                .path(fileName)
                .toUriString();

        UploadFileResponse response = UploadFileResponse.builder()
                .fileName(fileName)
                .fileDownloadUri(fileDownloadUri)
                .fileType(file.getContentType())
                .size(file.getSize())
                .build();

        return response;
    }

    @GetMapping("/image/{id}/{fileName:.+}")
    public ResponseEntity<Resource> downloadImage(@PathVariable("id") Long id,
                                                  @PathVariable("fileName") String fileName,
                                                  HttpServletRequest request) {

        logger.info("=====> 이미지 파일 다운로드 수행");

        Resource resource = userStorageService.loadFileAsResource(fileName);

        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e){
            logger.info("파일의 타입을 찾을 수 없습니다. :: {}", e.getMessage());
        }

        // 형식을 결정못하면 기본 컨텍스트로 대체
        if(contentType == null){
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
