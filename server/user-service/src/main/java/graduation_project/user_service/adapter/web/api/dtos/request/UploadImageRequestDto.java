package graduation_project.user_service.adapter.web.api.dtos.request;
import com.mgmtp.ihcmc.hive.server.adapter.web.api.exceptions.FileParsingException;
import com.mgmtp.ihcmc.hive.server.application.usecases.request.UploadImageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public record UploadImageRequestDto(
       MultipartFile multipartFile
) {
    public UploadImageRequest toRequest() {

        String imageName = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        byte[] imageData;
        try {
            imageData = multipartFile.getBytes();
        } catch (IOException e) {
            throw new FileParsingException("Fail to upload file: File extraction failed");

        }

        return UploadImageRequest.builder()
                .imageName(imageName)
                .contentType(contentType)
                .image(imageData)
                .build();
    }

}