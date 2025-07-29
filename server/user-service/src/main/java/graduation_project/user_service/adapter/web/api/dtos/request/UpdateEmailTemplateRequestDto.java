package graduation_project.user_service.adapter.web.api.dtos.request;

import com.mgmtp.ihcmc.hive.server.application.usecases.request.UpdateEmailTemplateRequest;

import java.util.Date;
import java.util.List;

public record UpdateEmailTemplateRequestDto(
    String body,
    String title,
    String subject,
    List<String> variables,
    Date createdAt,
    String updatedBy
) {

    public UpdateEmailTemplateRequest toRequest(){
        return new UpdateEmailTemplateRequest(body,title,subject,variables,createdAt,updatedBy);
    }

}
