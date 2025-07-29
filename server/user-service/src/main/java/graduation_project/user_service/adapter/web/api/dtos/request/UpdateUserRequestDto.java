package graduation_project.user_service.adapter.web.api.dtos.request;

import com.mgmtp.ihcmc.hive.server.adapter.web.api.exceptions.FileParsingException;
import com.mgmtp.ihcmc.hive.server.application.usecases.request.UpdateUserRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public record UpdateUserRequestDto(
        String fullName,
        String department,
        String bio,
        MultipartFile multipartFile,
        String about
) {
    public UpdateUserRequest toRequest() {
        String avatarName = null;
        String contentType = null;
        byte[] avatarData = null;

        if (multipartFile != null) {
            avatarName = multipartFile.getOriginalFilename();
            contentType = multipartFile.getContentType();
            try {
                avatarData = multipartFile.getBytes();
            } catch (IOException e) {
                throw new FileParsingException("Fail to upload file: File extraction failed");
            }
        }

        return UpdateUserRequest.builder()
                .fullName(fullName)
                .department(department)
                .bio(bio)
                .avatarName(avatarName)
                .contentType(contentType)
                .avatar(avatarData)
                .about(about)
                .build();
    }
}
